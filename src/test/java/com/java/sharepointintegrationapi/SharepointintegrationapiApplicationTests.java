package com.java.sharepointintegrationapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.java.sharepointintegrationapi.connection.SharePointDetail;
import com.java.sharepointintegrationapi.controller.SharePointController;
import com.java.sharepointintegrationapi.service.SharepointConnection;
import com.java.sharepointintegrationapi.service.SharepointFileService;
import com.java.sharepointintegrationapi.service.SharepointFileServiceImpl;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SharepointintegrationapiApplicationTests {

	@InjectMocks
	SharePointController sharePointController=new SharePointController();
	
	@InjectMocks
	SharepointFileService sharepointFileService=new SharepointFileServiceImpl();
	
	@InjectMocks
	SharePointDetail sharePointDetail=new SharePointDetail();
	
	@Test
	public void testCheckDownload() {	
		
		System.out.println("inside test");
		String token=SharepointConnection.getToken();
		
		
		//when
		sharepointFileService.downloadFoldersFiles(token,sharePointDetail);	
	
		//then 
		assertEquals(sharePointController.downloadFilesAndFolder(sharePointDetail),HttpStatus.OK);
			
	}
	
	
	
	
	

}
