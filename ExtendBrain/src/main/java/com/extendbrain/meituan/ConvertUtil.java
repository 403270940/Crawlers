package com.extendbrain.meituan;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class ConvertUtil {
	
	public static Object convertElementToBean(Element element, String beanName) {
		try {
			Object bean = Class.forName(beanName).newInstance();
			Field[] fields = bean.getClass().getDeclaredFields();
			// System.out.println("fields size: " + fields.length);
			for (Field field : fields) {
				String fieldName = field.getName().substring(0, 1)
						.toUpperCase()
						+ field.getName().substring(1);
				String setMethodName = "set" + fieldName;
				String getMethodName = "get" + fieldName;
				Type type = field.getGenericType();
				if (type == String.class) {
					Method setMethod = bean.getClass().getDeclaredMethod(
							setMethodName, new Class[] { String.class });
					String value = element.select(field.getName()).first()
							.text();
					setMethod.invoke(bean, new Object[] { value });
					// Method getMethod =
					// bean.getClass().getDeclaredMethod(getMethodName);
					// String result = (String)getMethod.invoke(bean);
					// System.out.println(field.getName());
					// System.out.println(field.getType());
					// System.out.println(result);
				} else if (type instanceof ParameterizedType) {
					ParameterizedType parameterizedType = (ParameterizedType) type;
					Class typeClass = (Class) parameterizedType
							.getActualTypeArguments()[0];
					List<Object> list = new ArrayList<Object>();
					String[] itemNames = typeClass.getName().split("\\.");
					String itemName = itemNames[itemNames.length - 1];
					for (Element ele : element.select(fieldName).first()
							.select(itemName)) {
						list.add(convertElementToBean(ele, typeClass.getName()));
					}
					Method setMethod = bean.getClass().getDeclaredMethod(
							setMethodName, new Class[] { field.getType() });
					setMethod.invoke(bean, new Object[] { list });
				} else {

					Method setMethod = bean.getClass().getDeclaredMethod(
							setMethodName, new Class[] { field.getType() });
					Object value = convertElementToBean(
							element.select(field.getName()).first(), field
									.getType().getName());
					setMethod.invoke(bean, new Object[] { value });
				}
			}
			return bean;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static List<Object> convertXmlToBeans(String xml, String beanName) {
		List<Object> resultBeans = new ArrayList<Object>();
		Document doc = Jsoup.parse(xml, "", Parser.xmlParser());
		String fullName = beanName;
		String[] names = fullName.split("\\.");
		String name = names[names.length - 1].toLowerCase();
		Elements datas = doc.select(name);
		// System.out.println("data数量为: "+datas.size());
		for (int i = 0; i < datas.size(); i++) {
			resultBeans.add(convertElementToBean(datas.get(i), beanName));
		}
		return resultBeans;
	}
}
