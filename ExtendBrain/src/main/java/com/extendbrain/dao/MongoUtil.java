package com.extendbrain.dao;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class MongoUtil {
	private static final String default_db_name = "nba";
	private static final String default_collection_name = "games";
	private static final String default_mongo_addr = "localhost";
	private static final int default_mongo_port = 27017;

	public static DBCollection getCollection() {
		try {
			Mongo mongo = new Mongo(default_mongo_addr, default_mongo_port);
			DB db = mongo.getDB(default_db_name);
			return db.getCollection(default_collection_name);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static DBCollection getCollection(String name) {
		try {
			Mongo mongo = new Mongo(default_mongo_addr, default_mongo_port);
			DB db = mongo.getDB(default_db_name);
			return db.getCollection(name);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static DBCollection getCollection(String dbName,
			String collectionName) {
		try {
			Mongo mongo = new Mongo(default_mongo_addr, default_mongo_port);
			DB db = mongo.getDB(dbName);
			return db.getCollection(collectionName);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void test() {
		DBCollection dbCollection = getCollection();
		DBCursor cursor = dbCollection.find();
		System.out.println(cursor.next());
		// while(cursor.hasNext()){
		// System.out.println(cur);
		// }
	}

	public static void main(String[] args) {
		test();
	}
}
