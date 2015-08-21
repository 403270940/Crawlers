package com.extendbrain.utils;

import java.util.Date;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestAD {
	private volatile static String ID = getID();
	public static String getID(){
		Date date = new Date();
		String time = String.valueOf(date.getSeconds());
		return time;
	}
	public static void testAd(){
		String url = "http://www.liyongyue.com/ssq.php";
		WebClient wb = new WebClient(BrowserVersion.FIREFOX_38,"fly.x.tuxingsun.net",29769);
		wb.getOptions().setCssEnabled(false);
		wb.setAjaxController(new NicelyResynchronizingAjaxController());  

		try {
			HtmlPage page = wb.getPage(url);
			System.out.println(page.asXml());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		while (true) {
			System.out.println(ID);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
