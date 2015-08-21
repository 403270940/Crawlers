package com.extendbrain.dota;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.extendbrain.dota.entity.Match;
import com.extendbrain.dota.entity.MatchDetail;
import com.extendbrain.dota.entity.MatchDetailPlayer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MatchUtil {
	private static String fileNameRoot = "src/main/java/MatchDetails/";
	private static final int defaultmax = 1986230845;
	private static List<Match> getAllMatchHistory(int accountID){
		List<Match> matchList = new ArrayList<Match>();
		List<Match> localList = getLocalMatchHistory(accountID);
		
		if(localList != null){
			matchList.addAll(localList);
		}
		
		int min = getMinMatchId(localList);
		List<Match> serverList = new ArrayList<Match>();
		while(true){
			serverList = getServerMatchHistory(accountID, min - 1);
			if(serverList == null||serverList.isEmpty())
				break;
			int serverMin = getMinMatchId(serverList);
			min = min > serverMin?serverMin : min;
			matchList.addAll(serverList);
		}
		matchList = updateList(matchList, accountID);
		saveListToLocal(matchList, accountID);
		return matchList;
	}
	private static List<Match> updateList(List<Match> curList,int accountID){
		List<Match> updateList = new ArrayList<Match>();
		boolean flag = true;
		int curmax = getMaxMatchId(curList);
		int servermin = getMinMatchId(updateList);
		while(flag){
			List<Match> serverList = getServerMatchHistory(accountID, servermin);
			servermin = getMinMatchId(serverList);
			if(servermin <= curmax) flag = false;
			servermin -= 1;
			updateList.addAll(serverList);
		}
		curList.removeAll(updateList);
		curList.addAll(updateList);
		return curList;
	}
	
	private static int getMinMatchId(List<Match> matches){
		int min = defaultmax;
		if(matches == null)
			return min;
		for(Match match : matches){
			if(match.getMatch_id() < min){
				min = match.getMatch_id();
			}
		}
		return min;
	}
	
	private static int getMaxMatchId(List<Match> matches){
		int max = 0;
		if(matches == null)
			return max;
		for(Match match : matches){
			if(match.getMatch_id() > max){
				max = match.getMatch_id();
			}
		}
		return max;
	}
	
	private static List<Match> getLocalMatchHistory(int accountID){
		List<Match> matchList = new ArrayList<Match>();
		try{
			String fileName = fileNameRoot + accountID;
			File file = new File(fileName);
			String result = "";
			if(file.exists()){
				FileInputStream fis = new FileInputStream(file);
				BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
				String line = "";
				while((line = bfr.readLine()) != null){
					result += line;
				}
				bfr.close();
				fis.close();
			}else{
				return null;
			}
			Gson gson = new Gson();
			JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
			JsonObject friendsListObject = jsonObject.getAsJsonObject("result");
			JsonArray jsonArray = friendsListObject.getAsJsonArray("matches");
			for(int i = 0 ;i < jsonArray.size(); i ++){
				Match match = new Match();
				JsonObject matchObject = (JsonObject)jsonArray.get(i);
				match = gson.fromJson(matchObject, Match.class);
				matchList.add(match);
			}
		}catch(Exception e){
			
		}
		return matchList;
	}
	
	public static List<Match> getServerMatchHistory(int accountID,int start_id){
		List<Match> matchList = new ArrayList<Match>();
		String result = "";
		result = Steam.getMatchHostry(accountID,start_id);
		if(result == null||result.equals("")){
			return null;
		}
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
		JsonObject friendsListObject = jsonObject.getAsJsonObject("result");
		JsonArray jsonArray = friendsListObject.getAsJsonArray("matches");
		for(int i = 0 ;i < jsonArray.size(); i ++){
			Match match = new Match();
			JsonObject matchObject = (JsonObject)jsonArray.get(i);
			match = gson.fromJson(matchObject, Match.class);
			matchList.add(match);
		}

		return matchList;
	}
	
	public static List<Match> getServerMatchHistory(int accountID){
		List<Match> matchList = new ArrayList<Match>();

		String result = Steam.getMatchHostry(accountID);
		if(result == null||result.equals("")){
			return null;
		}

		Gson gson = new Gson();
		JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
		JsonObject friendsListObject = jsonObject.getAsJsonObject("result");
		JsonArray jsonArray = friendsListObject.getAsJsonArray("matches");
		for(int i = 0 ;i < jsonArray.size(); i ++){
			Match match = new Match();
			JsonObject matchObject = (JsonObject)jsonArray.get(i);
			match = gson.fromJson(matchObject, Match.class);
			matchList.add(match);
		}
		return matchList;
	}

	public static MatchDetail getMatchDetail(int matchID){
		MatchDetail matchDetail = null;
		try{
			String fileName = fileNameRoot + matchID;
			File file = new File(fileName);
			String result = "";
			if(file.exists()){
				FileInputStream fis = new FileInputStream(file);
				BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
				String line = "";
				while((line = bfr.readLine()) != null){
					result += line;
				}
				bfr.close();
				fis.close();
			}else{
				result = Steam.getMatchDetail(matchID);
				if(result == null||result.equals("")){
					return null;
				}
				FileOutputStream fos = new FileOutputStream(fileName);
				fos.write(result.getBytes());
				fos.flush();
				fos.close();
			}
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
		JsonObject detailObject = jsonObject.getAsJsonObject("result");
		matchDetail = gson.fromJson(detailObject, MatchDetail.class);
		}catch(Exception e){
			return null;
		}
		return matchDetail;
	}
	
	public static void saveListToLocal(List<Match> matches,int accountID){
		JsonObject matchesObject = new JsonObject();
		JsonObject resultObject = new JsonObject();
		Gson gson = new Gson();
		String matchesString = gson.toJson(matches);
		JsonArray matchesArray = new JsonParser().parse(matchesString).getAsJsonArray();
		matchesObject.add("matches", matchesArray);
		resultObject.add("result", matchesObject);
		String resultString = resultObject.toString();
		resultString = resultString.replace("{", "{\n");
		resultString = resultString.replace("[", "[\n");
		resultString = resultString.replace(",", ",\n");
		String fileName = fileNameRoot + accountID;
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fileName);
			fos.write(resultString.getBytes());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveToCSV(String result){
		String fileName = fileNameRoot + "123.csv";
		
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.write(result.getBytes());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {

				int accountID = 134965269;
				int count = 0;
//				getLocalMatchHistory(accountID);
				List<Match> matchList = getLocalMatchHistory(accountID);
				String mydata = "";
				for(Match match :matchList){
					int match_id = match.getMatch_id();
					MatchDetail matchDetail = null;
					try{
						matchDetail = getMatchDetail(match_id);
						System.out.println(count++ + ":" + match_id );
						int firstbloodTime = matchDetail.getFirst_blood_time();
						List<MatchDetailPlayer> players = matchDetail.getPlayers();
						
						String thResult = "";
						String yyResult = "";
						for(MatchDetailPlayer player : players ){
							if(player.getAccount_id() == accountID){
								mydata += match_id + ",";
								mydata += player.getKills() + ",";
								mydata += player.getDeaths() + ",";
								mydata += player.getXp_per_min() + ",";
								mydata += player.getGold_per_min() + "\n";
							}
						}


					}catch(Exception e){
						System.out.println(match_id + "failed");
					}
					
				}
				saveToCSV(mydata);
//				List<Match> matchList = getAllMatchHistory(accountID);
//				for(Match match : matchList){
//					getMatchDetail(match.getMatch_id());
//					System.out.println()accountID;
//				}
	}
	
}
