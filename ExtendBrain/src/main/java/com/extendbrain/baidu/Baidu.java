package com.extendbrain.baidu;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.extendbrain.beans.Content;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;

public class Baidu {
	private String baseURL = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E8%82%BF%E7%98%A4&rsv_pq=d1dd3eb50000a769&rsv_t=5d39pTucEtb72Zb69zfSae5q7lcQlGdcb%2BZ%2FxRj73RKzc3LGlsLbgv79KB8&rsv_enter=1&rsv_sug3=3&rsv_sug1=2&rsv_sug2=0&inputT=3498&rsv_sug4=6192";
	public String search(Protocol protocol){
		protocol.getOutput("http://www.baidu.com");
		HttpGet get = new HttpGet(baseURL);
		Content content = protocol.getOutput(get);
		String html = content.getContentString();
//		System.out.println(html);
		return html;
	}
	
	public List<String> getURLS(String html,String targetURL){
		List<String> urlList = new ArrayList<String>();
		Document doc = Jsoup.parse(html);
		Element root = doc.select("#ec_im_container").first();
		Elements tuiguangs = root.children();
		int size = tuiguangs.size();
		for(Element ele : tuiguangs){
			
			String eleString = ele.html();
			if(eleString.contains(targetURL)){
				System.out.println(ele);
				String url = ele.child(0).attr("href").trim();
				if(!url.equals(""))
					urlList.add(url);
			}
		}
		return urlList;
	}
	
	public List<String> getSearchURLS(Protocol protocol,String targetURL){
		String html = search(protocol);
		List<String> urlList = getURLS(html,targetURL);
		return urlList;
	}
	
	public static void main(String[] args) {
		Protocol protocol = ProtocolFactory.getProtocol("http");
		String targetURL = "wjyy.tgbyy.com";
		Baidu baidu = new Baidu();
		List<String> resultURL = baidu.getSearchURLS(protocol,targetURL);
		for(String url : resultURL){
			System.out.println(url);
		}
	}
	
	

}
