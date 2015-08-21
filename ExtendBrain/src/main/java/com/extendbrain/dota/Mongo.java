package com.extendbrain.dota;

import com.extendbrain.dao.MongoUtil;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class Mongo {

	
	public static void insertMatchDetail(String result){
		
			DBObject dbObject = (DBObject)JSON.parse(result);
			DBCollection collection = MongoUtil.getCollection("Dota2","MatchDetails"); 
			collection.insert(dbObject);
	}
}
