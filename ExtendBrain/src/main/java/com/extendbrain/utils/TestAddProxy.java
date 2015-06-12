package com.extendbrain.utils;

import com.extendbrain.beans.Content;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;

public class TestAddProxy {
	public static void testAddProxy(){
		String hostname = "101.71.27.120";
		Protocol protocol = ProtocolFactory.getProtocol("");
		protocol.setProxy(hostname, 80);
		Content content = protocol.getOutput("http://www.liyongyue.com/getip.php");
		String result = content.getContentString();
		System.out.println(result);
		System.out.println(hostname.equals(result.trim()));
	}
	
	
	public static void main(String[] args) {
		testAddProxy();
	}
}
