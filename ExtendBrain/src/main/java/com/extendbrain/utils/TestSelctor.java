package com.extendbrain.utils;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestSelctor {
	static String fileName = "src/main/java/zhihu.html";
	public static void main(String[] args) {
		File file = new File(fileName);
		String xsrf = "";
		System.out.println(file.exists());
		try {
			Document doc = Jsoup.parse(file, "gb2312");
			System.out.println(doc);
//			doc.getElementsByAttributeValue("name", "_xsrf");
			Elements hidden = doc.select("input[type=hidden]");
			Element element = hidden.first();
			System.out.println(element);
			if(element == null) return ;
			String xsrfString = element.attr("value");
			if(xsrfString != null)
				xsrf = xsrfString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(xsrf);
	}
}
