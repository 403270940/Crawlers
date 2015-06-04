package com.extendbrain.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import com.extendbrain.beans.URLDatum;

public class Mysql_URLDatum {
	
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
	
	/*
	 * id,url,status,fetchtime,fetchinterval,lastmodifiedtime,score
	 */
	public static synchronized List<URLDatum> getStatus(byte status){
		List<URLDatum> list = new ArrayList<URLDatum>();
		PreparedStatement stmt = null;
		String sql = "select * from urldb where status=?";
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, status);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				String url = rs.getString("url");
				
				long fetchTime = rs.getLong("fetchtime");
				int fetchInterval = rs.getInt("fetchintval");
				long lastModifiedTime = rs.getLong("lastmodifiedtime");
				float score = rs.getFloat("score");
				URLDatum datum = new URLDatum(url, status, fetchTime, fetchInterval, lastModifiedTime, score);
				list.add(datum);
			}
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	public static synchronized void update(URLDatum datum, byte status){
		PreparedStatement preparedStatement = null;
		String sql = "update urldb set status=? where url=?;";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, status);
			preparedStatement.setString(2, datum.getUrl());
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * id,url,status,fetchtime,fetchinterval,lastmodifiedtime,score
	 */
	public static void save(URLDatum datum){
		PreparedStatement preparedStatement = null;
		String sql = "insert into urldb(url,status,fetchtime,fetchintval,lastmodifiedtime,score) values(?,?,?,?,?,?);";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, datum.getUrl());
			preparedStatement.setInt(2, datum.getStatus());
			preparedStatement.setLong(3, datum.getFetchTime());
			preparedStatement.setInt(4, datum.getFetchInterval());
			preparedStatement.setLong(5, datum.getLastModifiedTime());
			preparedStatement.setFloat(6, datum.getScore());
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		URLDatum datum = new URLDatum("http://bj.58.com/tongzhouqu/chuzu/", URLDatum.STATUS_INJECTED, System.currentTimeMillis(), 24*3600, System.currentTimeMillis(), 1);
		Mysql_URLDatum.save(datum);
	}
}
