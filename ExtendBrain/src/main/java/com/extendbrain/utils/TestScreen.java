package com.extendbrain.utils;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
import com.gargoylesoftware.htmlunit.javascript.host.Screen;
import com.gargoylesoftware.htmlunit.javascript.host.Window;

public class TestScreen {
	private static String url = "http://www.liyongyue.com/screen.html";
	public static void testScreen(){
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
		Screen screen = new Screen();
		screen.setWidth(1920);
		screen.setHeight(1280);
		webClient.setDefaultScreen(screen);
		try {
//			webClient.getOptions().setJavaScriptEnabled(false);
			JavaScriptEngine jsEngine = webClient.getJavaScriptEngine();
			HtmlPage page = webClient.getPage(url);
			String result = page.asXml();
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		testScreen();
	}
}
