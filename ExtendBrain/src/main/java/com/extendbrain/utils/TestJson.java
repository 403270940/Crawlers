package com.extendbrain.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.extendbrain.zhihu.Zhihu_Util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TestJson {
	private static String fileName = "src/main/java/json.txt";
//	private static void test(){
//		Gson gson = new Gson();
//		JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
//		JsonArray jsonArray = jsonObject.getAsJsonArray("crossDomainUrlList");
//		String resultUrl = "";
//	}
	
	private static String processJsonResult(String html){
		html = html.substring(2);
		html = html.substring(0, html.length()-2);
		html = html.replace("\\\"", "\"");
		html = html.replace("\\/", "/");
		html = html.replace("\\n", "");
		html = "<html>" + "<body>" +html + "</body>" + "</html>";
		return html;
	}
	
	public static void main(String[] args) {
		File file = new File(fileName);
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((line=br.readLine())!=null){
//				String tmp = new String(line.getBytes("unicode"),"utf-8");
//				System.out.println(line);
				sb.append(line);
				}
			String result = sb.toString();
			Gson gson = new Gson();
			JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
			JsonArray jsonArray = jsonObject.getAsJsonArray("msg");
			String html = jsonArray.toString();
			html = html.substring(2);
			html = html.substring(0, html.length()-2);
			html = html.replace("\\\"", "\"");
			html = html.replace("\\/", "/");
			html = html.replace("\\n", "");
			html = "<html>" + "<body>" +html + "</body>" + "</html>";
			Document doc = Jsoup.parse(html);
			System.out.println(doc);
//			String answerClassName = ".zm-item-answer"; 
//			Elements answers = doc.select(answerClassName);
//			System.out.println(answers.size());
			Zhihu_Util.getAnswerFromQuestionPage(123,html);
//			JsonArray jsonArray = jsonObject.getAsJsonArray("crossDomainUrlList");
//			String resultUrl = "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
