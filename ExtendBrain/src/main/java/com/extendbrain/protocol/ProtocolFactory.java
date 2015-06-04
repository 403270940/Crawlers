package com.extendbrain.protocol;

public class ProtocolFactory {
	private static Protocol protocol = null;
	public static Protocol getProtocol(String url){
		return new Http();
	}
	public static Protocol getSingletonProtocol(String url){
		if(protocol == null)
			protocol = new Http();
		return protocol;
	}
}
