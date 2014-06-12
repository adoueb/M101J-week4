package com.tengen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class GridFSTest {
	public static void main(String[] args) throws IOException {
		MongoClient client = new MongoClient();
		DB db = client.getDB("course");
		FileInputStream inputStream = null;
		
		GridFS videos = new GridFS(db, "videos");	// returns GridFS bucket named videos
		
		try {
			inputStream = new FileInputStream("danse.mp4");
		} catch (FileNotFoundException e) {
			System.out.println("Can't open file");
			System.exit(1);
		}
		
		GridFSInputFile video = videos.createFile(inputStream, "danse.mp4"); // persist the file name
		
		// Create some meta data for the object
		BasicDBObject meta = new BasicDBObject("description", "Culture Danse");
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("dancing");
		tags.add("let there be love");
		tags.add("Christina Aguilera");
		meta.append("tags", tags);
		
		video.setMetaData(meta);
		video.save();
		
		System.out.println("Object ID in Files collection" + video.get("_id"));
		
		System.out.println("Saved the file to MongoDB");
		System.out.println("Now lets read it back out");
		
		GridFSDBFile gridFile = videos.findOne(new BasicDBObject("filename", "danse.mp4"));
		
		FileOutputStream outputStream = new FileOutputStream("danse_copy.mp4");
		gridFile.writeTo(outputStream);
		
		System.out.println("Write the file back out");
	}
}
