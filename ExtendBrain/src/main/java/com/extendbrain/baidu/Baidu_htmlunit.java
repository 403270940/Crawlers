package com.extendbrain.baidu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;



public class Baidu_htmlunit extends Thread{
	private String hostname = null;
	private int port = 0;
	private BrowserVersion browserVersion = BrowserVersion.FIREFOX_31;
	private WebClient webClient = null;
	public Baidu_htmlunit(){
		webClient = new WebClient(browserVersion);
	}
	
	public Baidu_htmlunit(BrowserVersion browserVersion,String hostname,int port){
		this.hostname = hostname;
		this.port = port;
		this.browserVersion = browserVersion;
		webClient = new WebClient(browserVersion,hostname,port);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String baseUrl = "http://www.hao123.com";
		String zhongliu = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=baidu&wd=%E8%82%BF%E7%98%A4&rsv_pq=a43c688400001dee&rsv_t=cb70AihvNsIhCEpIo7PMVWFuE%2F8rjvbLeHqV09Xz4mrlubTyCGH5PeNmN2I&rsv_enter=1&rsv_sug3=2&rsv_sug1=1&rsv_sug2=0&inputT=1996&rsv_sug4=3784";
		boolean flag = true;
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
        while(flag){
		try {
//			HtmlPage page = webClient.getPage(baseUrl);
//			HtmlPage zlPage = webClient.getPage(zhongliu);
			HtmlPage page = webClient.getPage(baseUrl);
			HtmlTextInput text = (HtmlTextInput) page.getByXPath("/html/body/div[4]/div/div[2]/div/div/div[1]/div[3]/form/div[1]/div/input").get(0);
			HtmlSubmitInput button = (HtmlSubmitInput) page.getByXPath("/html/body/div[4]/div/div[2]/div/div/div[1]/div[3]/form/div[2]/input").get(0);
//			System.out.println(text);
//			System.out.println(button);
			text.setValueAttribute("肿瘤");
			HtmlPage zlPage = button.click();
			DomElement tg = zlPage.getElementById("ec_im_container");
			if(tg == null) {
				System.out.println("not found");
				continue;
			}
			flag = false;
			Iterable<DomElement> childs = tg.getChildElements();
			Iterator<DomElement> iter = childs.iterator();
			String urls = null;
			while (iter.hasNext()) {
				DomElement child = iter.next();
				String childXml = child.asXml();
				if(childXml.contains("瘤")){
					DomNodeList<DomNode> as = child.getChildNodes();
					DomNode urlNode = as.get(0);
					String url = urlNode.getAttributes().getNamedItem("href").getNodeValue();
					System.out.println(url);
					urls = url;
				}
					
			}
			webClient.getOptions().setJavaScriptEnabled(false);
			HtmlPage yyPage = webClient.getPage(urls);
			System.out.println(yyPage.asXml());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
	}

	public static void testHtmlUnit(){
		String baseUrl = "http://www.baidu.com";
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
		try {
			HtmlPage page = webClient.getPage(baseUrl);
			HtmlTextInput text = (HtmlTextInput) page.getElementById("kw");
			HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById("su");
//			HtmlTextInput text = (HtmlTextInput) page.getByXPath("/html/body/div[4]/div/div[2]/div/div/div[1]/div[3]/form/div[1]/div/input").get(0);
//			HtmlSubmitInput button = (HtmlSubmitInput) page.getByXPath("/html/body/div[4]/div/div[2]/div/div/div[1]/div[3]/form/div[2]/input").get(0);
			System.out.println(text);
			System.out.println(button);
			text.setValueAttribute("肿瘤");
			HtmlPage page2 = button.click();
			String result = page2.asText();
			System.out.println(result);
			


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testProxy(){
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38,"fly.x.tuxingsun.net",29769);
		try {
			HtmlPage page = webClient.getPage("http://www.baidu.com");
			System.out.println(page.asXml());
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
//		Baidu_htmlunit instance = new Baidu_htmlunit();
		Baidu_htmlunit instance = new Baidu_htmlunit(BrowserVersion.FIREFOX_24,"fly.x.tuxingsun.net",29769);
		instance.start();
		Baidu_htmlunit instance1 = new Baidu_htmlunit(BrowserVersion.FIREFOX_31,"fly.x.tuxingsun.net",29769);
		instance1.start();
	}
}
