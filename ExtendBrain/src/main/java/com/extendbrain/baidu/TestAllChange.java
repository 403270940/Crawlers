package com.extendbrain.baidu;

import java.util.Iterator;
import java.util.List;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class TestAllChange extends Thread{
	private WebClient webClient = null;
	public TestAllChange(){
		webClient = new WebClient();
	}
	
	public TestAllChange(WebClient webClient){
		this.webClient = webClient;
	}
	
	private void out(String msg){
		System.out.println(getName() + ":" + msg);
	}
	
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		webClient.getOptions().setThrowExceptionOnScriptError(false);
//		String baseUrl = "http://www.liyongyue.com/screen.html";
////		String baseUrl = "http://xin.bjyft.net";
//		boolean flag = true;
//        webClient.getOptions().setCssEnabled(false);
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
//        while(flag){
//			try {
//				HtmlPage page = webClient.getPage(baseUrl);
//				out(page.asXml());
//				flag = false;
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//	}
	
	
	@Override
	public void run(){
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
//				text.setValueAttribute("北京御方堂");
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
//					
//					DomElement child = iter.next();
//					out(child.asXml());
//					String childXml = child.asXml();
//					if(childXml.contains("御方堂")){
//						DomNodeList<DomNode> as = child.getChildNodes();
//						DomNode urlNode = as.get(0);
//						String url = urlNode.getAttributes().getNamedItem("href").getNodeValue();
//						System.out.println(url);
//						urls = url;
//					}
//				}
//				HtmlPage yyPage = webClient.getPage(urls);
//				out(yyPage.asXml());
//				
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//	}

	
	public static void main(String[] args) {
		List<WebClient> webClientList = GetWebClient.getWebClients();
		int count = 0;
		for(WebClient wb : webClientList){
			if(count>=3)
				break;
			count ++;
			wb.getOptions().setProxyConfig(new ProxyConfig("115.228.63.246", 3128));
			wb.getOptions().setThrowExceptionOnScriptError(false);
			wb.setAjaxController(new NicelyResynchronizingAjaxController()); 		
			TestAllChange tAllChange = new TestAllChange(wb);
			tAllChange.start();
//			break;
		}
	}
}
