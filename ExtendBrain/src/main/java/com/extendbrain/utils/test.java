package com.extendbrain.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class test {
	public static void testRandom(){
		Random r = new Random();
		double d = r.nextDouble();
		System.out.println(d);
	}
	
	public static void testDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
		Date date = new Date();
		String dt = df.format(date);
	}
	
	public static void main(String[] args) {
		testDate();
		System.out.println(System.currentTimeMillis());
	}
}
