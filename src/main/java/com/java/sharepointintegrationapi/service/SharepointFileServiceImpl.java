package com.java.sharepointintegrationapi.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.java.sharepointintegrationapi.connection.SharePointDetail;
import com.java.sharepointintegrationapi.connection.SharePointCredential;


@Slf4j
@Service
public class SharepointFileServiceImpl implements SharepointFileService {

    public static void main(String[] args) {

      SharepointFileService sharepointFileService = new SharepointFileServiceImpl();
       SharePointDetail sharePointDetail = new SharePointDetail();
      // sharePointDetail.setSiteName("/sites/sharepointjavaintegration");
     //  sharePointDetail.setDownloadDirectory("D:\\result\\");    
       sharepointFileService.downloadFoldersFiles(SharepointConnection.getToken(),sharePointDetail);

    }


    public void downloadFoldersFiles(String token,SharePointDetail sharePointDetail) {
    	
    	sharePointDetail=new SharePointDetail();
    	sharePointDetail.setSiteName("/sites/raju-dev");
        sharePointDetail.setDownloadDirectory("D:\\result\\");  
        
    	  String downloadFileDir = sharePointDetail.getDownloadDirectory();
          String siteFolderUrl = sharePointDetail.getSiteName() + "/Shared%20Documents";
          String siteURL = "https://" + SharePointCredential.domain + ".sharepoint.com" + sharePointDetail.getSiteName();
      
        try {
           // log.info("-----First downloading files from base folder-----.");
  
            System.out.println("-----First downloading files from base folder-----.");
            
            
            downloadAllFilesFromFolder(token, downloadFileDir,siteURL, siteFolderUrl);
            
            List<String> folderNames = getListOfFolders(token, siteURL, siteFolderUrl);
            for (String folder : folderNames) {
              //  log.info("Downloading Files from inside {} folder", folder);
                
            	 System.out.println("Downloading Files from inside {} folder");

                
                downloadAllFilesFromFolder(token, downloadFileDir + "/" + folder + "/", siteURL, siteFolderUrl + "/" + folder);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("The file could not be downloaded/Token error/File IO : " + new Timestamp(System.currentTimeMillis()));
        }

    }

    private void downloadAllFilesFromFolder(String token, String downloadFileDir, String siteURL, String siteFolderUrl) throws IOException {

        JsonArray nameFileArray = getFilesNameFromFolder(token, siteURL, siteFolderUrl);
        String fileName;
        for (JsonElement fpa : nameFileArray) {

            JsonObject jsonFileNameObj = fpa.getAsJsonObject();
            fileName = jsonFileNameObj.get("Name").getAsString();

            fileName = fileName.replaceAll("\\s", "%20");
            String fileUrl = siteURL + "/_api/web/GetFolderByServerRelativeUrl('"
                    + siteFolderUrl
                    + "')/Files('" + fileName + "')/$value";

            URL url = new URL(fileUrl);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + token);
            InputStream inputStream = httpURLConnection.getInputStream();
            convertToFileAndDownload(downloadFileDir, fileName, inputStream);

        }
    }

    private void convertToFileAndDownload(String downloadFileDir, String fileName, InputStream inputStream) throws IOException {
        File fileDirs = new File(downloadFileDir);
        if (!fileDirs.exists()) {
            fileDirs.mkdirs();
        }
        fileName = fileName.replaceAll("%20", " ");
        String saveFilePath = downloadFileDir + fileName;
        FileOutputStream outputStream = new FileOutputStream(saveFilePath);
        int bytesRead = -1;
        byte[] buffer = new byte[1024];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();
        
     //   log.info("File {} downloaded", fileName);
        
        System.out.println("File {} downloaded");
        
    }
    
    private JsonArray getFilesNameFromFolder(String token, String siteURL, String siteFolderUrl) throws IOException {
        String fUrl = siteURL + "/_api/web/GetFolderByServerRelativeUrl('" + siteFolderUrl
                + "')/Files";
        URL fileLstUrl = new URL(fUrl);
        URLConnection fConnection = fileLstUrl.openConnection();
        HttpURLConnection httpFConn = (HttpURLConnection) fConnection;
        httpFConn.setRequestMethod("GET");
        httpFConn.setRequestProperty("Authorization", "Bearer " + token);
        httpFConn.setRequestProperty("accept", "application/json;odata=verbose");
        
        
        // Read the response
        String httpFResponseStr = "";
        InputStreamReader inputStreamReader;
        if (httpFConn.getResponseCode() == 200) {
            inputStreamReader = new InputStreamReader(httpFConn.getInputStream());
        } else {
            inputStreamReader = new InputStreamReader(httpFConn.getErrorStream());
        }
        BufferedReader fin = new BufferedReader(inputStreamReader);
        String strfline = "";
        while ((strfline = fin.readLine()) != null) {
            httpFResponseStr = httpFResponseStr + strfline;
            System.out.println("strfline==" + strfline);
        }
        JsonParser parser = new JsonParser();
        JsonObject rootObj = parser.parse(httpFResponseStr).getAsJsonObject();
        return rootObj.get("d").getAsJsonObject().get("results")
                .getAsJsonArray();
    }

    public List<String> getListOfFolders(String token, String siteURL, String siteFolderUrl) throws IOException {
  	
        List<String> folderNames = new ArrayList<>();
        String fUrl0 = siteURL + "/_api/web/GetFolderByServerRelativeUrl('" + siteFolderUrl
                + "')/Folders";
        String httpFResponseStr0 = getHttpResponse(token, fUrl0);
        String fileName0 = "";
        JsonParser parser = new JsonParser();

        JsonObject jsonObject = parser.parse(httpFResponseStr0).getAsJsonObject();
        JsonArray nameFileArray0 = jsonObject.get("d").getAsJsonObject().get("results")
                .getAsJsonArray();

        for (JsonElement fpa : nameFileArray0) {
            JsonObject jsonFileNameObj = fpa.getAsJsonObject();
            fileName0 = jsonFileNameObj.get("Name").getAsString();
            if (!fileName0.equals("Forms"))
                folderNames.add(fileName0);
        }
        return folderNames;
    }

    
    
    private String getHttpResponse(String token, String fUrl0) throws IOException {
        URL fileLstUrl0 = new URL(fUrl0);
        HttpURLConnection httpFConn0 = getHttpURLConnection(token, fileLstUrl0);
        StringBuilder httpFResponseStr0 = new StringBuilder();
        InputStreamReader inputStreamReader = getInputStreamReader(httpFConn0);
        BufferedReader fin0 = new BufferedReader(inputStreamReader);
        String streamLine = "";
        while ((streamLine = fin0.readLine()) != null) {
            httpFResponseStr0.append(streamLine);
            System.out.println("streamLine==" + streamLine);
        }
        return httpFResponseStr0.toString();
    }

    private InputStreamReader getInputStreamReader(HttpURLConnection httpFConn0) throws IOException {
        InputStreamReader inputStreamReader = null;
        if (httpFConn0.getResponseCode() == 200) {
            inputStreamReader = new InputStreamReader(httpFConn0.getInputStream());
        } else {
            inputStreamReader = new InputStreamReader(httpFConn0.getErrorStream());
        }
        return inputStreamReader;
    }

    private HttpURLConnection getHttpURLConnection(String token, URL fileLstUrl0) throws IOException {
        URLConnection fConnection0 = fileLstUrl0.openConnection();
        HttpURLConnection httpFConn0 = (HttpURLConnection) fConnection0;
        httpFConn0.setRequestMethod("GET");
        httpFConn0.setRequestProperty("Authorization", "Bearer " + token);
        httpFConn0.setRequestProperty("accept", "application/json;odata=verbose");
        httpFConn0.connect();
        return httpFConn0;
    }
}
