package com.extendbrain.baidu;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;

public class ClickBaiduAD {
	Protocol protocol = ProtocolFactory.getProtocol("http://");
	
	private static List<String> getProxies(){
		List<String> proxies = new ArrayList<String>();
		proxies.add("none");
		return proxies;
	}
	
	private static List<String> getUserAgents(){
		List<String> userAgents = new ArrayList<String>();
		userAgents.add("none");
		userAgents.add("Mozilla/5.0 (Windows NT 5.2) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30");
		userAgents.add("Mozilla/5.0 (Windows NT 5.1; rv:5.0) Gecko/20100101 Firefox/5.0");
		userAgents.add("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET4.0E; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C)");
		return userAgents;
	}
	
	public Thread clickThread = new Thread(){
		public void run() {
			Protocol protocol = ProtocolFactory.getProtocol("http://");
			List<String> proxies = getProxies();
			List<String> userAgents = getUserAgents();
			int i = 0;
			String proxy = proxies.get(i);
			String userAgent = userAgents.get(i);
			if(!proxy.equals("none")){
				String hostname = proxy.split(":")[0];
				int port = Integer.valueOf(proxy.split(":")[1]);
				protocol.setProxy(hostname, port);
			}
			if(!userAgent.equals("none")){
				protocol.setUserAgent(userAgent);
			}
			
			String targetURL = "www.shzazx.com";
			Baidu baidu = new Baidu();
			List<String> destURList = baidu.getSearchURLS(protocol, targetURL);
			for(String url : destURList){
				System.out.println("url:"+url);
			}
			
		};
	};
	
	public void run(int threadCount){
		for(int i = 0; i < threadCount; i ++){
			Thread thread = new Thread(clickThread);
			thread.run();
		}
	}
	
	public static void main(String[] args) {
		ClickBaiduAD instance = new ClickBaiduAD();
		instance.run(1);
	}
	
}
