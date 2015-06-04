package com.extendbrain.meituan;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class GetCitiesFromXML {
	public static Vector<String> getCities(){
		File file = new File("src/main/java/conf/meituan_cities.xml");
		Vector<String> cities = null;
		try {
			cities = new Vector<String>();
			Document doc = Jsoup.parse(file, "UTF-8");
			Elements eles = doc.select("division");
			for(Element ele : eles){
				String city = ele.select("name").first().text().trim();
				cities.add(city);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			cities = null;
			e.printStackTrace();
		}
		
		return cities;
	}
	
	public static void main(String[] args) {
		Vector<String> cities = getCities();
		for(String city : cities)
			System.out.println(city);
	}
}
