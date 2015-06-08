package com.extendbrain.zhihu;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.extendbrain.beans.Content;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;
import com.extendbrain.utils.ConfigUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
/*
 * more http://www.zhihu.com/node/QuestionAnswerListV2
 * post :
 * _xsrf	2f130b345475d92baa88075bec3a15ec
 * method	next
 * params	{"url_token":27621722,"pagesize":100,"offset":0}
 */
public class Zhihu_Util {
	private static String BaseURL = "http://www.zhihu.com";
	private static Protocol protocol = ProtocolFactory.getSingletonProtocol("http://");
	public static String getQuestion(String id){
		String html = null;
		String questionBaseUrl = "http://www.zhihu.com/question/";
		String url = questionBaseUrl + id;
		Content content = protocol.getOutput(url);
		html = content.getContentString();
		Login_Zhihu.updateXsrf(html);
		return html;
	}
	
	private static String processJsonResult(String html){
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonParser().parse(html).getAsJsonObject();
		JsonArray jsonArray = jsonObject.getAsJsonArray("msg");
		html = jsonArray.toString();
		html = html.substring(2);
		html = html.substring(0, html.length()-2);
		html = html.replace("\\\"", "\"");
		html = html.replace("\\/", "/");
		html = html.replace("\\n", "");
		html = "<html>" + "<body>" +html + "</body>" + "</html>";
		return html;
	}
	
	public static String getQuestionJson(String id,int offset,int count){
		String url = "http://www.zhihu.com/node/QuestionAnswerListV2";
		String xsrf = Login_Zhihu.xsrf;
		String totalCount = String.valueOf(count);
		String currentOffset = String.valueOf(offset);
		String method = "next";
		String params = "{\"url_token\":" + id + ",\"pagesize\":" + count + ",\"offset\":" + offset + "}";
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("method", method));
		paramsList.add(new BasicNameValuePair("params", params));
		paramsList.add(new BasicNameValuePair("_xsrf", xsrf));
		System.out.println(xsrf);
		System.out.println(params);
		HttpPost post = new HttpPost(url);
		post.setHeader("Accept", "*/*");
		post.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		post.setHeader("Cache-Control", "no-cache");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Host", "www.zhihu.com");
		post.setHeader("Pragma", "no-cache");
		post.setHeader("Referer", "http://www.zhihu.com/question/" + id);
		post.setHeader("X-Requested-With", "XMLHttpRequest");
		try {
			UrlEncodedFormEntity paramsEntity = new UrlEncodedFormEntity(paramsList,"utf-8");
			post.setEntity(paramsEntity);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Content content = protocol.postOutput(post);
		String html = content.getContentString();
		html = processJsonResult(html);
		return html;
	}
	
	public static int getAnswerCount(String html){
		Document doc = Jsoup.parse(html);		
		String ifMoreTag = "#zh-question-answer-num";
		String answerCount = doc.select(ifMoreTag).first().attr("data-num");
		return Integer.valueOf(answerCount.trim());
	}

	
	//zu-button-more
	public static List<Answer> getAnswerFromQuestionPage(String html){
		List<Answer> answerList = new ArrayList<Answer>();
		Document doc = Jsoup.parse(html);
	
		String answerClassName = ".zm-item-answer"; 
		Elements answers = doc.select(answerClassName);
		for(Element answer : answers ){
			Answer ans = new Answer();
			int upCount = Integer.valueOf(answer.select(".up .count").first().text().trim());
			ans.setUpCount(upCount);

			String commentsCountString = answer.select(".z-icon-comment").first().parent().text().trim();
			System.out.println("commentCount:"+commentsCountString);
			if(!commentsCountString.equals("添加评论")){
				int commentCount = Integer.valueOf(commentsCountString.split(" ")[0]);
				ans.setComentCount(commentCount);
			}
					
			Elements userInfoElements = answer.select(".zm-item-answer-author-wrap").first().children();
			if(userInfoElements.size()>=2){
				String userURL = userInfoElements.get(0).text();
				ans.setUserURL(userURL);
				String userName = userInfoElements.get(1).text();
				ans.setUserURL(userName);
				if(userInfoElements.size()>=3){
					String userDesc = userInfoElements.get(2).text();
					ans.setUserURL(userDesc);
				}
			}else{
				ans.setUserName("匿名用户");
			}
			
			String answerText = answer.select(".zm-editable-content").first().text();
			ans.setAnswerText(answerText);
			String answerTime = answer.select(".answer-date-link-wrap a").first().text();
			ans.setAnswerTime(answerTime);
			
			System.out.println(ans);
			answerList.add(ans);
		}
		return answerList;
	}
	
	public static void getAllUserFromQuestionPage(String html){
		Document doc = Jsoup.parse(html);
		
		String answerClassName = "div .zm-item-answer";
		Elements answers = doc.select(answerClassName);
		
		String className = ".answer-head";
		Elements elements = doc.select(className);
		//up count   	div .zm-item-answer .up .count
		//commentcount 	div .zm-item-answer .z-icon-comment text()
		//userURL   	div .zm-item-answer .zm-item-answer-author-wrap [0]
		//username   	div .zm-item-answer .zm-item-answer-author-wrap [1]
		//userdesc   	div .zm-item-answer .zm-item-answer-author-wrap [2]
		//answer     	div .zm-item-answer .zm-editable-content  text()
		//time       	div .zm-item-answer .answer-date-link-wrap a text()
		for(Element element : elements){
			Elements elementss = element.select("h3.zm-item-answer-author-wrap a");
			if(elementss.size() >= 2){
				String userURL = BaseURL + elementss.attr("href");
				String userName = elementss.get(1).text();
				System.out.println(userURL);
				System.out.println(userName);
			}
		}
	}
	
	public static List<Answer> getALLAnswerOfQuestion(String questionId){
		List<Answer> answerList = new ArrayList<Answer>();
		String html = getQuestion(questionId);
		List<Answer> resultList = getAnswerFromQuestionPage(html);
		answerList.addAll(resultList);
		int answerCount = getAnswerCount(html);
		
		for(int i = 50; i < answerCount; i=i+50){
			int offset = i;
			int count = (answerCount - i) > 50? 50 : answerCount - i;
			String jsonhtml = getQuestionJson(questionId,offset,count);
			try {
				String utfhtml = new String(jsonhtml.getBytes("unicode"),"utf-8");
				System.out.println(jsonhtml);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			List<Answer> jsonList = getAnswerFromQuestionPage(jsonhtml);
			answerList.addAll(jsonList);
		}
		return answerList;
	}
	
	
	public static void main(String[] args) {
		String email = ConfigUtil.getProperty("zhihu_userName");
		String password = ConfigUtil.getProperty("zhihu_password");
		Login_Zhihu.login(email,password);
//		String questionId = "27621722";
//		String questionHtml = getQuestion(questionId);
//		getAllUserFromQuestionPage(questionHtml);
		Scanner sc = new Scanner(System.in);

		while(true){
			try {
			String line = sc.nextLine();	
			System.out.println("line: " + line);
			int intid = Integer.valueOf(line.trim());
			String id = String.valueOf(intid);
			if(id.length()!=8)
				throw new Exception("The id Length should be 8");
			//String html = getQuestion(id);
			List<Answer> answerList = getALLAnswerOfQuestion(id);
			System.out.println(answerList.size());
//			getAllUserFromQuestionPage(html);
//			System.out.println("id: " + id);
//			Thread.sleep(1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println(getQuestion(questionId));
//		String result = getQuestionJson(questionId);
//		System.out.println(result);
		
	}
}
