package com.demo.sharepointauthentication.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class SharepointRetrieveAllFiles {
	
	
	public static void main(String[] args) {
		
	/*
	Sharepoint REST details to retrieve all of the files in a folder
	url: http://site url/_api/web/GetFolderByServerRelativeUrl('/Folder Name')/Files
	method: GET
	headers:
	    Authorization: "Bearer " + accessToken
	    accept: "application/json;odata=verbose" or "application/atom+xml"
	*/
	try {		
	   //Frame SharePoint siteURL			
	   String siteURL = "https://<host>/<siteURL_path>";
			
	   //Frame SharePoint URL to retrieve all of the files in a folder
	   String wsUrl = siteURL + "/_api/web/GetFolderByServerRelativeUrl('Shared%20Documents')/Files";
		
	   //Create HttpURLConnection
	   URL url = new URL(wsUrl);
	   URLConnection connection = url.openConnection();
	   HttpURLConnection httpConn = (HttpURLConnection) connection;
							
	   //Set Header		
	   httpConn.setRequestMethod("GET");				
	   httpConn.setRequestProperty("Authorization", "Bearer " + accessToken);
	   httpConn.setRequestProperty("accept", "application/json;odata=verbose"); //To get response in JSON
	   //httpConn.setRequestProperty("accept", "application/atom+xml");         //To get response in XML
	   
	   //Read the response
	   String httpResponseStr = "";
	   InputStreamReader isr = null;
	   if (httpConn.getResponseCode() == 200) {
	     isr = new InputStreamReader(httpConn.getInputStream());
	   } else {
	     isr = new InputStreamReader(httpConn.getErrorStream());
	   }		
	   BufferedReader in = new BufferedReader(isr); 	 		
	   String strLine = "";
	   while ((strLine = in.readLine()) != null) {
	     httpResponseStr = httpResponseStr + strLine;
	   }
	   //System.out.println(httpResponseStr);	//Print response
	} catch (Exception e) {
	   //System.out.println("Error while reading file: " + e.getMessage());
	}		
	
	
	}
	

}
