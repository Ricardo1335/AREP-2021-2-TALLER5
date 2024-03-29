/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.escuelaing.virtualization.dockerdemo;

import java.net.UnknownHostException;
import static spark.Spark.*;

public class SparkWebServer {
    public static void main(String... args) throws UnknownHostException{
        MongoDB mongo = new MongoDB();
        port(getPort());
        get("hello", (req,res) -> "Hello Docker!");
        get("database" ,(req,res) -> {
            res.type("application/json");
            mongo.addElelement("Elemento vacio"); 
            return mongo.getLast(); 
                  });
        get("database/:s" ,(req,res) -> {
            res.type("application/json");            
            String s = req.params(":s");  
            mongo.addElelement(s); 
            return mongo.getLast(); 
                  });
    }
    
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}