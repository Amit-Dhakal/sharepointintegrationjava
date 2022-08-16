package com.demo.sharepointauthentication.main;

import java.io.IOException;

public class HttpDownloader {
	
	
	 public static void main(String[] args) {
		    String wsUrl="https://2x106n.sharepoint.com/sites/sharepointjavaintegration/_api/web/GetFileByServerRelativeUrl('/sites/sharepointjavaintegration/Shared%20Documents/amitsharedocs/Document.docx')/$value";
	        String saveDir = "D:\\result\\test.txt";
	        try {
	            HttpDownloadUtility.downloadFile(wsUrl,saveDir);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    

}
	 
	 
}
