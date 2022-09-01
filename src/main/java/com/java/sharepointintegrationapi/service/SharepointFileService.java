package com.java.sharepointintegrationapi.service;

import java.io.IOException;
import java.util.List;

import com.java.sharepointintegrationapi.dto.SharePointDetail;



public interface SharepointFileService {

    public void downloadFoldersFiles(String token,SharePointDetail sharePointDetail);
    
     List<String> getListOfFolders(String token, String siteURL, String siteFolderUrl) throws IOException;
    
    
}
