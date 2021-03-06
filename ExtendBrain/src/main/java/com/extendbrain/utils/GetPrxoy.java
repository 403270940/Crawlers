package com.extendbrain.utils;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.extendbrain.beans.Content;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class GetPrxoy {
	
	public static List<ProxyItem> getProxyList(int count){
		List<ProxyItem> proxyList = new ArrayList<ProxyItem>();
		String proxyURL = ConfigUtil.getProperty("proxy_url")+count;
		Protocol protocol = ProtocolFactory.getProtocol(proxyURL);
		Content content = protocol.getOutput(proxyURL);
		String proxyResult = content.getContentString();
		String[] proxies = proxyResult.split("\n");
		for(String proxy :proxies){
			String hostname = proxy.trim().split(":")[0];
			int port = Integer.valueOf(proxy.trim().split(":")[1]);
			ProxyItem pItem = new ProxyItem(hostname,port);
			if(isGNProxy(pItem))
				proxyList.add(pItem);
		}

		return proxyList;
	}
	
	public static List<ProxyItem> getHttpsProxyList(int count){
		List<ProxyItem> proxyList = new ArrayList<ProxyItem>();
		String proxyURL = ConfigUtil.getProperty("proxy_url")+count+"&protocol=https";
		Protocol protocol = ProtocolFactory.getProtocol(proxyURL);
		Content content = protocol.getOutput(proxyURL);
		String proxyResult = content.getContentString();
		String[] proxies = proxyResult.split("\n");
		for(String proxy :proxies){
			String hostname = proxy.trim().split(":")[0];
			int port = Integer.valueOf(proxy.trim().split(":")[1]);
			ProxyItem pItem = new ProxyItem(hostname,port);
			if(isGNProxy(pItem))
				proxyList.add(pItem);
		}

		return proxyList;
	}
	
	public static boolean isGNProxy(ProxyItem proxyItem){
		boolean isGNProxy =false;
		Protocol protocol = ProtocolFactory.getProtocol("");
		protocol.setProxy(proxyItem.getHostname(), proxyItem.getPort());
		try {
			Content content = protocol.getOutput("http://www.liyongyue.com/getip.php");
			if(content == null||content.getResultCode()!=200){
				System.out.println(proxyItem.getHostname() + ":" + proxyItem.getPort()+" is bad!");
			}else{
				String result = content.getContentString();
				isGNProxy = proxyItem.getHostname().equals(result.trim());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(proxyItem.getHostname() + ":" + proxyItem.getPort()+" is bad!");
			isGNProxy = false;
//			e.printStackTrace();
		}
		return isGNProxy;
	}
	
	public static void main(String[] args) {
		WebClient wb = new WebClient(BrowserVersion.CHROME);
		List<ProxyItem> proxyList = getProxyList(5);
		System.out.println("result:");
		for(ProxyItem proxyItem : proxyList){
			try {
				wb.getOptions().setTimeout(6000);
				wb.getOptions().setJavaScriptEnabled(false);
				HtmlPage page = wb.getPage("http://www.sogou.com/");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				continue;
			} 
			System.out.println(proxyItem.getHostname() + ":" + proxyItem.getPort() + "can visit baidu");
//			boolean result = isGNProxy(proxyItem);
//			if(result == true){
//				System.out.println(proxyItem.getHostname() + ":" + proxyItem.getPort()+"是高匿代理");
//				wb.getOptions().setProxyConfig(new ProxyConfig(proxyItem.getHostname(),proxyItem.getPort()));
//				try {
//					HtmlPage hPage= wb.getPage("http://www.hao123.com");
//					System.out.println(hPage.asXml());
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}else {
//				System.out.println(proxyItem.getHostname() + ":" + proxyItem.getPort()+"不是高匿代理");
//			}
			
		}
	}
}
