package com.java.sharepointintegrationapi.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.sharepointintegrationapi.dto.DownloadRequest;
import com.java.sharepointintegrationapi.service.SharepointFileService;
import com.java.sharepointintegrationapi.util.Constants;
import com.java.sharepointintegrationapi.util.FolderList;
import com.java.sharepointintegrationapi.util.FolderList.Node;

@RestController
public class SharePointController {

    @Autowired
    SharepointFileService sharepointFileService;

    @GetMapping("/view")
    public ResponseEntity<?> listFilesAndFolder() throws IOException {
        String filePath = "D:" + File.separator + Constants.shared_folder;
        Node node = FolderList.getNode(new File(filePath));
        return new ResponseEntity<>(node, HttpStatus.OK);

    }

    @PostMapping("/download")
    public ResponseEntity<?> download(DownloadRequest dto) throws IOException {
        //logic to download.
        return new ResponseEntity<>(null, HttpStatus.OK);

    }

}
