/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.escuelaing.virtualization.dockerdemo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.bson.Document;


public class MongoDB {
    Integer n;
    MongoClientURI uri;
    MongoClient mongoClient;
    public MongoDB(){
        this.n = 0;
        uri = new MongoClientURI("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false");
        
        mongoClient = new MongoClient(uri);

    }
    public void addElelement(String message){
        MongoDatabase database = mongoClient.getDatabase("arep");
        MongoCollection<Document> collection = database.getCollection("local");
        Document document=new Document();
        document.put("mensaje",message);
        document.put("fecha", LocalDateTime.now().toString()); 
        collection.insertOne(document);
    }
    public String getLast(){
        Document a = new Document();
        MongoDatabase database = mongoClient.getDatabase("arep");
        MongoCollection<Document> collection =database.getCollection("local");
        FindIterable fit = collection.find();
        ArrayList<Document> docs = new ArrayList<Document>();
        ArrayList<String> results = new ArrayList<>();
        fit.into(docs);
        ArrayList<Document> docs1 = new ArrayList<Document>();
        for (Document docz : docs){
            docs1.add(0, docz);
        }
        for (Document doc : docs1) {
            if (doc.get("mensaje")!= null){
                   n += 1;
                a.append(doc.get("_id").toString(), doc);
            }
            if(n>= 10 ){
              n = 0;
                break;
            }
            
        }
       return a.toJson();
    }
}
