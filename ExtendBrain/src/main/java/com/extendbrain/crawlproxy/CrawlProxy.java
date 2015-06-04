package com.extendbrain.crawlproxy;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.extendbrain.beans.Content;
import com.extendbrain.beans.URLDatum;
import com.extendbrain.dao.Mysql_Proxies;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;

public class CrawlProxy {
	private static final String baseUrl = "http://www.proxy.com.ru/";
	private static Protocol protocol = ProtocolFactory.getProtocol(baseUrl);
	List<ProxyItem> proxyList = new ArrayList<ProxyItem>();
	
	/*
	 * 先获取代理的页码总数，再依次获取每页中所有的代理
	 */
	public CrawlProxy(){
		URLDatum datum = new URLDatum(baseUrl);
		Content content = protocol.getOutput(baseUrl, datum);
		String html = content.getContentString();
		int pagenum = getPageNum(html);
		Mysql_Proxies.droptable();
		for(int i = 1;i <= pagenum ; i ++){
			String  listUrl = baseUrl + "list_" + i +".html";
			URLDatum listDatum = new URLDatum(listUrl);
			Content listContent = protocol.getOutput(listUrl, listDatum);
			String listHtml = listContent.getContentString();
			proxyList.addAll(getProxyList(listHtml));
		}
		
		Mysql_Proxies.createTable();
		if(proxyList.size()<= 0 ){
			System.out.println("没有找到代理！");
			return;
		} 
		for(int i = 0; i < proxyList.size(); i ++){
			ProxyItem proxyItem = proxyList.get(i);
			Mysql_Proxies.save(proxyItem);
		}
//		try {
//			FileOutputStream fos = new FileOutputStream(new File("proxy.txt"));
//			for(int i = 0; i < proxyList.size(); i ++){
//				fos.write(proxyList.get(i).toString().getBytes());
//				fos.write("\n".getBytes());
//			}
//			fos.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println(proxyList.size());
	}
	
	public int getPageNum(String html){
		Document doc= Jsoup.parse(html);
		Element element = doc.select("[color=red]").get(0).parent();
		int num = element.select("[href]").size();
		return num + 1;
	}
	
	public static List getProxyList(String html){
		List<ProxyItem> proxyList = new ArrayList<ProxyItem>();
		Document doc= Jsoup.parse(html);
		//td[@valign='top']/font[@size='2']//table[contains(@width,'100%')]/tr
		Elements elements = doc.select("td > font > table > tbody > tr");
		int len = elements.size();
		for(int i = 1; i < len; i ++){
			Element element = elements.get(i);
			String ip = element.child(1).text();
			String port = element.child(2).text();
			String proxyType = element.child(3).text();
			String location = element.child(4).text();
			ProxyItem proxyItem = new ProxyItem(ip, port, proxyType, location);
			proxyList.add(proxyItem);
		}
		return proxyList;
	}
	
	public static void main(String[] args) {
		new CrawlProxy();
	}
	
}
