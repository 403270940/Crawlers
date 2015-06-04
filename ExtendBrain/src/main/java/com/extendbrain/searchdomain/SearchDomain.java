package com.extendbrain.searchdomain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;

import com.extendbrain.beans.Content;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;

public class SearchDomain extends Thread {

	private static final String baseURL = "http://panda.www.net.cn/cgi-bin/check.cgi?area_domain=";
	private static String[] suffix = new String[] { ".com" };
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

	public static void getCities(String prefix) {
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
					String domainName = prefix + line + suffix[k];
					domains.add(domainName);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static class DomainFeeder extends Thread {
		@Override
		public void run() {
			getCities("");
		}
	}

	public class DomainChecker extends Thread {
		@Override
		public void run() {
			Protocol protocol = ProtocolFactory.getProtocol(baseURL);
			int delay = 1000;
			while (!domains.isEmpty()) {
				String domain = domains.remove(0);
				String url = baseURL + domain;
				// System.out.println(url);
				Content content = null;
				content = protocol.getOutput(url);
				if (content == null) {
					domains.add(domain);
					System.out.println("content is null");
					break;
				}
				String result = content.getContentString();
				if (result.contains(domain_exist)) {
					logger.error(url + " is unavailable");
					unavailableCount.incrementAndGet();
				} else if (result.contains(domain_not_exist)) {
					availableDomains.add(url);
					logger.info(url + " is available");
				} else {
					domains.add(domain);
					System.out.println(content.getContentString());
					logger.error("dleay: " + delay + " is too frequency");
					delay *= 2;
					// System.out.println(unavailableCount);
				}
				try {
					sleep(delay);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void run() {
		DomainFeeder domainFeeder = new DomainFeeder();
		domainFeeder.start();
		int threadCount = 1;
		for (int i = 0; i < threadCount; i++) {
			DomainChecker domainChecker = new DomainChecker();
			domainChecker.start();
		}
	}

	public static void main(String[] args) {
		new SearchDomain().start();
		int resultCount = domains.size();
	}
}
