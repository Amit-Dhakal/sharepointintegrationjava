package com.demo.sharepointauthentication.main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;




public class ReceiveSecurityTokens{
	
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
	String shp_clientId="31cc1d50-b668-4c7d-b6b3-67f9b76deb16";
	String shp_tenantId="2e49583c-d997-41c6-b358-70371eda4c84";
	String shp_clientSecret="YhJyqpy9xT6hGAN2Y%2FZJ2xBhC0jci7GAZWtOm28OJ%2Bw%3D";

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
	+ "&resource=00000003-0000-0ff1-ce00-000000000000/www.sharepointintegration.com@" + shp_tenantId;
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




/*     "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAvd3d3LnNoYXJlcG9pbnRpbnRlZ3JhdGlvbi5jb21cbkAyZTQ5NTgzYy1kOTk3LTQxYzYtYjM1OC03MDM3MWVkYTRjODQiLCJpc3MiOiIwMDAwMDAwMS0wMDAwLTAwMDAtYzAwMC0wMDAwMDAwMDAwMDBAMmU0OTU4M2MtZDk5Ny00MWM2LWIzNTgtNzAzNzFlZGE0Yzg0IiwiaWF0IjoxNjYwMTI0NzEwLCJuYmYiOjE2NjAxMjQ3MTAsImV4cCI6MTY2MDIxMTQxMCwiaWRlbnRpdHlwcm92aWRlciI6IjAwMDAwMDAxLTAwMDAtMDAwMC1jMDAwLTAwMDAwMDAwMDAwMEAyZTQ5NTgzYy1kOTk3LTQxYzYtYjM1OC03MDM3MWVkYTRjODQiLCJuYW1laWQiOiIzMWNjMWQ1MC1iNjY4LTRjN2QtYjZiMy02N2Y5Yjc2ZGViMTZAMmU0OTU4M2MtZDk5Ny00MWM2LWIzNTgtNzAzNzFlZGE0Yzg0Iiwib2lkIjoiMTk0YTMzZDktZDVjYi00ZmMzLTk0YWUtZDEzODQ3MzZhMzQxIiwic3ViIjoiMTk0YTMzZDktZDVjYi00ZmMzLTk0YWUtZDEzODQ3MzZhMzQxIiwidHJ1c3RlZGZvcmRlbGVnYXRpb24iOiJmYWxzZSJ9.BxYWgbec0qzBbO-3HlktxNA9KqSqcuz1Kuf8bC9Kjn14YvKpkbJRYdkx7pEpsbpIjhfl13S_U7RGNH-iS-v8sGcgFf8ZyFz3MTjVXbEvvG7E2aXkfoMNpYHatdXJ5wXFbam7E3f2qM3s3B2073FQ6Xu9DrFkZISRRaXMslLxzz5tJXiawiUNdblpgetw-juyfeFLbNruGOupsusDFRUb8W16IaqyocrFHfj9BCYh2i8VOBacvZWl7Aifq0XTB6GLc_8R6VY193kg1m0QZ62qPLdpHVaknuH3zhCS4pvFS_6e5K3yiA2QnIQixSCAu2omuTlVgJML_3jcklSiiq6KvQ"                                                        */


