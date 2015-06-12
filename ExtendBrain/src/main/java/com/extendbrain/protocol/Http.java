package com.extendbrain.protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.extendbrain.beans.Content;
import com.extendbrain.beans.URLDatum;

public class Http implements Protocol{
	private static HttpClient httpClient;
	private static SSLSocketFactory ssf = null;
	private final String defaultUserAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0";
	static{
        try {
        	SSLContext ctx = SSLContext.getInstance("SSL");
	 
        //Implementation of a trust manager for X509 certificates   
		X509TrustManager tm = new X509TrustManager() {

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] chain, String authType)
					throws java.security.cert.CertificateException {
				// TODO Auto-generated method stub
				
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] chain, String authType)
					throws java.security.cert.CertificateException {
				// TODO Auto-generated method stub
				
			}

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				// TODO Auto-generated method stub
				return null;
			}   
		
		};   
		ctx.init(null, new TrustManager[] { tm }, null);   
		ssf = new SSLSocketFactory(ctx);   
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	
	public Http(){
		
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 60000);
		HttpConnectionParams.setSoTimeout(params, 60000);
		HttpClientParams.setCookiePolicy(params, CookiePolicy.BROWSER_COMPATIBILITY);
		params.setParameter(CoreProtocolPNames.USER_AGENT, defaultUserAgent);
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, ssf));
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		cm.setMaxTotal(10);
		httpClient = new DefaultHttpClient(cm, params);
	}
	
	public void setUserAgent(String userAgent){
		httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, userAgent);
	}
	
	public boolean setProxy(String hostname, int port){
		HttpHost host = new HttpHost(hostname, port);
		httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, host);
		return true;
	}
	
	public Content getOutput(String url) {
		// TODO Auto-generated method stub
		HttpGet httpGet = new HttpGet(url);
		Content content = null;
		try {
			content = new Content();
			HttpResponse httpResponse = httpClient.execute(httpGet);
			boolean ifGZip = false;
			HttpEntity entity = httpResponse.getEntity();
			Header encodingHeader = httpResponse.getEntity().getContentEncoding();
			if(encodingHeader != null)
				ifGZip = encodingHeader.getValue().contains("gzip");
			if(ifGZip)
				entity = new GzipDecompressingEntity(entity);
			byte[] contents = EntityUtils.toByteArray(entity);
			content.setUrl(url);
			content.setContent(contents);
			content.setContentType(entity.getContentType().getValue());
			String charset = getCharset(new String(contents));
			content.setCharSet(charset);
			EntityUtils.consume(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			content = null;
			e.printStackTrace();
		}
		return content;
	}
	
	public Content getOutput(HttpGet httpGet) {
		// TODO Auto-generated method stub
		Content content = null;
		try {
			content = new Content();
			HttpResponse httpResponse = httpClient.execute(httpGet);
			boolean ifGZip = false;
			HttpEntity entity = httpResponse.getEntity();
			Header encodingHeader = httpResponse.getEntity().getContentEncoding();
			if(encodingHeader != null)
				ifGZip = encodingHeader.getValue().contains("gzip");
			if(ifGZip)
				entity = new GzipDecompressingEntity(entity);
			byte[] contents = EntityUtils.toByteArray(entity);
			content.setUrl(httpGet.getURI().toString());
			content.setContent(contents);
			content.setContentType(entity.getContentType().getValue());
			String charset = getCharset(new String(contents));
			content.setCharSet(charset);
			EntityUtils.consume(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			content = null;
			e.printStackTrace();
		}
		return content;
	}
	
	public Content getOutput(String url, URLDatum datum) {
		// TODO Auto-generated method stub
		HttpGet httpGet = new HttpGet(url);
		Content content = null;
		try {
			content = new Content();
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			boolean ifGZip = httpResponse.getEntity().getContentEncoding().getValue().contains("gzip");
			if(ifGZip)
				entity = new GzipDecompressingEntity(entity);
			byte[] contents = EntityUtils.toByteArray(entity);
			content.setUrl(datum.getUrl());
			content.setContent(contents);
			content.setContentType(entity.getContentType().getValue());
			String charset = getCharset(new String(contents));
			content.setCharSet(charset);
			EntityUtils.consume(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			content = null;
			e.printStackTrace();
		}
		return content;
	}
	
	public Content postOutput(HttpPost post){
		Content content = null;
		try {
			content = new Content();
			HttpResponse httpResponse = httpClient.execute(post);
			boolean ifGZip = false;
			HttpEntity entity = httpResponse.getEntity();
			Header encodingHeader = httpResponse.getEntity().getContentEncoding();
			if(encodingHeader != null)
				ifGZip = encodingHeader.getValue().contains("gzip");
			if(ifGZip)
				entity = new GzipDecompressingEntity(entity);
			byte[] contents = EntityUtils.toByteArray(entity);
			content.setUrl(post.getURI().toString());
			content.setContent(contents);
			content.setContentType(entity.getContentType().getValue());
			
			String charset = getCharset(new String(contents));
			content.setCharSet(charset);
			EntityUtils.consume(entity);
		} catch (Exception e) {
			content = null;
			e.printStackTrace();
			// TODO: handle exception
		}
		return content;
	}
	private String getCharset(String html){
		String chs = "utf-8";
		Pattern p2 = Pattern.compile("(?<=charset=?\")(.+)(?=\")");
		Matcher m2 = p2.matcher(html);
		if (m2.find())
			return m2.group();
//		Pattern p = Pattern.compile("(?<=charset=)(.+)(?=\")");
//		Matcher m = p.matcher(html);
//		if (m.find())
//			return m.group();
		Pattern p1 = Pattern.compile("(?<=encoding=\")(.+)(?=\")");
		Matcher m1 = p1.matcher(html);
		if (m1.find())
			return m1.group();
//		System.out.println(chs);
		return chs;

	}

}
