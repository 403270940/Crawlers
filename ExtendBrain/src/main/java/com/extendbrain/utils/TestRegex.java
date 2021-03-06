package com.extendbrain.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.extendbrain.zhihu.exception.ZhihuLoginException;

import sun.misc.Regexp;

public class TestRegex {
	
	public static void testResultCode(){
		
		String result = "{\"r\":0,\"msg\":\"\\/\"}";
		String resultCode = "init";
		String regexString = "(?<=\"r\":)(.+)(?=,)";
		Pattern p2 = Pattern.compile(regexString);
		Matcher m2 = p2.matcher(result);
		if (m2.find())
			resultCode = m2.group();
		System.out.println(resultCode);
	}
	public static void testCharset(){
		String source = "charset=utf-8";
        String pattern = "(?i)\bcharset=(?<charset>[-a-zA-Z_0-9]+)";  
		Pattern p2 = Pattern.compile("(?<=charset=?\")(.+)(?=\";)");
//        Pattern p2 = Pattern.compile(pattern);
		Matcher m2 = p2.matcher(source);
		if (m2.find())
			System.out.println(m2.group());
	}
	
	public static void test(){
		String IMEIReg = "^\\d{15}$";
		String IMEI = "355065053311001";
		
		String IMSIReg = "^4600[0-3]{1}\\d{10}$";
		String IMSI = "460002289000168";
		
		String MACReg = "^[0-9a-fA-F]{2}(:[0-9a-fA-F]{2}){5}$";
		String MAC = "F2:D2:E5:E8:03:F6";
		
		Pattern pattern = Pattern.compile(IMSIReg);
		Matcher matcher = pattern.matcher(IMSI);
		if(matcher.find())
			System.out.println("True");
		else 
			System.out.println("False");
	}
	
	
	public static void main(String[] args) {
		test();
		
	}
}
