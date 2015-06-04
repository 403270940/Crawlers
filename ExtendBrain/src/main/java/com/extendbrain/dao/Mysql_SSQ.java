package com.extendbrain.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.extendbrain.caipiao.SSQ;
import com.extendbrain.utils.ConfigUtil;

public class Mysql_SSQ {
	
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
	
	public static void createTable(){
		PreparedStatement preparedStatement = null;
		String sql = "create table ssq( id int(10) not null auto_increment primary key,"
				+ "opentime varchar(20)  not null,edition int(8) not null unique,"
				+ "red1 int(2),red2 int(2),red3 int(2),"
				+ "red4 int(2),red5 int(2),red6 int(2),blue int(2),money int(15));";
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
		String sql = "drop table ssq;";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int getMaxEdition(){
		PreparedStatement preparedStatement = null;
		String sql = "select max(edition) from ssq;";
		int edition = 2003001;
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				edition = rs.getInt(1);
			}
			rs.close();
			preparedStatement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return edition;
	}
	public static void save(List<SSQ> ssqList){
		PreparedStatement preparedStatement = null;
		String sql = "insert into ssq(opentime,edition,red1,red2,red3,red4,red5,red6,blue,money) values(?,?,?,?,?,?,?,?,?,?);";
		try {
			for(int i =ssqList.size()-1;i>=0;i--){
				SSQ ssq = ssqList.get(i);
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, ssq.getOpenTime());
				preparedStatement.setInt(2, ssq.getEdition());
				preparedStatement.setInt(3, ssq.getRed1());
				preparedStatement.setInt(4, ssq.getRed2());
				preparedStatement.setInt(5, ssq.getRed3());
				preparedStatement.setInt(6, ssq.getRed4());
				preparedStatement.setInt(7, ssq.getRed5());
				preparedStatement.setInt(8, ssq.getRed6());
				preparedStatement.setInt(9, ssq.getBlue());
				preparedStatement.setInt(10, ssq.getMoney());
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
	public static void save(SSQ ssq){
		PreparedStatement preparedStatement = null;
		String sql = "insert into ssq(opentime,edition,red1,red2,red3,red4,red5,red6,blue,money) values(?,?,?,?,?,?,?,?,?,?);";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, ssq.getOpenTime());
			preparedStatement.setInt(2, ssq.getEdition());
			preparedStatement.setInt(3, ssq.getRed1());
			preparedStatement.setInt(4, ssq.getRed2());
			preparedStatement.setInt(5, ssq.getRed3());
			preparedStatement.setInt(6, ssq.getRed4());
			preparedStatement.setInt(7, ssq.getRed5());
			preparedStatement.setInt(8, ssq.getRed6());
			preparedStatement.setInt(9, ssq.getBlue());
			preparedStatement.setInt(10, ssq.getMoney());
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int maxEdition = getMaxEdition();
		System.out.println(maxEdition);
	}
}
