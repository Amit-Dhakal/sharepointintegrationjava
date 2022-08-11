package com.demo.sharepointauthentication.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.bson.json.JsonObject;
import org.json.JSONArray;
import org.omg.CORBA.portable.InputStream;

import com.fasterxml.jackson.core.JsonParser;

public class DownloadSharepointApi {
	
public static void main(String[] args) {
	
    try {
String accessTokenInt = getSharepointToken();
String accessTokenIntglobal=accessTokenInt;
System.out.println("Access Token Generated - "+accessTokenIntglobal);
//Below folderUrl is the directory from where we will download the file. 
//and SiteURL is organizations' site for e.g. gotobo.sharepoint.com/sites/mysite
String folderUrl = "Shared%20Documents"+"/test";
String siteURL = "https://gotobo.sharepoint.com/sites/mysite";
// ======================================================================
// Get Folder Names from share point site to match our required directory from where download operation will be performed
// ======================================================================
String fUrl0 = siteURL + "/_api/web/GetFolderByServerRelativeUrl(\'" + folderUrl+"/"
		+ "\')/Folders?$orderby=ListItemAllFields/Modified%20desc&$top=1";                            
URL fileLstUrl0 = new URL(fUrl0);
URLConnection fConnection0 = fileLstUrl0.openConnection();
HttpURLConnection httpFConn0 = (HttpURLConnection) fConnection0;
httpFConn0.setRequestMethod("GET");
httpFConn0.setRequestProperty("Authorization", "Bearer " + accessTokenIntglobal);
httpFConn0.setRequestProperty("accept", "application/json;odata=verbose");
String httpFResponseStr0 = "";
InputStreamReader fisr0 = null;
if (httpFConn0.getResponseCode() == 200) {
	fisr0 = new InputStreamReader(httpFConn0.getInputStream());
} else {
	fisr0 = new InputStreamReader(httpFConn0.getErrorStream());
}
BufferedReader fin0 = new BufferedReader(fisr0);
String strfLine0 = "";
while ((strfLine0 = fin0.readLine()) != null) {
	httpFResponseStr0 = httpFResponseStr0 + strfLine0;
	System.out.println("strfLine==" + strfLine);
}
System.out.println(httpFResponseStr0); // Print
String fileName0 = "";
JsonParser fparser0 = new JsonParser();
JsonObject rootfObj0 = fparser0.parse(httpFResponseStr0).getAsJsonObject();
JsonArray nameFileArray0 = rootfObj0.get("d").getAsJsonObject().get("results")
		.getAsJsonArray();
for (JsonElement fpa : nameFileArray0) {
	JsonObject jsonFileNameObj = fpa.getAsJsonObject();
	fileName0 = jsonFileNameObj.get("Name").getAsString();
}
System.out.println(fileName0); // Print                         
// ======================================================================
// Get File Names to find our file to be downloaded
// ======================================================================
String fUrl = siteURL + "/_api/web/GetFolderByServerRelativeUrl(\'" + folderUrl+"/"
		 + "\')/Files?$orderby=ListItemAllFields/Modified%20desc";
URL fileLstUrl = new URL(fUrl);
URLConnection fConnection = fileLstUrl.openConnection();
HttpURLConnection httpFConn = (HttpURLConnection) fConnection;
httpFConn.setRequestMethod("GET");
httpFConn.setRequestProperty("Authorization", "Bearer " + accessTokenIntglobal);
httpFConn.setRequestProperty("accept", "application/json;odata=verbose");
// Read the response
String httpFResponseStr = "";
InputStreamReader fisr = null;
if (httpFConn.getResponseCode() == 200) {
	fisr = new InputStreamReader(httpFConn.getInputStream());
} else {
	fisr = new InputStreamReader(httpFConn.getErrorStream());
}
BufferedReader fin = new BufferedReader(fisr);
String strfLine = "";
while ((strfLine = fin.readLine()) != null) {
	httpFResponseStr = httpFResponseStr + strfLine;
	System.out.println("strfLine==" + strfLine);
}
System.out.println(httpFResponseStr); // Print
// response
String fileName = "";
JsonParser fparser = new JsonParser();
JsonObject rootfObj = fparser.parse(httpFResponseStr).getAsJsonObject();
JsonArray nameFileArray = rootfObj.get("d").getAsJsonObject().get("results")
		.getAsJsonArray();
for (JsonElement fpa : nameFileArray) {
	JsonObject jsonFileNameObj = fpa.getAsJsonObject();
	fileName = jsonFileNameObj.get("Name").getAsString();
	//System.out.println("fileName :" + fileName);
	// ======================================================================
	// Download files in the
	// respective
	// folders
	// ======================================================================
	if(fileName.toString().toLowerCase().contains("gotobo.zip"))
	{
	fileName = fileName.replaceAll("\\s", "%20");
	String fileUrl = siteURL + "/_api/web/GetFolderByServerRelativeUrl(\'"
			+ folderUrl + "/" 
			+ "\')/Files('" + fileName + "')/$value";
	URL urlf = new URL(fileUrl);
	URLConnection fconnection = urlf.openConnection();
	HttpURLConnection httpfConn = (HttpURLConnection) fconnection;
	httpfConn.setRequestMethod("GET");
	httpfConn.setRequestProperty("Authorization", "Bearer " + accessTokenIntglobal);
	InputStream inputStream = httpfConn.getInputStream();
	String fileDir = "c:\\users\\gotobo\\";
	//System.out.println("My path : " + fileDir);
	File fileDirs = new File(fileDir);
	if (!fileDirs.exists()) {
		fileDirs.mkdirs();
	}
	fileName = fileName.replaceAll("%20", " ");
	String saveFilePath = "c:\\users\\gotobo\\"+fileName;;
	//System.out.println("saveFilePath.." + saveFilePath);
	FileOutputStream outputStream = new FileOutputStream(saveFilePath);
	int bytesRead = -1;
	byte[] buffer = new byte[1024];
	while ((bytesRead = inputStream.read(buffer)) != -1) {
		outputStream.write(buffer, 0, bytesRead);
	}

	outputStream.close();
	inputStream.close();

	System.out.println(fileName + "  downloaded :");
	if(fileName.length()==0)
	{
		filelink="File not found";
	}
	else
	{
		filelink= "file is found";
	}
}
}


}
catch (Exception e) {
    e.printStackTrace();
    System.out.println("The file could not be downloaded/Token error/File IO : "+new Timestamp(System.currentTimeMillis()));
    filelink="File not found";
}   
	
	
	
	
	
	
	
	
	
	
	
}

}























//String  accessTokenInt="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAvd3d3LnNoYXJlcG9pbnRpbnRlZ3JhdGlvbi5jb21cbkAyZTQ5NTgzYy1kOTk3LTQxYzYtYjM1OC03MDM3MWVkYTRjODQiLCJpc3MiOiIwMDAwMDAwMS0wMDAwLTAwMDAtYzAwMC0wMDAwMDAwMDAwMDBAMmU0OTU4M2MtZDk5Ny00MWM2LWIzNTgtNzAzNzFlZGE0Yzg0IiwiaWF0IjoxNjYwMTI0NzEwLCJuYmYiOjE2NjAxMjQ3MTAsImV4cCI6MTY2MDIxMTQxMCwiaWRlbnRpdHlwcm92aWRlciI6IjAwMDAwMDAxLTAwMDAtMDAwMC1jMDAwLTAwMDAwMDAwMDAwMEAyZTQ5NTgzYy1kOTk3LTQxYzYtYjM1OC03MDM3MWVkYTRjODQiLCJuYW1laWQiOiIzMWNjMWQ1MC1iNjY4LTRjN2QtYjZiMy02N2Y5Yjc2ZGViMTZAMmU0OTU4M2MtZDk5Ny00MWM2LWIzNTgtNzAzNzFlZGE0Yzg0Iiwib2lkIjoiMTk0YTMzZDktZDVjYi00ZmMzLTk0YWUtZDEzODQ3MzZhMzQxIiwic3ViIjoiMTk0YTMzZDktZDVjYi00ZmMzLTk0YWUtZDEzODQ3MzZhMzQxIiwidHJ1c3RlZGZvcmRlbGVnYXRpb24iOiJmYWxzZSJ9.BxYWgbec0qzBbO-3HlktxNA9KqSqcuz1Kuf8bC9Kjn14YvKpkbJRYdkx7pEpsbpIjhfl13S_U7RGNH-iS-v8sGcgFf8ZyFz3MTjVXbEvvG7E2aXkfoMNpYHatdXJ5wXFbam7E3f2qM3s3B2073FQ6Xu9DrFkZISRRaXMslLxzz5tJXiawiUNdblpgetw-juyfeFLbNruGOupsusDFRUb8W16IaqyocrFHfj9BCYh2i8VOBacvZWl7Aifq0XTB6GLc_8R6VY193kg1m0QZ62qPLdpHVaknuH3zhCS4pvFS_6e5K3yiA2QnIQixSCAu2omuTlVgJML_3jcklSiiq6KvQ";        




