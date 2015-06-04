package com.extendbrain.crawl58;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.extendbrain.beans.Content;
import com.extendbrain.beans.District;
import com.extendbrain.beans.RentItem;
import com.extendbrain.beans.URLDatum;
import com.extendbrain.dao.Mysql_Cities;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;

public class Parser_58 {
	
	/*
	 * 获取所有区的分区名并保存到数据库
	 */
	public void parseArea(){
		int count = 0;
		for(Entry<String,String> entry : District.districtMap.entrySet()){
			String name = entry.getKey();
			String pinyin = entry.getValue();
			String url = "http://bj.58.com/"+pinyin+"/chuzu/";
			System.out.println(name);
			URLDatum datum = new URLDatum(url);
			Protocol protocol = ProtocolFactory.getProtocol(datum.getUrl());
			Content content = protocol.getOutput(datum.getUrl(), datum);
			List<String> areaList = getArea(content);
			for(String area: areaList)
				Mysql_Cities.save(count, name, area, area);
		}
	}
	
	/*
	 * 获取某个区中的分区名
	 */
	public List<String> getArea(Content content){
		List<String> areaList = new ArrayList<String>();
		Document doc = Jsoup.parse(new String(content.getContent()));
		Elements elements = doc.select("#filter_quyu dd a");
		for(Element element : elements){
			String str = element.text();
			String url = element.attr("href");
			if(str.equals("全北京") )
				continue;
			if(District.districtMap.get(str)!= null)
				continue;
			if(!url.contains("javascript")&&url.endsWith("chuzu/"))
			{
				url = "http://bj.58.com" + url;
				areaList.add(url);
			}
		}
		return areaList;
	}
	
	public static List<RentItem> getRentItems(Content content){
		String tr = "#infolist>table tbody tr";
		List<RentItem> rentList = new ArrayList<RentItem>();
		Document doc = Jsoup.parse(new String(content.getContent()));
		Elements elements = doc.select(tr);
//		new RentItem(imgURL, destURL, anchor, area, addr, village, time)
		for(Element element : elements){
			
			Elements imgs = element.select("img");
			Element img = imgs.first();
			String imgURL = img.attr("src");
			
			Element link = element.select("h1 a").first();
			String destURL = link.attr("href");
			String anchor = link.text();
			
			Element address = element.select(".qj-renaddr").first();
			String[] addrlist = address.text().split("-|/");
			String area = addrlist[0].trim();
			String village = addrlist[1].trim();
			String time = addrlist[2].trim();
			
			Element detail =  element.child(2);
			Float charges = Float.valueOf(detail.select(".pri").first().text().trim());
			int charge = charges.intValue();
			String room = detail.select(".showroom").first().text();
			RentItem rentItem = new RentItem(imgURL, destURL, anchor, area, village, time, charge,room);
			rentList.add(rentItem);
		}
		return rentList;
	}

}
