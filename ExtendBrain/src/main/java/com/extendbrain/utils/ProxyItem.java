package com.extendbrain.utils;

public class ProxyItem {
	private String hostname;
	private int port;
	
	public ProxyItem(String hostname,int port){
		this.hostname = hostname;
		this.port = port;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}
