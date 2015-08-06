package com.extendbrain.zhihu;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.extendbrain.beans.Content;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;
import com.extendbrain.utils.TimeUtil;
import com.extendbrain.zhihu.exception.ZhihuLoginException;

public class Zhihu implements ZhihuBase{
	
	public Protocol protocol = null;
	private boolean ifLogin = false;
	private static Zhihu instance = null;
	private String xsrf = null;
	private Logger logger = Logger.getLogger(this.getClass());
	
	
	public static ZhihuBase getInstance(){
		if(instance==null){
			synchronized (Zhihu.class) {
				if(instance==null)
					instance = new Zhihu();
			}
		}
		return instance;
	}
	
	public  void addCookie(){
		protocol = ProtocolFactory.getSingletonProtocol("http://");	
		
	}
	public void setCookie(String cookie){
		protocol = ProtocolFactory.getSingletonProtocol("http://");
		protocol.setCookie(cookie);
	}
	public boolean login(String userName, String password) throws Exception {
		// TODO Auto-generated method stub
		logger.info("UserName:" + userName +" PassWord:"+password);
		protocol = ProtocolFactory.getSingletonProtocol("http://");	
		boolean ifInitedXsrf = initXsrf();
		if(!ifInitedXsrf){
			throw new ZhihuLoginException("Login failed!Can not found xsrf label in the login page!");
		}
		String rememberMe = "true";	
		HttpPost post = new HttpPost("http://www.zhihu.com/login/email");
		post.setHeader("Accept", "*/*");
		post.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		post.setHeader("Cache-Control", "no-cache");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Host", "www.zhihu.com");
		post.setHeader("Pragma", "no-cache");
		post.setHeader("Referer", "http://www.zhihu.com/");
		post.setHeader("X-Requested-With", "XMLHttpRequest");
		
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("_xsrf", xsrf));
		paramsList.add(new BasicNameValuePair("email", userName));
		paramsList.add(new BasicNameValuePair("password", password));
		paramsList.add(new BasicNameValuePair("rememberme", rememberMe));
		
		try {
			UrlEncodedFormEntity params = new UrlEncodedFormEntity(paramsList,"UTF-8");
			post.setEntity(params);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Content content = protocol.postOutput(post);
		String result = content.getContentString();
		//{"r":0,"msg":"\/"}
		String resultCode = null;
		String regexString = "(?<=\"r\":)(.+)(?=,)";
		Matcher codeMatcher = Pattern.compile(regexString).matcher(result);
		if (codeMatcher.find())
			resultCode = codeMatcher.group();
		
		
		String resultMSG = null;
		String msgRegexString = "(?<=\"msg\":)(.+)(?=\")";
		Matcher msgMatcher = Pattern.compile(regexString).matcher(result);
		if (msgMatcher.find())
			resultMSG = msgMatcher.group();
		
		if(resultCode.equals("0")){
			System.out.println("登录成功");	
			logger.info("登陆成功 ");
			return true;
		}else if(resultCode == null){
			throw new ZhihuLoginException("登录失败!返回结果:"+result);
		}else {
			throw new ZhihuLoginException("登录失败!返回结果:"+resultMSG);
		} 
	}

	public List<Object> getQuestionsByTag(String tag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Answer> getAllAnswerOfQuestion(int questionId) {
		// TODO Auto-generated method stub
		logger.info("Get all quesiton of id " + questionId);
		List<Answer> answerList = GetAllAnswerOfQuestion.getALLAnswerOfQuestion(this, questionId);
		logger.info("The count of answer of " + questionId + "is " + answerList.size());
		return answerList;
	}

	public boolean upAnswer(int questionId,int answerId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean downAnswer(int questionId,int answerId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean commentAnswer(int questionId, int answerId, String comment) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Object> getAnswerComments(int questionId, int answerId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getUserInfoById(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	private boolean initXsrf(){
		String loginUrl = "http://www.zhihu.com";
		Content content = protocol.getOutput(loginUrl);
		String html = content.getContentString();	
		return updateXsrf(html);
	}

	
	public boolean updateXsrf(String html){
		Document doc = Jsoup.parse(html);
		Elements hidden = doc.select("[name=_xsrf]");
		Element element = hidden.first();
		if(element == null) return false;
		String xsrfString = element.attr("value");
		if(xsrfString == null)
			return false;
		xsrf = xsrfString;
		return true;
	}
	
	
	public String getXsrf(){
		return xsrf;
	}
	
	
}
