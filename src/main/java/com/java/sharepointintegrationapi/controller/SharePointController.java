package com.java.sharepointintegrationapi.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.java.sharepointintegrationapi.connection.SharePointCredential;
import com.java.sharepointintegrationapi.connection.SharePointDetail;
import com.java.sharepointintegrationapi.service.SharepointConnection;
import com.java.sharepointintegrationapi.service.SharepointFileService;
import com.java.sharepointintegrationapi.service.SharepointFileServiceImpl;

@RestController
public class SharePointController {
	
	@Autowired
     SharepointFileService sharepointFileService;
	
    public SharePointController(SharepointFileService sharepointFileService) {
        this.sharepointFileService = sharepointFileService;
    }
    
    
    
    public SharePointController() {
		// TODO Auto-generated constructor stub
	}



	@PostMapping(path="/download",consumes = {
    		MediaType.APPLICATION_XML_VALUE,
    		MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_XML_VALUE,
    	    		MediaType.APPLICATION_JSON_VALUE}   )
   
    public ResponseEntity<?> downloadFilesAndFolder() {
    	SharePointDetail sharePointDetail=new SharePointDetail();
        String token = SharepointConnection.getToken();
        sharepointFileService.downloadFoldersFiles(token,sharePointDetail);
        return new ResponseEntity<>("Downloaded Successfully!", HttpStatus.OK);
        
        
    }
    
    
    @GetMapping("/listfiles")   
    public ResponseEntity<?> listFilesAndFolder() throws IOException {
    	
    	SharePointDetail sharePointDetail=new SharePointDetail();
    //	sharePointDetail.setSiteName("/sites/sharepointjavaintegration");
    	
       	sharePointDetail.setSiteName("/sites/raju-dev");
        sharePointDetail.setDownloadDirectory("D:\\result\\");  	
    	
        String token = SharepointConnection.getToken();
        String siteFolderUrl = sharePointDetail.getSiteName() + "/Shared%20Documents";
        String siteURL = "https://" + SharePointCredential.domain +".sharepoint.com" + sharePointDetail.getSiteName();
 
	    List<String> listFolder = sharepointFileService.getListOfFolders(token,siteURL,siteFolderUrl);
		return new ResponseEntity<>("List Folder is :"+listFolder, HttpStatus.OK);

    
    }
    
    
    
    
}
