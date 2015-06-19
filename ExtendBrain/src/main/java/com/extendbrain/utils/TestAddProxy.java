package com.extendbrain.utils;

import com.extendbrain.beans.Content;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class TestAddProxy {
	public static void testAddProxy(){
		String hostname = "fly.x.tuxingsun.net";
		Protocol protocol = ProtocolFactory.getProtocol("");
//		protocol.setProxy(hostname, 29769);
		Content content = protocol.getOutput("http://www.liyongyue.com/getip.php");
		String result = content.getContentString();
		System.out.println(result);
		System.out.println(hostname.equals(result.trim()));
	}
	
	
	public static void main(String[] args) {
		testAddProxy();
//		BrowserVersion browserVersion = BrowserVersion.FIREFOX_24;
//		System.out.println(browserVersion.getUserAgent());
//		System.out.println(browserVersion.getCpuClass());
	}
}
