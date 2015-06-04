package com.extendbrain.utils;

import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class ThreadPoolHttpClient {

	private ExecutorService exe = null;
	public static HashSet<String> threadSet = new HashSet<String>();
	private static final int POOL_SIZE = 10;
	public static int count = 0;
	private HttpClient client = null;
	String[] urls = null;
	public ThreadPoolHttpClient(String[] urls){
		this.urls = urls;
	}
	
	public void test() throws Exception{
		exe = Executors.newFixedThreadPool(POOL_SIZE);
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 2000);
		HttpConnectionParams.setSoTimeout(params, 4000);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, 
										PlainSocketFactory.getSocketFactory()));
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		cm.setMaxTotal(10);
		final HttpClient httpClient = new DefaultHttpClient(cm,params);
		HttpClientParams.setCookiePolicy(httpClient.getParams(),CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
		final String[] urisToGet = urls;
		
		for(int i = 0; i < urisToGet.length; i ++){
			HttpGet httpGet = new HttpGet(urisToGet[i]);
			exe.execute(new FetcherThread(httpClient, httpGet));
		}
		exe.shutdown();
	}
	
	
	
	public static void main(String[] args) {
		HashSet<String> urlSet = new HashSet<String>();
		String [] urls = {"http://xyq.cbg.163.com/cgi-bin/query.py?act=query&server_id=699&areaid=3&server_name=%E5%96%9C%E5%A4%A7%E6%99%AE%E5%A5%94&page=2&query_order=selling_time%20DESC&kindid="};
//		String [] urls = new String[365];
//		for(int i = 1; i <= 1;i++)
//		urls[i-1] = "http://xyq.cbg.163.com/cgi-bin/query.py?act=query&server_id=699&areaid=3&server_name=%E5%96%9C%E5%A4%A7%E6%99%AE%E5%A5%94&page="+i+"&query_order=selling_time%20DESC&kindid=";
		ThreadPoolHttpClient tphc = new ThreadPoolHttpClient(urls);
		try {
			tphc.test();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

