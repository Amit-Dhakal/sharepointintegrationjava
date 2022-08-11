package com.demo.sharepointauthentication.main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class SharepointAccessClass {
	
		/**
		* upload a file to a sharepoint library
		*/
		public static void main(String[] args) throws Exception{
		/**
		* This function helps to get SharePoint Access Token. SharePoint Access
		* Token is required to authenticate SharePoint REST service while performing Read/Write events.
		* SharePoint REST-URL to get access token is as:
		* https://accounts.accesscontrol.windows.net/<tenantID>/tokens/OAuth/2
		*
		* Input required related to SharePoint are as:
		* 1. shp_clientId
		* 2. shp_tenantId
		* 3. shp_clientSecret
		*/
			
			

		String accessToken = "";
		String shp_clientId="f83e1c66-f64d-49a1-85e2-207e47092918";
		String shp_tenantId="42150afd-062a-4a0f-81b9-0323526054cc";
		String shp_clientSecret="28t3QzULzpYlhJhHqt8BTGn%2BxY%2FWmQQcokb1SxKryNI%3D";		
		
			
		try {

		// AccessToken url
		String wsURL = "https://accounts.accesscontrol.windows.net/" + shp_tenantId + "/tokens/OAuth/2";

		URL url = new URL(wsURL);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) connection;

		// Set header
		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpConn.setRequestProperty("Content-Length", "0");
		httpConn.setRequestProperty("Host", "<calculated when request is sent>");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		httpConn.setRequestMethod("POST");

		// Prepare RequestData

		String jsonParam = "grant_type=client_credentials"
		+ "&client_id=" + shp_clientId + "@" + shp_tenantId
		+ "&client_secret=" + shp_clientSecret
		+ "&resource=00000003-0000-0ff1-ce00-000000000000/naveedtest.sharepoint.com@" + shp_tenantId;
		//Here, <org_Shp_Host> is "Origanisations's Sharepoint Host"

		// Send Request
		DataOutputStream wr = new DataOutputStream(httpConn.getOutputStream());
		wr.writeBytes(jsonParam);
		wr.flush();
		wr.close();

		// Read the response.
		InputStreamReader isr = null;
		if (httpConn.getResponseCode() == 200) {
		isr = new InputStreamReader(httpConn.getInputStream());
		} else {
		isr = new InputStreamReader(httpConn.getErrorStream());
		}

		BufferedReader in = new BufferedReader(isr);
		String responseString = "";
		String outputString = "";

		// Write response to a String.
		while ((responseString = in.readLine()) != null) {
		outputString = outputString + responseString;
		System.out.println(outputString);
		}

		// Extracting accessToken from string, here response (outputString)is a Json format string
		if (outputString.indexOf("access_token\":\"") > -1) {
		int i1 = outputString.indexOf("access_token\":\"");
		String str1 = outputString.substring(i1 + 15);
		int i2 = str1.indexOf("\"}");
		String str2 = str1.substring(0, i2);
		accessToken = str2;
		}
		} catch (Exception e) {
		accessToken = "Error: " + e.getMessage();
		}
		}
		

}
