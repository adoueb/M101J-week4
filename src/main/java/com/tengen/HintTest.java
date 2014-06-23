package com.tengen;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class HintTest {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB courseDB = client.getDB("school");
		DBCollection collection = courseDB.getCollection("foo");
		

		BasicDBObject query = new BasicDBObject("a", 40000);
		query.append("b", 40000);
		query.append("c", 40000);
		
		// Solution 1
		//DBObject doc = collection.find(query).hint("a_1_b_-1_c_1").explain();
		
		// Solution 2
		BasicDBObject myHint = new BasicDBObject("a",1).append("b", -1).append("c", 1);
		DBObject doc = collection.find(query).hint(myHint).explain();
		
		for (String s: doc.keySet()) {
			System.out.printf("%25s : %s\n", s, doc.get(s));
		}	
	}

}
