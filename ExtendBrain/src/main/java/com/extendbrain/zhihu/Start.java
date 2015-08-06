package com.extendbrain.zhihu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import com.extendbrain.dao.Mysql_Answer;
import com.extendbrain.utils.ConfigUtil;

public class Start {

	public static void main(String[] args) {
		String email = ConfigUtil.getProperty("zhihu_userName");
		String password = ConfigUtil.getProperty("zhihu_password");
		ZhihuBase zhihu = Zhihu.getInstance();
		try {
			File file = new File("src/main/java/cookie.txt");
			FileInputStream fio = new FileInputStream(file);
			BufferedReader bReader = new BufferedReader(new InputStreamReader(fio));
			String cookie = "";
			String line = "";
			while((line = bReader.readLine())!=null){
				cookie += line;
			}
			zhihu.setCookie(cookie);
//			zhihu.login(email, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner sc = new Scanner(System.in);
		while(true){
			try {
			String line = sc.nextLine();	
			int questionId = Integer.valueOf(line.trim());
			String id = String.valueOf(questionId);
			if(id.length()!=8)
				throw new Exception("The id Length should be 8");
			List<Answer> answerList = zhihu.getAllAnswerOfQuestion(questionId);
			Mysql_Answer.save(answerList);
			System.out.println(answerList.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
