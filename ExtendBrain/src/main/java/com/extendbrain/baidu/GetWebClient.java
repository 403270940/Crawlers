package com.extendbrain.baidu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.Screen;

public class GetWebClient {
	public static List<WebClient> webClientList = new ArrayList<WebClient>();
//	BrowserVersion myBrowserVersion = BrowserVersion.FIREFOX_38;
//	myBrowserVersion.setUserAgent("Mozilla/5.0 (X11; U; Linux i686; en-GB; rv:1.8.1.6) Gecko/20070914 Firefox/2.0.0.7");
//	Baidu_htmlunit instance1 = new Baidu_htmlunit(BrowserVersion.FIREFOX_31,"fly.x.tuxingsun.net",29769);

	public static Screen screen_1376_768 = null;
	public static Screen screen_1600_1200 = null;
	public static Screen screen_1680_1050 = null;
	public static Screen screen_1280_1024 = null;
	public static Screen screen_1920_1080 = null;
	
	static {
		
		screen_1920_1080 = new Screen();
		screen_1920_1080.setWidth(1920);
		screen_1920_1080.setAvailWidth(1920);
		screen_1920_1080.setHeight(1080);
		screen_1920_1080.setAvailHeight(1020);
		screen_1920_1080.setColorDepth(16);
		
		
		screen_1280_1024 = new Screen();
		screen_1280_1024.setWidth(1280);
		screen_1280_1024.setAvailWidth(1280);
		screen_1280_1024.setHeight(1024);
		screen_1280_1024.setAvailHeight(964);
		screen_1280_1024.setColorDepth(36);
		
		screen_1376_768 = new Screen();
		screen_1376_768.setWidth(1376);
		screen_1376_768.setAvailWidth(1376);
		screen_1376_768.setHeight(768);
		screen_1376_768.setAvailHeight(720);
		screen_1376_768.setColorDepth(24);
		
		screen_1680_1050 = new Screen();
		screen_1680_1050.setWidth(1680);
		screen_1680_1050.setAvailWidth(1680);
		screen_1680_1050.setHeight(1050);
		screen_1680_1050.setAvailHeight(1000);
		screen_1680_1050.setColorDepth(32);

		screen_1600_1200 = new Screen();
		screen_1600_1200.setWidth(1600);
		screen_1600_1200.setAvailWidth(1600);
		screen_1600_1200.setHeight(1200);
		screen_1600_1200.setAvailHeight(1100);
		screen_1600_1200.setColorDepth(16);
	}
	
	
	public static List<WebClient> getWebClients(){
		WebClient chromeClient = new WebClient(BrowserVersion.CHROME);
		chromeClient.setDefaultScreen(screen_1280_1024);
		chromeClient.getOptions().setAppletEnabled(true);
		webClientList.add(chromeClient);
		
		WebClient fireFox31 = new WebClient(BrowserVersion.FIREFOX_31);
		fireFox31.setDefaultScreen(screen_1376_768);
		fireFox31.getOptions().setAppletEnabled(true);
		webClientList.add(fireFox31);
		
		WebClient fireFox38 = new WebClient(BrowserVersion.FIREFOX_38);
		fireFox38.setDefaultScreen(screen_1600_1200);
		webClientList.add(fireFox38);
		
		WebClient ie8 = new WebClient(BrowserVersion.INTERNET_EXPLORER_8);
		ie8.setDefaultScreen(screen_1680_1050);
		ie8.getOptions().setAppletEnabled(true);
		webClientList.add(ie8);
		
		BrowserVersion xpFirefox = BrowserVersion.FIREFOX_38.clone();
		xpFirefox.setUserAgent("Mozilla/5.0 (Windows NT 5.1; WOW64; rv:22.0) Gecko/20100101 Firefox/22.0");
		xpFirefox.setBrowserLanguage("zh-cn");
		WebClient xpFF = new WebClient(xpFirefox);
		xpFF.setDefaultScreen(screen_1920_1080);
		webClientList.add(xpFF);
		
		
		BrowserVersion linuxFirefox = BrowserVersion.FIREFOX_38.clone();
		linuxFirefox.setUserAgent("Mozilla/5.0 (X11; U; Linux i686; en-GB; rv:1.8.1.6) Gecko/20070914 Firefox/2.0.0.7");
		linuxFirefox.setPlatform("X11");
		linuxFirefox.setBrowserLanguage("zh-cn");
		WebClient linuxFF = new WebClient(linuxFirefox);
		linuxFF.setDefaultScreen(screen_1920_1080);
		webClientList.add(linuxFF);
		
		
		BrowserVersion win8Firefox = BrowserVersion.FIREFOX_38.clone();
		win8Firefox.setUserAgent("Mozilla/5.0 (Windows NT 6.2; WOW64; rv:22.0) Gecko/20100101 Firefox/22.0");
		win8Firefox.setBrowserLanguage("zh-cn");
		WebClient win8FF = new WebClient(win8Firefox);
		win8FF.setDefaultScreen(screen_1920_1080);
		webClientList.add(win8FF);
		

		
		
//		ProxyConfig proxyConfig = new ProxyConfig("fly.x.tuxingsun.net", 29769);
//		for(int i = 0 ; i < 7; i++){
//			WebClient tmpClient = new WebClient(webClientList.get(i).getBrowserVersion(),proxyConfig.getProxyHost(),proxyConfig.getProxyPort());
//			tmpClient.setDefaultScreen(webClientList.get(i).getDefaultScreen());
//			webClientList.add(tmpClient);
//		}
		
		return webClientList;
	}

	public static void main(String[] args) {
		List<WebClient> clientList = getWebClients();
		for (int i = 0; i < clientList.size(); i++) {
			WebClient client = clientList.get(i);
			client.getOptions().setJavaScriptEnabled(true);
//			client.getOptions().setThrowExceptionOnScriptError(false);
			client.setAjaxController(new NicelyResynchronizingAjaxController()); 
			HtmlPage page;
			try {
				page = client.getPage("http://www.liyongyue.com/screen.html");
				System.out.println(page.asXml());
				System.out.println("\n\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
