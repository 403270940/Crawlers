package com.extendbrain.baidu;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestBaiduClick {
	private static String url = "http://www.baidu.com/";
	
	public static void testClickSearch(){
		WebClient wb = new WebClient(BrowserVersion.CHROME);
//		wb.getOptions().setJavaScriptEnabled(false);// HtmlUnit对JavaScript的支持不好，关闭之
		wb.getOptions().setCssEnabled(false);// HtmlUnit对CSS的支持不好，关闭之
		wb.setAjaxController(new NicelyResynchronizingAjaxController());  
		try {
			HtmlPage page = wb.getPage(url);
			HtmlElement input = (HtmlElement) page.getHtmlElementById("kw");// 获取搜索输入框并提交搜索内容（查看源码获取元素名称）
			input.fireEvent("click");
			input.type("1");
			HtmlInput btn = (HtmlInput) page.getHtmlElementById("su");// 获取搜索按钮并点击
			wb.getOptions().setJavaScriptEnabled(false);
			HtmlPage firstBaiduPage = btn.click();// 模拟搜索按钮事件
			wb.getOptions().setJavaScriptEnabled(true);
			System.out.println(firstBaiduPage.asXml());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) {
		testClickSearch();
	}
}
