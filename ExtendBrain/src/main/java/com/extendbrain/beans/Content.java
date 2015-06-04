package com.extendbrain.beans;

import java.io.UnsupportedEncodingException;

public class Content {
	private String url;
	private byte[] content;
	private String contentType = "text/html;charset=utf-8";
	private String charSet = "";
	public Content(){
		
	}
	public Content(String url,byte[] content,String contentType,String contentEncoding){
		this.url = url;
		this.content = content;
		this.contentType = contentType;
		this.charSet = contentEncoding;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getCharSet(){
		return charSet;
	}
	public void setCharSet(String contentEncoding){
		this.charSet = contentEncoding;
	}
	
	public String getContentString(){
		String contentString = null;
		try {
			contentString = new String(content,charSet);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentString;
	}
}
