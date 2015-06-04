package com.extendbrain.crawl58;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.extendbrain.beans.Content;
import com.extendbrain.beans.OutLinks;

public class Parser {

	public OutLinks parseLinks(Content content){
		Document document = Jsoup.parse(new String(content.getContent()));
		Elements elments = document.select("a");
		for(Element ele : elments){
			String link = ele.attr("href");
			String regex  = "^[http]{4}[\\S]*[^com]{3}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(link);
			boolean find = matcher.find();
			if(find == true)
				System.out.println(link);
		}
		Elements elments2 = document.select("link");
		for(Element ele : elments2){
			String link = ele.attr("href");
			System.out.println(link);
		}
		return null;
	}
}
