package com.extendbrain.baidu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.Screen;



public class Baidu_htmlunit extends Thread{
	private String hostname = null;
	private int port = 0;
	private BrowserVersion browserVersion = BrowserVersion.FIREFOX_31;
	private WebClient webClient = null;
	private Screen screen = new Screen();
	private String name = "";
	public Baidu_htmlunit(){
		webClient = new WebClient(browserVersion);
	}
	
	public Baidu_htmlunit(BrowserVersion browserVersion){
		webClient = new WebClient(browserVersion);
	}
	
	public Baidu_htmlunit(BrowserVersion browserVersion,String hostname,int port){
		this.hostname = hostname;
		this.port = port;
		this.browserVersion = browserVersion;
		webClient = new WebClient(browserVersion,hostname,port);
	}
	
	
	public void setScreen(Screen screen) {
		this.screen = screen;
	}
	
	private void out(String msg){
		System.out.println(getName() + ":" + msg);
	}
	
	@Override
	public void run(){
		webClient.setDefaultScreen(screen);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
//		String baseUrl = "http://baidu.com";
		String baseUrl = "http://xin.bjyft.net";
		boolean flag = true;
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
        while(flag){
			try {
				HtmlPage page = webClient.getPage(baseUrl);
				out(page.asXml());
				flag = false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		webClient.setDefaultScreen(screen);
//		webClient.getOptions().setThrowExceptionOnScriptError(false);
//		String baseUrl = "http://www.hao123.com";
//		boolean flag = true;
//        webClient.getOptions().setCssEnabled(false);
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
//        while(flag){
//			try {
//				HtmlPage page = webClient.getPage(baseUrl);
//				
//				HtmlTextInput text = (HtmlTextInput) page.getByXPath("/html/body/div[4]/div/div[2]/div/div/div[1]/div[3]/form/div[1]/div/input").get(0);
//				HtmlSubmitInput button = (HtmlSubmitInput) page.getByXPath("/html/body/div[4]/div/div[2]/div/div/div[1]/div[3]/form/div[2]/input").get(0);
//				if(text != null && button != null)
//					out("hao123 获取到");
//				else {
//					continue;
//				}
//	//			System.out.println(text);
//	//			System.out.println(button);
//				text.setValueAttribute("李忠");
//				HtmlPage zlPage = button.click();
//				out("click over");
//				DomElement tg = zlPage.getElementById("ec_im_container");
//				if(tg == null) {
//					out("not found");
//					continue;
//				}
//				flag = false;
//				Iterable<DomElement> childs = tg.getChildElements();
//				Iterator<DomElement> iter = childs.iterator();
//				String urls = null;
//				while (iter.hasNext()) {
//					DomElement child = iter.next();
//					String childXml = child.asXml();
//					if(childXml.contains("李忠")){
//						DomNodeList<DomNode> as = child.getChildNodes();
//						DomNode urlNode = as.get(0);
//						String url = urlNode.getAttributes().getNamedItem("href").getNodeValue();
//						System.out.println(url);
//						urls = url;
//					}
//				}
//				HtmlPage yyPage = webClient.getPage(urls);
//				out(yyPage.asXml());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//	}

	public static void testHtmlUnit(){
		String baseUrl = "http://www.liyongyue.com/screen.html";
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
		Screen screen = new Screen();
		webClient.setDefaultScreen(screen);
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
		try {
			HtmlPage page = webClient.getPage(baseUrl);
			HtmlTextInput text = (HtmlTextInput) page.getByXPath("/html/body/div[4]/div/div[2]/div/div/div[1]/div[3]/form/div[1]/div/input").get(0);
			HtmlSubmitInput button = (HtmlSubmitInput) page.getByXPath("/html/body/div[4]/div/div[2]/div/div/div[1]/div[3]/form/div[2]/input").get(0);
			System.out.println(text);
			System.out.println(button);
			text.setValueAttribute("肿瘤");
			HtmlPage page2 = button.click();
			String result = page2.asText();
			System.out.println(page2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 183.141.73.47:3128 is bad!
	result:
	116.228.80.186:8080是高匿代理
	115.228.49.240:3128是高匿代理
	122.232.231.49:3128是高匿代理
	115.228.63.144:3128是高匿代理
	 * */
	
	public static void main(String[] args) {
		
		
		
		
		Baidu_htmlunit instance2 = new Baidu_htmlunit(BrowserVersion.CHROME);
		Screen screen_1680_1050 = new Screen();
		screen_1680_1050.setWidth(1680);
		screen_1680_1050.setHeight(1050);
		screen_1680_1050.setColorDepth(32);
		instance2.setScreen(screen_1680_1050);
		instance2.start();
	}

}
