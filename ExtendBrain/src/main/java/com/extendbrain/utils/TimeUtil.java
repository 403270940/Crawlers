package com.extendbrain.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	public static String getCurrentTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd hh:mm:ss");
		Date date = new Date();
		String dt = df.format(date);
		return dt;
	}
}
