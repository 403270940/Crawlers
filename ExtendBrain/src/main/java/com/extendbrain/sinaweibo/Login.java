package com.extendbrain.sinaweibo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.extendbrain.beans.Content;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.sun.org.omg.SendingContext.CodeBasePackage.URLSeqHelper;


public class Login {
	//https://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.15)&_=1429839435485
	private static String baseUrl = "https://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.15)&_=";
	private static String cdult = "3";
	private static String domain = "sina.com.cn";
	private static String encoding = "UTF-8";
	private static String entry = "sso";
	private static String from = "null";
	private static String gateway = "1";
	private static String pagerefer = "";
	private static String prelt = "0";
	private static String returntype = "TEXT";
	private static String savestate = "30";
	private static String service = "sso";
	private static String sr = "1366*768";
	private static String useticket = "0";
	private static String vsnf = "1";
	private static List<NameValuePair> paramList;
	private static Protocol protocol = null;
	private static HttpPost post = null;
	private static void init(String userName,String password){
		String su = Base64.encode(URLEncoder.encode(userName).getBytes());
		String sp = password;
		protocol = ProtocolFactory.getProtocol("http");
		paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("cdult", cdult));
		paramList.add(new BasicNameValuePair("domain", domain));
		paramList.add(new BasicNameValuePair("encoding", encoding));
		paramList.add(new BasicNameValuePair("entry", entry));
		paramList.add(new BasicNameValuePair("from", from));
		paramList.add(new BasicNameValuePair("gateway", gateway));
		paramList.add(new BasicNameValuePair("pagerefer", pagerefer));
		paramList.add(new BasicNameValuePair("prelt", prelt));
		paramList.add(new BasicNameValuePair("returntype", returntype));
		paramList.add(new BasicNameValuePair("savestate", savestate));
		paramList.add(new BasicNameValuePair("service", service));
		paramList.add(new BasicNameValuePair("sp", sp));
		paramList.add(new BasicNameValuePair("sr", sr));
		paramList.add(new BasicNameValuePair("su", su));
		paramList.add(new BasicNameValuePair("useticket", useticket));
		paramList.add(new BasicNameValuePair("vsnf", vsnf));	
		String url = baseUrl + System.currentTimeMillis();
		post = new HttpPost(url);
		post.setHeader("Accept", "	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setHeader("Accept-Encoding", "Accept-Encoding");
		post.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		post.setHeader("Cache-Control", "no-cache");
		post.setHeader("Connection", "Keep-Alive");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.setHeader("Host", "login.sina.com.cn");
		post.setHeader("Origin", "http://login.sina.com.cn");
		post.setHeader("Pragma", "no-cache");
		post.setHeader("Referer", "http://login.sina.com.cn/signup/signin.php?entry=sso");
		try {
			UrlEncodedFormEntity params = new UrlEncodedFormEntity(paramList,"UTF-8");
			post.setEntity(params);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			post = null;
			e.printStackTrace();
		}
		
	}
	public static void login(String userName,String password){
		init(userName,password);
		if(post == null){
			System.out.println("post is null");
			return;
		}
		Content content = protocol.postOutput(post);
		if(content == null){
			System.out.println("post failed");
			return;
		}
		String result = content.getContentString();
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
		JsonArray jsonArray = jsonObject.getAsJsonArray("crossDomainUrlList");
		String resultUrl = "";
		for(int i = 0 ; i < jsonArray.size(); i ++){
			resultUrl = gson.fromJson(jsonArray.get(i), String.class);
			System.out.println(resultUrl);
			protocol.getOutput(resultUrl);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Content finalContent = protocol.getOutput("http://weibo.com/u/2438720734/home?wvr=5");
		System.out.println(finalContent.getContentString());
	}
	public static void main(String[] args) {
		String userName = "";
		String password = "";
		login(userName,password);
		
	}
}
