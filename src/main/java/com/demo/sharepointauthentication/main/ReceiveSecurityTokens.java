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
	String shp_clientId="2e7b729f-819b-42ed-94de-24cfd51612ff";
	String shp_tenantId="da9e3b7b-1a23-4279-b9fb-dfee744dff10";
	String shp_clientSecret="5XS8Q~kXEfHN740U1wW-LbqQql1SzuKWCfS3Oa7i";

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
	+ "&resource=00000003-0000-0ff1-ce00-000000000000/2x106n.sharepoint.com@" + shp_tenantId;
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




//{"token_type":"Bearer","expires_in":"86399","not_before":"1660544398","expires_on":"1660631098","resource":"00000003-0000-0ff1-ce00-000000000000/2x106n.sharepoint.com@da9e3b7b-1a23-4279-b9fb-dfee744dff10","access_token":

// "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAvMngxMDZuLnNoYXJlcG9pbnQuY29tQGRhOWUzYjdiLTFhMjMtNDI3OS1iOWZiLWRmZWU3NDRkZmYxMCIsImlzcyI6IjAwMDAwMDAxLTAwMDAtMDAwMC1jMDAwLTAwMDAwMDAwMDAwMEBkYTllM2I3Yi0xYTIzLTQyNzktYjlmYi1kZmVlNzQ0ZGZmMTAiLCJpYXQiOjE2NjA1NDQzOTgsIm5iZiI6MTY2MDU0NDM5OCwiZXhwIjoxNjYwNjMxMDk4LCJpZGVudGl0eXByb3ZpZGVyIjoiMDAwMDAwMDEtMDAwMC0wMDAwLWMwMDAtMDAwMDAwMDAwMDAwQGRhOWUzYjdiLTFhMjMtNDI3OS1iOWZiLWRmZWU3NDRkZmYxMCIsIm5hbWVpZCI6IjJlN2I3MjlmLTgxOWItNDJlZC05NGRlLTI0Y2ZkNTE2MTJmZkBkYTllM2I3Yi0xYTIzLTQyNzktYjlmYi1kZmVlNzQ0ZGZmMTAiLCJvaWQiOiIwOWZkMjM4OC1kMjk0LTRjMGYtYmQwNy03ZjAwYzgxNjhjM2MiLCJzdWIiOiIwOWZkMjM4OC1kMjk0LTRjMGYtYmQwNy03ZjAwYzgxNjhjM2MiLCJ0cnVzdGVkZm9yZGVsZWdhdGlvbiI6ImZhbHNlIn0.YwJqmXKynWQs2kIdwBBSXrsyQ4oEMa-L7USJW4e0rhMBRRKx4LeVfGWdVEgt7r33oXqwReEwybFbUwSBdan6nw4e-ZRDmnrfVOqMY3VjxMvesH1kIxjTpHv2ODWh8szeUFoyQH6ru0z60jYrPOchJBDjQZRakKHusCCrj-paziSINfl68xMHSVnS1tOfJ49DpI7cT57hMwBaQZ0p8rRatj5jP_mZ02WREej-YpKNl9rdAFZfMdLD9Nf9Rcf-y4Le52lYzf5j0OqIBf6GpcE7i43nacVu-fCAgPO6E4H4OEWKnS22kOHoUySupReQ4sVRkFRVsc0X5GSl_0Tx2iS2iw"}


//"access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAvMngxMDZuLnNoYXJlcG9pbnQuY29tQGRhOWUzYjdiLTFhMjMtNDI3OS1iOWZiLWRmZWU3NDRkZmYxMCIsImlzcyI6IjAwMDAwMDAxLTAwMDAtMDAwMC1jMDAwLTAwMDAwMDAwMDAwMEBkYTllM2I3Yi0xYTIzLTQyNzktYjlmYi1kZmVlNzQ0ZGZmMTAiLCJpYXQiOjE2NjA1NTI5MjgsIm5iZiI6MTY2MDU1MjkyOCwiZXhwIjoxNjYwNjM5NjI4LCJpZGVudGl0eXByb3ZpZGVyIjoiMDAwMDAwMDEtMDAwMC0wMDAwLWMwMDAtMDAwMDAwMDAwMDAwQGRhOWUzYjdiLTFhMjMtNDI3OS1iOWZiLWRmZWU3NDRkZmYxMCIsIm5hbWVpZCI6IjJlN2I3MjlmLTgxOWItNDJlZC05NGRlLTI0Y2ZkNTE2MTJmZkBkYTllM2I3Yi0xYTIzLTQyNzktYjlmYi1kZmVlNzQ0ZGZmMTAiLCJvaWQiOiIwOWZkMjM4OC1kMjk0LTRjMGYtYmQwNy03ZjAwYzgxNjhjM2MiLCJzdWIiOiIwOWZkMjM4OC1kMjk0LTRjMGYtYmQwNy03ZjAwYzgxNjhjM2MiLCJ0cnVzdGVkZm9yZGVsZWdhdGlvbiI6ImZhbHNlIn0.RRtEaO4WJb-orFuVUZbe_PL4o8EZs0vq_w8fUr7H6dg5rGavB-e9Se6slaaOqDPzvbGCGPqqqdRQRCbw5oFZ3kn1deldPKYpUykgEFeKS78ncXWr1bTgX_y_SkcJU0Tjeocoa1ryLUZhwYulLDiMNw4TKKPmLOXRtkBjQBvDTmC4GxDuh50FFStzi3SNbhcAjyaduYKY52HZndOwY839cxfEIlsmWu9Sg8vLvKhYzgiOgDeZGhPQfSgPDDhNroelu9RBY_wz1ZzmGYNNxMLBjlYL-_yPXnPeNR9NAbiTNfpbXTAnsDYCWPbIdHk9dy5SH7QRA-4UgkxvZMlur1Uq4A"}




