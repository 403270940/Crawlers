package com.extendbrain.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class FetcherThread extends Thread{

	private final HttpClient httpClient;
	private final HttpContext context;
	private final HttpGet httpGet;
	private BasicCookieStore cookieStore;
	public FetcherThread(HttpClient httpClient,HttpGet httpGet){
		this.httpClient = httpClient;
		this.context = new BasicHttpContext();
		this.cookieStore = new BasicCookieStore();
		this.context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		this.httpGet = httpGet;

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.setName("threadsPoolClient");
		try {
			Thread.sleep(1000);
			login();
			get();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	public String login() throws Exception{
		
		Random r = new Random();
		double stamp = r.nextDouble();
		String imgURL = "http://xyq.cbg.163.com/cgi-bin/show_captcha.py?stamp=" + stamp;
		HttpGet getImage = new HttpGet(imgURL);
		HttpResponse response = httpClient.execute(getImage, context);
		
		File imageFile = new File("img.jpg");
		FileOutputStream fo = new FileOutputStream(imageFile);
		fo.write(EntityUtils.toByteArray(response.getEntity()));
		

		Scanner scanner = new Scanner(System.in);
		String identify = scanner.nextLine();
		
		//check identify status
		String checkUrlString = "http://xyq.cbg.163.com/cgi-bin/show_captcha.py?act=check_login_captcha&captcha=" + identify;
		//request url and get set cookie value cookie and return cookie
		HttpGet checkGet = new HttpGet(checkUrlString);
		HttpResponse checkResponse = httpClient.execute(checkGet, context);
		System.out.println(EntityUtils.toString(checkResponse.getEntity()));
		
		String loginURL = "http://xyq.cbg.163.com/cgi-bin/login.py";
		HttpPost checkPost = new HttpPost(loginURL);
		List<NameValuePair> paramaList = new ArrayList<NameValuePair>();
		paramaList.add(new BasicNameValuePair("act", "do_anon_auth"));
		paramaList.add(new BasicNameValuePair("image_value", identify));
		paramaList.add(new BasicNameValuePair("next_url", "http://xyq.cbg.163.com/cgi-bin/query.py?act=query&server_id=699&areaid=3&server_name=%E5%96%9C%E5%A4%A7%E6%99%AE%E5%A5%94&page=2&query_order=selling_time%20DESC&kindid="));
		paramaList.add(new BasicNameValuePair("server_id", "699"));
		paramaList.add(new BasicNameValuePair("server_name", "喜大普奔"));
		checkPost.setEntity(new UrlEncodedFormEntity(paramaList));
		
		HttpResponse loginResponse = httpClient.execute(checkPost, context);
//		System.out.println(EntityUtils.toString(loginResponse.getEntity(),"gb2312"));
//		HttpGet httpGet = new HttpGet("http://xyq.cbg.163.com/cgi-bin/query.py?act=query&server_id=699&areaid=3&server_name=%E5%96%9C%E5%A4%A7%E6%99%AE%E5%A5%94&page=2&query_order=selling_time%20DESC&kindid=");
//		HttpResponse response1 = httpClient.execute(httpGet, context);
//		System.out.println(EntityUtils.toString(response1.getEntity(),"gb2312"));
		return null;
	}
	public void get(){
		try {
			HttpGet httpGet = new HttpGet("http://xyq.cbg.163.com/cgi-bin/query.py?act=query&server_id=699&areaid=3&server_name=%E5%96%9C%E5%A4%A7%E6%99%AE%E5%A5%94&page=2&query_order=selling_time%20DESC&kindid=");
			HttpResponse response = httpClient.execute(httpGet,this.context);
			HttpEntity entity = response.getEntity();
			HttpParams params = httpClient.getParams();
			//			System.out.println(this.httpGet.getURI()+": status" + response.getStatusLine().toString());
			if(entity != null){
				System.out.println(this.httpGet.getURI()+": status" + response.getStatusLine().toString() +
						 this.hashCode());
				String charset = EntityUtils.getContentCharSet(entity);
				String sb = EntityUtils.toString(entity,"gb2312");
				System.out.println(sb);
//				Document document =Jsoup.parse(sb);
//				Elements nodes = document.select("#soldList");
//				Element element = nodes.get(0);
//				Elements trs = element.select("tr td a img");
//				for(Element tr : trs){
//					if(tr.attr("data_equip_name").equals("金钱"))
//						ThreadPoolHttpClient.count++;
//				}
//				System.out.println(ThreadPoolHttpClient.count);
//					System.out.println(tr.attr("data_equip_name"));
//				System.out.println(sb);
			}
			else {
				System.out.println(response.getStatusLine());
			}
			EntityUtils.consume(entity);

		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			httpGet.releaseConnection();
		}
	}
}
