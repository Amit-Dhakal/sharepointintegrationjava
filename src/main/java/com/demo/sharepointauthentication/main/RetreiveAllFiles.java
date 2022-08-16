package com.demo.sharepointauthentication.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class RetreiveAllFiles {
	
	
	 public static void main(String[] args) {
		
	
	try {						
		   //Frame SharePoint URL to retrieve all of the files in a folder
		//   String wsUrl = siteURL + "/_api/web/GetFolderByServerRelativeUrl('Shared%20Documents')/Files";
		 String accessToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAvMngxMDZuLnNoYXJlcG9pbnQuY29tQGRhOWUzYjdiLTFhMjMtNDI3OS1iOWZiLWRmZWU3NDRkZmYxMCIsImlzcyI6IjAwMDAwMDAxLTAwMDAtMDAwMC1jMDAwLTAwMDAwMDAwMDAwMEBkYTllM2I3Yi0xYTIzLTQyNzktYjlmYi1kZmVlNzQ0ZGZmMTAiLCJpYXQiOjE2NjA1NTI5MjgsIm5iZiI6MTY2MDU1MjkyOCwiZXhwIjoxNjYwNjM5NjI4LCJpZGVudGl0eXByb3ZpZGVyIjoiMDAwMDAwMDEtMDAwMC0wMDAwLWMwMDAtMDAwMDAwMDAwMDAwQGRhOWUzYjdiLTFhMjMtNDI3OS1iOWZiLWRmZWU3NDRkZmYxMCIsIm5hbWVpZCI6IjJlN2I3MjlmLTgxOWItNDJlZC05NGRlLTI0Y2ZkNTE2MTJmZkBkYTllM2I3Yi0xYTIzLTQyNzktYjlmYi1kZmVlNzQ0ZGZmMTAiLCJvaWQiOiIwOWZkMjM4OC1kMjk0LTRjMGYtYmQwNy03ZjAwYzgxNjhjM2MiLCJzdWIiOiIwOWZkMjM4OC1kMjk0LTRjMGYtYmQwNy03ZjAwYzgxNjhjM2MiLCJ0cnVzdGVkZm9yZGVsZWdhdGlvbiI6ImZhbHNlIn0.RRtEaO4WJb-orFuVUZbe_PL4o8EZs0vq_w8fUr7H6dg5rGavB-e9Se6slaaOqDPzvbGCGPqqqdRQRCbw5oFZ3kn1deldPKYpUykgEFeKS78ncXWr1bTgX_y_SkcJU0Tjeocoa1ryLUZhwYulLDiMNw4TKKPmLOXRtkBjQBvDTmC4GxDuh50FFStzi3SNbhcAjyaduYKY52HZndOwY839cxfEIlsmWu9Sg8vLvKhYzgiOgDeZGhPQfSgPDDhNroelu9RBY_wz1ZzmGYNNxMLBjlYL-_yPXnPeNR9NAbiTNfpbXTAnsDYCWPbIdHk9dy5SH7QRA-4UgkxvZMlur1Uq4A";
	    String wsUrl="https://2x106n.sharepoint.com/sites/sharepointjavaintegration/_api/web/GetFileByServerRelativeUrl('/sites/sharepointjavaintegration/Shared%20Documents/amitsharedocs/Document.docx')/$value";
		
	    
		   //Create HttpURLConnection
		   URL url = new URL(wsUrl);
		   URLConnection connection = url.openConnection();
		   HttpURLConnection httpConn = (HttpURLConnection) connection;
								
		   //Set Header		
		   httpConn.setRequestMethod("GET");				
		   httpConn.setRequestProperty("Authorization", "Bearer " + accessToken);
		   httpConn.setRequestProperty("accept", "application/json;odata="); //To get response in JSON
		 //  httpConn.setRequestProperty("accept", "application/atom+xml");         //To get response in XML
		   
		      
		   //Read the response
		   String httpResponseStr = "";
		  InputStreamReader isr = null;
		  		   
		   	          
		   if (httpConn.getResponseCode() == 200) {
		          
		   isr = new InputStreamReader(httpConn.getInputStream());
		     	     
		          System.out.println("success");
		          
		          
		   } else {
			   
		    isr = new InputStreamReader(httpConn.getErrorStream());
			   	   
		   }		
		   BufferedReader in = new BufferedReader(isr); 	
		   
		   String strLine = "";
		   while ((strLine = in.readLine()) != null) {
		     httpResponseStr = httpResponseStr + strLine;
		   }
		  		
			 //code to download files    

		   
			   
			   
			   			   
			   try {
				   String path="D:\\result\\test.mq5";

				   File file=new File(path);
				   FileWriter fileWriter = new FileWriter(file);
				   fileWriter.write(httpResponseStr); 			
				   fileWriter.flush();
				   fileWriter.close();
				   System.out.println("file downloaded at");

				 } catch (FileNotFoundException e) {
				   e.printStackTrace();
				 }
			   
			   
			   /*
			   
			   File file = new File("C:\\result\\text.txt");
			   FileWriter fileWriter = new FileWriter(file);   	   
			   fileWriter.write(httpResponseStr); 		   
	            Files.write(Paths.get("C:\\result\\text.txt"), Base64.getDecoder().decode(Base64.getEncoder().encodeToString(bytes)));
	               
			   fileWriter.flush();
			   fileWriter.close();
			   
			   */
			   
			   
			 } catch (IOException e) {
			   e.printStackTrace();
			 }
		   
			   
		
	 }
	
	
}
