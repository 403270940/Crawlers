package com.extendbrain.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.extendbrain.beans.Content;
import com.extendbrain.beans.RentItem;
import com.extendbrain.crawl58.Parser_58;

public class Mysql_Content {
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
	public static List<Content> select(){
		PreparedStatement preparedStatement = null;
		String sql = "select * from content";
		List<Content> contentList = new ArrayList<Content>();
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				
				String url = rs.getString("url");
				byte[] con = rs.getBytes("content");
				String contentType = rs.getString("contentType");
				String contentEncoding = rs.getString("contentEncoding");
				Content content = new Content(url,con,contentType,contentEncoding,200);
				contentList.add(content);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contentList;
	}
	/*
	 * id
	 * url
	 * content
	 * contentType
	 * contentEncoding
	 */
	public static synchronized void save(Content content){
		PreparedStatement preparedStatement = null;
		String sql = "insert into content(url,content,contentType,contentEncoding) values(?,?,?,?);";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, content.getUrl());
			preparedStatement.setBytes(2, content.getContent());
			preparedStatement.setString(3, content.getContentType());
			preparedStatement.setString(4, content.getCharSet());
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		List<Content> contentList = Mysql_Content.select();
		Content content = contentList.get(0);
		List<RentItem> list = Parser_58.getRentItems(content);
		for(RentItem rentItem : list){
			System.out.println(rentItem.toString());
		}
	}
}
