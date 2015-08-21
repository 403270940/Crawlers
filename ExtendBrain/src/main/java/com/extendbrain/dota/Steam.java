package com.extendbrain.dota;

import java.net.URI;
import java.net.URL;

import org.apache.http.client.methods.HttpGet;

import com.extendbrain.beans.Content;
import com.extendbrain.protocol.Protocol;
import com.extendbrain.protocol.ProtocolFactory;
import com.mysql.jdbc.log.Log;

public class Steam {
	private static final String key = "32EC45452C5F0EBF7CD99A561FC8CAFE";
	private static final String GetPlayerSummaries = " http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key="+key;
	private static final String GetGlobalStatsForGame = "http://api.steampowered.com/ISteamUserStats/GetGlobalStatsForGame/v0001/?format=xml&appid=570&count=1&name";
	private static Protocol protocol = ProtocolFactory.getProtocol("");
	private static final String GETFRIENDLIST = " http://api.steampowered.com/ISteamUser/GetFriendList/v0001/?relationship=friend&key="+key;
	private static final String GetPlayerAchievements = "http://api.steampowered.com/ISteamUserStats/GetPlayerAchievements/v0001/?appid=570&key=" + key;
	private static final String GetUserStatsForGame = " http://api.steampowered.com/ISteamUserStats/GetUserStatsForGame/v0002/?appid=440&key=" + key;
	private static final String GetMatchHistory = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?key=" + key;
	private static final String GetHeros = "https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?language=zh_cn&key=" + key;
	private static final String GetMatchDetail = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?key=" + key;
	
	public static String getMatchDetail(int matchID){
		String result = "";
		String url = GetMatchDetail + "&match_id=" + matchID;
		System.out.println("GetMatchDetail URL:" + url);
		try {
			URL url1 = new URL(url); 
			URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null); 
			HttpGet httpGet = new HttpGet(uri);
			Content content = protocol.getOutput(httpGet);
			if(content == null)
				return null;
			result = content.getContentString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
		
	}
	public static String getHeroes(){
		String result = "";
		String url = GetHeros;
	
		try {
			URL url1 = new URL(url); 
			URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null); 
			HttpGet httpGet = new HttpGet(uri);
			Content content = protocol.getOutput(httpGet);
			if(content == null)
				return null;
			result = content.getContentString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	public static String getMatchHostry(int acountID){
		String result = "";
		String url = GetMatchHistory + "&account_id="+acountID;
		System.out.println("GetMatchHistory URL:" + url);
		try {
			URL url1 = new URL(url); 
			URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null); 
			HttpGet httpGet = new HttpGet(uri);
			Content content = protocol.getOutput(httpGet);
			if(content == null)
				return null;
			result = content.getContentString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	public static String getMatchHostry(int acountID, int start_id){
		String result = "";
		String url = GetMatchHistory + "&account_id="+acountID + "&start_at_match_id="+start_id;
		System.out.println("GetMatchHistory URL:" + url);
		try {
			URL url1 = new URL(url); 
			URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null); 
			HttpGet httpGet = new HttpGet(uri);
			Content content = protocol.getOutput(httpGet);
			if(content == null)
				return null;
			result = content.getContentString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	public static String getPlayerSUmmaries(String steamID){
		String result = "";
		String url = GetPlayerSummaries + "&steamids="+steamID;
	
		try {
			URL url1 = new URL(url); 
			URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null); 
			HttpGet httpGet = new HttpGet(uri);
			Content content = protocol.getOutput(httpGet);
			if(content == null)
				return null;
			result = content.getContentString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	public static String getFriendList(String steamID){
		String result = "";
		String url = GETFRIENDLIST + "&steamid="+steamID;

		try {
			URL url1 = new URL(url); 
			URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null); 
			HttpGet httpGet = new HttpGet(uri);
			Content content = protocol.getOutput(httpGet);
			if(content == null)
				return null;
			result = content.getContentString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	
	public static String getPlayerAchievements(String steamID){
		String result = "";
		String url = GetPlayerAchievements + "&steamid="+steamID;

		try {
			URL url1 = new URL(url); 
			URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null); 
			HttpGet httpGet = new HttpGet(uri);
			Content content = protocol.getOutput(httpGet);
			if(content == null)
				return null;
			result = content.getContentString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	public static String getUserStatsForGame(String steamID){
		String result = "";
		String url = GetUserStatsForGame + "&steamid="+steamID;

		try {
			URL url1 = new URL(url); 
			URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null); 
			HttpGet httpGet = new HttpGet(uri);
			Content content = protocol.getOutput(httpGet);
			if(content == null)
				return null;
			result = content.getContentString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	public static void main(String[] args) {
		String steamID = "76561198095230997";
		String result = getPlayerSUmmaries(steamID);
//		String result = getFriendList(steamID);
		System.out.println(result);
	}
}
