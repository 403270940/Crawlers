package com.extendbrain.zhihu;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.extendbrain.beans.Content;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;

public class Login_Zhihu {
	/*
	 * Accept	* / *
	Accept-Encoding	gzip, deflate
	Accept-Language	zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3
	Cache-Control	no-cache
	Connection	keep-alive
	Content-Length	98
	Content-Type	application/x-www-form-urlencoded; charset=UTF-8

	Host	www.zhihu.com
	Pragma	no-cache
	Referer	http://www.zhihu.com/
	X-Requested-With	XMLHttpRequest
	
	
	_xsrf	2f3df91743d0513dcf7788e0ef23bacb
	email	
	password	
	rememberme	y
	 */
	private static Protocol protocol ;
	public static String xsrf = null;
	private static String getXsrf(){
		String loginUrl = "http://www.zhihu.com";
		Content content = protocol.getOutput(loginUrl);
		String html = content.getContentString();
		String xsrfString = updateXsrf(html);
		return xsrfString;
	}
	public static String updateXsrf(String html){
		Document doc = Jsoup.parse(html);
		Elements hidden = doc.select("[name=\"_xsrf\"]");
		Element element = hidden.first();
		if(element == null) return null;
		String xsrfString = element.attr("value");
		if(xsrfString != null)
			xsrf = xsrfString;
		return xsrfString;
	}
	
	public static void login(String email,String password){
		protocol = ProtocolFactory.getSingletonProtocol("http://");
		
		xsrf = getXsrf();

		String rememberMe = "y";
		
		HttpPost post = new HttpPost("http://www.zhihu.com/login");
		post.setHeader("Accept", "*/*");
//		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		post.setHeader("Cache-Control", "no-cache");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Host", "www.zhihu.com");
		post.setHeader("Pragma", "no-cache");
		post.setHeader("Referer", "http://www.zhihu.com/");
		post.setHeader("X-Requested-With", "XMLHttpRequest");
		
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("_xsrf", xsrf));
		paramsList.add(new BasicNameValuePair("email", email));
		paramsList.add(new BasicNameValuePair("password", password));
		paramsList.add(new BasicNameValuePair("rememberme", rememberMe));
		
		try {
			UrlEncodedFormEntity params = new UrlEncodedFormEntity(paramsList,"UTF-8");
			post.setEntity(params);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Content content = protocol.postOutput(post);
		String result = content.getContentString();
		System.out.println(result);
		
		
	}
	
	public static void main(String[] args) {
		String email = "liyongyuea@126.com";
		String password = "a403270940";
		login(email,password);
		Content content = protocol.getOutput("http://www.zhihu.com/question/20248668");
		System.out.println(content.getContentString());
	}
}
