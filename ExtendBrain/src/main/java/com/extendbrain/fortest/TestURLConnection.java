package com.extendbrain.fortest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestURLConnection {
	private static HttpURLConnection conn = null;
	public static void testConn(){
		try {
			conn = (HttpURLConnection)new URL("http://www.123.com?n=1").openConnection();
			conn.setConnectTimeout(1000);
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0");
			InputStream in = conn.getInputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line=br.readLine())!=null){
				System.out.println(line);
			}
			br.close();
			in.close();
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		while(true)
		testConn();
	}
}
