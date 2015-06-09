package com.extendbrain.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.extendbrain.caipiao.SSQ;
import com.extendbrain.utils.ConfigUtil;
import com.extendbrain.zhihu.Answer;

public class Mysql_Answer {

	static String driver = "com.mysql.jdbc.Driver";
	static String addr = ConfigUtil.getProperty("local_server_mysql_addr");
	static String dataBaseName = ConfigUtil.getProperty("local_server_mysql_databasename");
	static String url = "jdbc:mysql://"+addr+"/"+dataBaseName+"?useUnicode=true&characterEncoding=utf8";
	static String userName = ConfigUtil.getProperty("local_server_mysql_username");
	static String passWord = ConfigUtil.getProperty("local_server_mysql_password");
	static Connection connection = null;
	static{
		try {
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url,userName,passWord);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	//create table answer(id int not null primary key auto_increment,questionid int not null,answerid int not null,upcount int not null,commentcount int not null,answertext text,answertime varchar(15),username varchar(10),userinfo varchar(100),userurl varchar(100));
	public static void createTable(){
		PreparedStatement preparedStatement = null;
		String sql = "create table answer(id int not null primary key auto_increment,"
				+ "questionid int not null,"
				+ " answerid int not null, "
				+ "upcount int not null,"
				+ "commentcount int not null,"
				+ "answertext text,"
				+ "answertime varchar(15),"
				+ "username varchar(10),"
				+ "userinfo varchar(100),"
				+ "userurl varchar(100)) default charset=utf8;";
		//CREATE TABLE `proxy` (   `id` int(11) NOT NULL AUTO_INCREMENT,   `ip` varchar(20) DEFAULT NULL,   `port` varchar(5) DEFAULT NULL,   `type` varchar(20) DEFAULT NULL,   `location` varchar(50) DEFAULT NULL,   PRIMARY KEY (`id`) );
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static synchronized void droptable(){
		PreparedStatement preparedStatement = null;
		String sql = "drop table answer;";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void save(List<Answer> answerList){
		PreparedStatement preparedStatement = null;
		String sql = "insert into answer(questionid,answerid,upcount,commentcount,answertext,answertime,username,userinfo,userurl) values(?,?,?,?,?,?,?,?,?);";
		try {
			for(int i =answerList.size()-1;i>=0;i--){
				Answer answer = answerList.get(i);
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, answer.getQuestionId());
				preparedStatement.setInt(2, answer.getAnswerId());
				preparedStatement.setInt(3, answer.getUpCount());
				preparedStatement.setInt(4, answer.getComentCount());
				preparedStatement.setString(5, answer.getAnswerText());
				preparedStatement.setString(6, answer.getAnswerTime());
				preparedStatement.setString(7, answer.getUserName());
				preparedStatement.setString(8, answer.getUserInfo());
				preparedStatement.setString(9, answer.getUserURL());
				
				System.out.println(preparedStatement.toString());
				preparedStatement.execute();
			}
			
			preparedStatement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * id,url,status,fetchtime,fetchinterval,lastmodifiedtime,score
	 */
	public static void save(Answer answer){
		PreparedStatement preparedStatement = null;
		String sql = "insert into answer(questionid,answerid,upcount,commentcount,answertext,answertime,username,userinfo,userurl) values(?,?,?,?,?,?,?,?,?);";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, answer.getQuestionId());
			preparedStatement.setInt(2, answer.getAnswerId());
			preparedStatement.setInt(3, answer.getUpCount());
			preparedStatement.setInt(4, answer.getComentCount());
			preparedStatement.setString(5, answer.getAnswerText());
			preparedStatement.setString(6, answer.getAnswerTime());
			preparedStatement.setString(7, answer.getUserName());
			preparedStatement.setString(8, answer.getUserInfo());
			preparedStatement.setString(9, answer.getUserURL());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Answer answer = new Answer(1, 1, 1, 1, "中文", "时间", "userName", "userInfo", "userUrl");
		save(answer);
	}
}
