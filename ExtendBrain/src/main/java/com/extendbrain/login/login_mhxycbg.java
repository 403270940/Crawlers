package com.extendbrain.login;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class login_mhxycbg implements BasicLogin{

public boolean login(HttpClient httpClient){
		
		Random r = new Random();
		double stamp = r.nextDouble();
		String imgURL = "http://xyq.cbg.163.com/cgi-bin/show_captcha.py?stamp=" + stamp;
		HttpGet getImage = new HttpGet(imgURL);
		HttpResponse response;
		try {
			response = httpClient.execute(getImage);
			File imageFile = new File("img.jpg");
			FileOutputStream fo = new FileOutputStream(imageFile);
			fo.write(EntityUtils.toByteArray(response.getEntity()));
			
			//request url and image and set-cookie value
			//identify image and get string identify
			Scanner scanner = new Scanner(System.in);
			String identify = scanner.nextLine();
			
			//check identify status
			String checkUrlString = "http://xyq.cbg.163.com/cgi-bin/show_captcha.py?act=check_login_captcha&captcha=" + identify;
			//request url and get set cookie value cookie and return cookie
			HttpGet checkGet = new HttpGet(checkUrlString);
			HttpResponse checkResponse = httpClient.execute(checkGet);
			System.out.println(EntityUtils.toString(checkResponse.getEntity()));
			
			String loginURL = "http://xyq.cbg.163.com/cgi-bin/login.py";
			HttpPost loginPost = new HttpPost(loginURL);
			List<NameValuePair> paramaList = new ArrayList<NameValuePair>();
			paramaList.add(new BasicNameValuePair("act", "do_anon_auth"));
			paramaList.add(new BasicNameValuePair("image_value", identify));
			paramaList.add(new BasicNameValuePair("next_url", "http://xyq.cbg.163.com/static_file/699/buy_equip_list/equip_list1.html"));
			paramaList.add(new BasicNameValuePair("server_id", "699"));
			paramaList.add(new BasicNameValuePair("server_name", "Ï²´óÆÕ±¼"));
			loginPost.setEntity(new UrlEncodedFormEntity(paramaList));
			HttpResponse loginResponse = httpClient.execute(loginPost);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//System.out.println(EntityUtils.toString(loginResponse.getEntity(),"gb2312"));
		return true;
	}
}
