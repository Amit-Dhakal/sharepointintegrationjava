package com.java.sharepointintegrationapi.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.catalina.authenticator.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.java.sharepointintegrationapi.dto.DownloadRequest;
import com.java.sharepointintegrationapi.dto.SharePointDetail;
import com.java.sharepointintegrationapi.service.ConnectionService;
import com.java.sharepointintegrationapi.service.SharepointFileService;
import com.java.sharepointintegrationapi.util.Credentials;
import com.java.sharepointintegrationapi.util.FolderList;

import ch.qos.logback.core.pattern.parser.Node;



@RestController
public class SharePointController {

    @Autowired
    SharepointFileService sharepointFileService;
    
    
    FolderList folderList=new FolderList();
    
    Constants constants=new Constants();
    
    Credentials credentials =new Credentials();
      
    @GetMapping("/view")
    public ResponseEntity<?> listFilesAndFolder() throws IOException {
    	
       // String filePath = "D:" + File.separator + credentials.shared_folder;      
       // Node node = folderList.getNode(new File(filePath));
        
        String token = ConnectionService.getToken();
        
        String siteFolderUrl = Credentials.getSite() + "/Shared%20Documents";
        
        String siteURL = "https://" + Credentials.domain +".sharepoint.com" + Credentials.getSite();
 
	    List<String> listFolder = sharepointFileService.getListOfFolders(token,siteURL,siteFolderUrl);
	    
		return new ResponseEntity<>("List Files & Folder are ::"+listFolder, HttpStatus.OK);
        
       
      //  return new ResponseEntity<>(node, HttpStatus.OK);

    }

    
    @PostMapping("/download")
    public ResponseEntity<?> download(@RequestBody SharePointDetail sharePointDetail) throws IOException {
        //logic to download.
    	
     //   SharePointDetail sharePointDetail=new SharePointDetail();
    	
    	String siteName=sharePointDetail.getSiteName();
    	String downloadDirectory=sharePointDetail.getDownloadDirectory();
        String siteFolderUrl = sharePointDetail.getSiteName() + "/Shared%20Documents";//2 part

     //   String relativeurl = "https://" + Credentials.domain + ".sharepoint.com" + sharePointDetail.getSiteName()+siteFolderUrl;

    	
        String token = ConnectionService.getToken();
        sharepointFileService.downloadFoldersFiles(token, sharePointDetail);
        return new ResponseEntity<>("Downloaded Successfully!", HttpStatus.OK);
    	
    	
       // return new ResponseEntity<>(null, HttpStatus.OK);

    }

}
