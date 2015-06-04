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
import com.extendbrain.crawlproxy.ProxyItem;
import com.extendbrain.utils.ConfigUtil;

public class Mysql_Proxies {
	
	static String driver = "com.mysql.jdbc.Driver";
	static String addr = ConfigUtil.getProperty("server_mysql_addr");
	static String dataBaseName = ConfigUtil.getProperty("server_mysql_databasename");
	static String url = "jdbc:mysql://"+addr+"/"+dataBaseName;
	static String userName = ConfigUtil.getProperty("server_mysql_username");
	static String passWord = ConfigUtil.getProperty("server_mysql_password");
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
	public static synchronized void droptable(){
		PreparedStatement preparedStatement = null;
		String sql = "drop table proxy;";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public static void save(ProxyItem proxyItem){
		PreparedStatement preparedStatement = null;
		String sql = "insert into proxy(ip,port,type,location) values(?,?,?,?);";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, proxyItem.getIp());
			preparedStatement.setString(2, proxyItem.getPort());
			preparedStatement.setString(3, proxyItem.getProxyType());
			preparedStatement.setString(4, proxyItem.getLocation());
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void createTable(){
		PreparedStatement preparedStatement = null;
		String sql = "create table proxy( id int(10) not null auto_increment primary key,ip varchar(20) not null,port varchar(5) not null,type varchar(20),location varchar(50));";
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
	public static void main(String[] args) {
		ProxyItem proxyItem = new ProxyItem("192.168.222.140", "200", "123", "shandong");
		Mysql_Proxies.save(proxyItem);
	}
}
