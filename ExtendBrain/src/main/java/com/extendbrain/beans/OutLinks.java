package com.extendbrain.beans;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class OutLinks {
	private String fromURL;
	private Set<String> toURLS = new HashSet<String>();
	public void setFromURL(String fromURL){
		this.fromURL = fromURL;
	}
	public String getFromURL(){
		return fromURL;
	}
	public String getToURL(){
		Iterator it = toURLS.iterator();//这个set是否有下一个	
		String result = null;
		if(it.hasNext()){//有就把下一个取出来			
			result = (String) it.next();
			it.remove();
		}
		
		return result;
	}
	public Set<String> getToURLS(){
		return toURLS;
	}
	public void addToURLS(String url){
		toURLS.add(url);
	}
	public static void main(String[] args) {
		OutLinks outLinks = new OutLinks();
		outLinks.addToURLS("http://www.baidu.com");
		outLinks.addToURLS("http://www.baidu.com");
		System.out.println(outLinks.getToURL());
		System.out.println(outLinks.getToURL());
	}
}
