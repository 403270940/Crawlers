package com.extendbrain.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.extendbrain.beans.URLDatum;

public class Mysql_Cities {

	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://192.168.222.134/crawler";
	static String userName = "root";
	static String passWord = "123456";
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
	public static void save(int cityno,String strict,String area,String url){
		PreparedStatement preparedStatement = null;
		String sql = "insert into cities(cityno,strict,area,url) values(?,?,?,?);";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, cityno);
			preparedStatement.setString(2, strict);
			preparedStatement.setString(3, area);
			preparedStatement.setString(4, url);
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
