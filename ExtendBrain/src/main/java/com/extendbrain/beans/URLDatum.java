package com.extendbrain.beans;

public class URLDatum {
	
	public static byte STATUS_INJECTED = 0x01;
	public static byte STATUS_SUCCESS = 0x20;
	public static byte STATUS_PERM_REDIR = 0x31;
	public static byte STATUS_TEMP_REDIR = 0x32;
	public static byte STATUS_NOT_FOUND = 0x44;
	
	
	private String url;
	private byte status;
	private long fetchTime = System.currentTimeMillis();
	private int fetchInterval = 24*3600;
	private long lastModifiedTime = System.currentTimeMillis();
	private float score = 1;
	public URLDatum(){
		
	}
	public URLDatum(String url){
		this(url, STATUS_INJECTED, System.currentTimeMillis(), 24 * 3600, System.currentTimeMillis(), 1);
	}
	public URLDatum(String url,byte status,long fetchTime,int fetchInterval,long lastModifiedTime,float score){
		this.url = url;
		this.status = status;
		this.fetchTime = fetchTime;
		this.fetchInterval = fetchInterval;
		this.lastModifiedTime = lastModifiedTime;
		this.score = score;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public long getFetchTime() {
		return fetchTime;
	}
	public void setFetchTime(long fetchTime) {
		this.fetchTime = fetchTime;
	}
	public int getFetchInterval() {
		return fetchInterval;
	}
	public void setFetchInterval(int fetchInterval) {
		this.fetchInterval = fetchInterval;
	}
	public long getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(long lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	
	
}
