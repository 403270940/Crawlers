package com.extendbrain.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
	private static Properties config;
	static{
		config = new Properties();
		InputStream in = Object.class.getResourceAsStream("/conf/config.properties");
		try {
			config.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getProperty(String key){
		return config.getProperty(key);
	}

}
