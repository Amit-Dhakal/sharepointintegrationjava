package com.java.sharepointintegrationapi.connection;

import lombok.Data;

@Data
public class SharePointDetail {

 //  private String siteName="/sites/sharepointjavaintegration";
  //  private String downloadDirectory = "D:\\result\\";

    
    private String siteName;
    private String downloadDirectory;
    
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getDownloadDirectory() {
		return downloadDirectory;
	}
	public void setDownloadDirectory(String downloadDirectory) {
		this.downloadDirectory = downloadDirectory;
	}
    
    
    
    
    
    
    
}
