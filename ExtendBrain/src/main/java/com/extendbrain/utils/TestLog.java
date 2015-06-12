package com.extendbrain.utils;

import org.apache.log4j.Logger;

public class TestLog {
	private static Logger logger = Logger.getLogger(TestLog.class.getClass());
	public static void testLogger(){
		logger.info("info");
		logger.debug("debug");
		logger.error("error");
	}
	
	public static void main(String[] args) {
		testLogger();
	}
}
