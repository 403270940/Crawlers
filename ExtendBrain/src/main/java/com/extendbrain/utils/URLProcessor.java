package com.extendbrain.utils;

public class URLProcessor {

	public synchronized static String getFileNameByUrl(String url,String contentType)
	{
		url = url.substring(7);
		if(contentType.indexOf("html") != -1)
		{
			url = url.replaceAll("[\\?/:*|<>\"]","_")+".html";
			return url;
		}
		else
		{
			return url.replaceAll("[\\?/:*|<>\"]","_")+"."+
					contentType.substring(contentType.lastIndexOf("/")+1);
		}
	}
}
