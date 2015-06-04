package com.extendbrain.dao;

import java.util.List;

import com.extendbrain.meituan.Data;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.util.JSON;

public class Mongo_Meituan {
	private static final DBCollection coll = MongoUtil.getCollection("meituan",
			"items");

	public static void findAll() {
		DBCursor cursor = coll.find();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	public static void addList(List<Object> dataList) {

		for (Object object : dataList) {
			insert((Data) object);
		}
	}

	public static void insert(Data data) {
		Gson gson = new Gson();
		String dataString = gson.toJson(data);
		BasicDBObject dbObject = (BasicDBObject) JSON.parse(dataString);
		MongoUtil.getCollection("meituan", "items").insert(dbObject);
	}

	public static void main(String[] args) {
	}
}
