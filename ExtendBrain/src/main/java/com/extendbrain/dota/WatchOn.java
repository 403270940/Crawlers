package com.extendbrain.dota;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.extendbrain.dota.entity.Friend;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WatchOn {

	private static List<String> friendSteamIdList = new ArrayList<String>();
	private static ConcurrentHashMap<String,Friend> friendMap = new ConcurrentHashMap<String,Friend>();

	
	public static boolean initFriendList(){

		String steamID = "76561198095230997";
		String friendsResult = Steam.getFriendList(steamID);
		if(friendsResult == null) return false;
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonParser().parse(friendsResult).getAsJsonObject();
		JsonObject friendsListObject = jsonObject.getAsJsonObject("friendslist");
		JsonArray jsonArray = friendsListObject.getAsJsonArray("friends");
		for(int i = 0 ;i < jsonArray.size(); i ++){
			JsonObject friendObject = (JsonObject) jsonArray.get(i);
			
			String friendSteamId = friendObject.get("steamid").getAsString();
			friendSteamIdList.add(friendSteamId);
			System.out.println(friendSteamId);
		}
		return true;
	}

	public static boolean updateFriendMap(){
		for(String friendSteamId : friendSteamIdList){
			String result = Steam.getPlayerSUmmaries(friendSteamId);
			if(result == null)continue;
			JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
			JsonObject friendsListObject = jsonObject.getAsJsonObject("response");
			JsonArray jsonArray = friendsListObject.getAsJsonArray("players");
			JsonObject friendObject = (JsonObject) jsonArray.get(0);
			String steamId = friendObject.get("steamid").getAsString();
			String name = friendObject.get("personaname").getAsString();
			String personastate = friendObject.get("personastate").getAsString();
			int code = Integer.valueOf(personastate);
			String lastLogoff = friendObject.get("lastlogoff").getAsString();
			long now = System.currentTimeMillis();

			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sd = sdf.format(new Date(Long.parseLong(lastLogoff)*1000));
			Friend friend = new Friend(steamId, name, code, sd);
			

			friendMap.put(steamId, friend);
		}
		return true;
	}
	
	public static void testDate(){
		String time = "1438274261";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sd = sdf.format(new Date(Long.parseLong(time)*1000));
		System.out.println(sd);
	}
	public static void run(){
		Thread updateThread = new Thread(){
			int count = 0;
			@Override
			public void run() {
				while(true){
					
					if(count%5 == 0){
						updateFriendMap();
						System.out.println("update finished!");
					}
					outputMap();
					count ++;
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	
				}
			}
		};
	
		updateThread.run();
//		outputThread.run();
	}
	
	public static void outputMap(){
		System.out.println("start bianli!");
		Iterator iter = friendMap.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			Friend friend = (Friend)val;
			if(friend.getStatus().equals("Online"))
				System.out.println(friend);
		}

	}
	
	public static void main(String[] args) {
		initFriendList();
		System.out.println("init friend finished");
		run();
//		testDate();
	}
}
