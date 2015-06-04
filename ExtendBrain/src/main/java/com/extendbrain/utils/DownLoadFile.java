package com.extendbrain.utils;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

public class DownLoadFile {
	private static HttpClient httpClient = new DefaultHttpClient();
	static{

		HttpProtocolParams.setUserAgent(httpClient.getParams(),
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; " 
						+"rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9"); 
		httpClient.getParams().setIntParameter("http.socket.timeout", 5000);
	} 
	public synchronized static String getFileNameByUrl(String url,String contentType)
	{
		url = url.substring(7);
		if(contentType.indexOf("html") != -1)
		{
			url = url.replaceAll("[\\?/:*|<>\"]","_")+".html";
			return url;
		}
		else
		{
			return url.replaceAll("[\\?/:*|<>\"]","_")+"."+
					contentType.substring(contentType.lastIndexOf("/")+1);
		}
	}
	
	public synchronized static void saveToLocal(String html,String filePath)
	{
		try{
			DataOutputStream out = new DataOutputStream(
					new FileOutputStream(filePath));
			
			out.writeBytes(html);
			out.flush();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static String GetHtml(String url){
		String filePath = null;
				ResponseHandler<String> responseHandler = new BasicResponseHandler();

		HttpGet httpGet = new HttpGet(url);
		httpGet.getParams().setIntParameter("http.socket.timeout", 5000);
		HttpResponse httpResponse;
		String html = null;
		HttpEntity httpEntity;
		try {
			httpResponse = httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			{
				System.err.println("Method failed:"+httpResponse.getStatusLine().getStatusCode());
				filePath = null;
			}
			httpEntity = httpResponse.getEntity();
			html = EntityUtils.toString(httpEntity, "UTF-8");
			filePath = "data/"+getFileNameByUrl(url, 
					httpResponse.getFirstHeader("Content-Type").getValue());
//			saveToLocal(html, filePath);
			EntityUtils.consume(httpEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			httpGet.releaseConnection();
		}
		
		return html;
	}
	
	public synchronized static String downloadFile(String url){
		String filePath = null;
				ResponseHandler<String> responseHandler = new BasicResponseHandler();

		HttpGet httpGet = new HttpGet(url);
		httpGet.getParams().setIntParameter("http.socket.timeout", 5000);
		HttpResponse httpResponse;
		String html = null;
		HttpEntity httpEntity;
		try {
			httpResponse = httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			{
				System.err.println("Method failed:"+httpResponse.getStatusLine().getStatusCode());
				filePath = null;
			}
			httpEntity = httpResponse.getEntity();
			html = EntityUtils.toString(httpEntity, "UTF-8");
			filePath = "data/"+getFileNameByUrl(url, 
					httpResponse.getFirstHeader("Content-Type").getValue());
			saveToLocal(html, filePath);
			EntityUtils.consume(httpEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			httpGet.releaseConnection();
		}
		
		return html;
	}
	
	public static void main(String[] args)
	{
		DownLoadFile downLoadfile = new DownLoadFile();
		String html = GetHtml("http://trade.500.com/jclq/index.php?playid=275&date=2014-03-28");
//		for(int i=1;i<=54;i++)
//			downLoadfile.downloadFile("http://fcasp.zjol.com.cn/zjfc/asp_ssq.asp?pagenum="+i);
		System.out.println(html);
	}
}
