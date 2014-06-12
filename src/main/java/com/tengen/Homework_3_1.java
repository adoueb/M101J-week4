package com.tengen;

import java.net.UnknownHostException;

import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Homework_3_1 {

	public static void main(String[] args) throws UnknownHostException {
		
		DBCollection collection = getCollection();
		
		DBCursor cur = collection.find();
		
		// Iterate through the students.
		while (cur.hasNext()) {
			DBObject currentStudent = cur.next();
			
			// Iterate through the scores.
			BasicDBList scores = (BasicDBList)currentStudent.get("scores");			
			double lowestScore = 1000;
			int indexLowestHomework = -1;
			for (int scoreIndex = 0; scoreIndex < scores.size(); scoreIndex++) {
				DBObject score = (DBObject)scores.get(scoreIndex);
				String type = score.get("type").toString();
				double scoreVal = (double)score.get("score");
				
				if ((type.equals("homework")) && (scoreVal < lowestScore)) {
					lowestScore = scoreVal;
					indexLowestHomework = scoreIndex;
				}
			}
			
			// Remove the lowest homework score.
			//System.out.println("lowest score: " + String.valueOf(lowestScore) + " index: " + String.valueOf(indexLowestHomework));
			if (indexLowestHomework != -1) {
				scores.remove(indexLowestHomework);
				collection.save(currentStudent);
			} else {
				System.out.println("***warning*** no lowest homework score");
			}
		}
		
		//printFromCursor(cur);
	}
	
	private static DBCollection getCollection() throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB db = client.getDB("school");
		DBCollection collection = db.getCollection("students");
		return collection;
	}
	
	private static void printCollection(DBCollection collection) {
		DBCursor cur = collection.find();
		printFromCursor(cur);
	}
	
	private static void printFromCursor(DBCursor cur) {
		while (cur.hasNext()) {
			DBObject doc = cur.next();
			System.out.println(doc);
		}
	}

}