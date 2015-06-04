package com.extendbrain.crawlproxy;

public class ProxyItem {
	private String ip;
	private String port;
	private String proxyType;
	private String location;
	public ProxyItem(String ip, String port, String proxyType,String location){
		this.ip = ip;
		this.port = port;
		this.proxyType = proxyType;
		this.location = location;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getProxyType() {
		return proxyType;
	}
	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String toString(){
		return "ip:" + ip + 
				",port:" + port + 
				",proxyType:" + proxyType + 
				",location:" + location; 
	}
}
