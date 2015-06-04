package com.extendbrain.searchdomain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

public  class DomainFeeder extends Thread {
	private static final String baseURL = "http://panda.www.net.cn/cgi-bin/check.cgi?area_domain=";
	private static String[] suffix = new String[] { ".com", ".cn", ".cc" };
	private static Vector<String> domains = new Vector<String>();
	private static Vector<String> availableDomains = new Vector<String>();
	public AtomicInteger unavailableCount = new AtomicInteger(0);
	public static Vector<String> cities = new Vector<String>();
	public static final String domain_exist = "211";
	public static final String domain_not_exist = "210 : Domain name is available";
	private Logger logger = Logger.getLogger(this.getClass());

	public static void getDomainByLen() {
		char[] chars = new char[36];
		for (int i = 0; i < 10; i++) {
			chars[i] = (char) (i + '0');
		}
		for (int i = 0; i < 26; i++) {
			chars[i + 10] = (char) (i + 'a');
		}

		for (int i = 0; i < 36; i++)
			for (int j = 0; j < 36; j++) {
				String name = chars[i] + "" + chars[j];
				for (int k = 0; k < suffix.length; k++) {
					String domainName = name + suffix[k];
					domains.add(domainName);
				}
			}
	}

	public static void getTuanCities() {
		File file = new File("city.txt");
		if (!file.exists())
			System.out.println("not exist");
		try {
			InputStream in = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				String domainName = "tuan" + line + ".com";
				domains.add(domainName);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static void getCities() {
		File file = new File("city.txt");
		if (!file.exists())
			System.out.println("not exist");
		try {
			InputStream in = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				for (int k = 0; k < suffix.length; k++) {
					String domainName = line + suffix[k];
					domains.add(domainName);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void run() {
		getTuanCities();
	}
}
