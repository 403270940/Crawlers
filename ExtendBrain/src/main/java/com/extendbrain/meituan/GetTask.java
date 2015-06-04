package com.extendbrain.meituan;

import java.util.HashMap;
import java.util.Map;

public class GetTask {
//	private final static String baseURL = "http://api.union.meituan.com/data/api";
//	// http://api.union.meituan.com/data/api?keyword=%E7%81%AB%E9%94%85&sort=1&limit=2
//	private static final String key = "ed00e761f5ed345f2ef9d3d9fbae292e291";
//	private static final String city = "北京";
//	private static final String category = "美食";
//	private static final String limit = "100";
//	private static String district_name = "茌平县";
//	private static String key_word = "火锅";
//	private static String sort = "1";
//	private static String offset = "0";
	private static Map<String, String> parameters = new HashMap<String, String>();
	private String city = "";
	private String district_name = "";
	private String category = "";
	private String key_word = "";
	private String sort = "1";
	private int limit = 1;
	private int offset = 0;
	
	public GetTask(String city, String district_name, String category,
			String key_word, String sort, int limit, int offset) {
		this.city = city;
		this.district_name = district_name;
		this.category = category;
		this.key_word = key_word;
		this.sort = sort;
		this.limit = limit;
		this.offset = offset;
	}
	
	
}
