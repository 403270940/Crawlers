package com.extendbrain.meituan;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import sun.org.mozilla.javascript.internal.ast.WhileLoop;

import com.extendbrain.beans.Content;
import com.extendbrain.dao.Mongo_Meituan;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;

public class GetDatas {
	private final static String baseURL = "http://api.union.meituan.com/data/api";
	// http://api.union.meituan.com/data/api?keyword=%E7%81%AB%E9%94%85&sort=1&limit=2
	private static final String key = "ed00e761f5ed345f2ef9d3d9fbae292e291";
	private static final String city = "北京";
	private static final String category = "美食";
	private static final String limit = "1";
	private static String district_name = "茌平县";
	private static String key_word = "火锅";
	private static String sort = "1";
	private static String offset = "0";
	private static int activeThreads = 0;
	private static Map<String, String> parameters = new HashMap<String, String>();
	private static Vector<Object> resultVector = new Vector<Object>();
	static {
		parameters.put("key", key);
		parameters.put("city", city);
//		parameters.put("category", category);
		parameters.put("limit", limit);
//		parameters.put("district_name", district_name);
		parameters.put("key_word", key_word);
		parameters.put("sort", sort);
	}

	private static String formURL(Map<String, String> parameters) {
		String url = baseURL + "?";
		Iterator iter = parameters.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
					.next();
			String key = entry.getKey();
			String value = entry.getValue();
			url += key + "=" + URLEncoder.encode(value) + "&";
		}
		url = url.substring(0, url.length() - 1);
		return url;
	}

	public static String GetXML() {
		String url = formURL(parameters);
		System.out.println(url);
		Protocol protocol = ProtocolFactory.getProtocol(url);
		Content content = protocol.getOutput(url);
		if(content == null) return null;
		String result = "";
		try {
			result = new String(content.getContent(), content.getCharSet());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	

	public static List<Object> getAllItems() {
		List<Object> resultList = new ArrayList<Object>();
		long start = new Date().getTime();
		String countPerRequest = limit;
		countPerRequest = "200";
		parameters.put("limit", countPerRequest);
		boolean flag = true;
		while (flag) {
			String xml = GetXML();
			if(xml == null) continue;
			List<Object> result = ConvertUtil.convertXmlToBeans(xml, Data.class.getName());
			if (result.size() != Integer.valueOf(countPerRequest))
				flag = false;
			offset = String.valueOf(Integer.valueOf(countPerRequest)
					+ Integer.valueOf(offset));
			parameters.put("offset", offset);
			resultList.addAll(result);
			resultVector.addAll(result);
			System.out.println(resultVector.size());
		}
		long end = new Date().getTime();
		System.out.println(resultList.size() + ":" + (end - start));
		activeThreads --;
		return resultList;
	}

	public static void getItem() {
		String xml = GetXML();
		List<Object> result = ConvertUtil.convertXmlToBeans(xml, Data.class.getName());
		for (Object object : result) {
			Data data = (Data) object;
			System.out.println(data);
		}
	}

	public static class storeThread extends Thread{
		public void run() {
			// TODO Auto-generated method stub
			while(activeThreads > 0 || resultVector.size()>0){
				System.out.println(activeThreads + ":" + resultVector.size());
				if(resultVector.size() > 0 ){
				Data data = (Data)resultVector.remove(0);
				Mongo_Meituan.insert(data);
				}
				try {
					sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void getAndStore(){
		activeThreads = 1;
		new storeThread().start();
		List<Object> resultList = getAllItems();
	}
	public static void main(String[] args) {
		getAndStore();
//		getItem();
		// List<Object> dataList = getAllItems();
		// Mongo_Meituan.addList(dataList);
	}

}
