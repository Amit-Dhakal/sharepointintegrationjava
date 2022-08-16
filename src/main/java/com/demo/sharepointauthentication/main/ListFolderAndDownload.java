package com.demo.sharepointauthentication.main;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class ListFolderAndDownload {
	
	public static void main(String[] args) {
		
		 String accessToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAvMngxMDZuLnNoYXJlcG9pbnQuY29tQGRhOWUzYjdiLTFhMjMtNDI3OS1iOWZiLWRmZWU3NDRkZmYxMCIsImlzcyI6IjAwMDAwMDAxLTAwMDAtMDAwMC1jMDAwLTAwMDAwMDAwMDAwMEBkYTllM2I3Yi0xYTIzLTQyNzktYjlmYi1kZmVlNzQ0ZGZmMTAiLCJpYXQiOjE2NjA1NTI5MjgsIm5iZiI6MTY2MDU1MjkyOCwiZXhwIjoxNjYwNjM5NjI4LCJpZGVudGl0eXByb3ZpZGVyIjoiMDAwMDAwMDEtMDAwMC0wMDAwLWMwMDAtMDAwMDAwMDAwMDAwQGRhOWUzYjdiLTFhMjMtNDI3OS1iOWZiLWRmZWU3NDRkZmYxMCIsIm5hbWVpZCI6IjJlN2I3MjlmLTgxOWItNDJlZC05NGRlLTI0Y2ZkNTE2MTJmZkBkYTllM2I3Yi0xYTIzLTQyNzktYjlmYi1kZmVlNzQ0ZGZmMTAiLCJvaWQiOiIwOWZkMjM4OC1kMjk0LTRjMGYtYmQwNy03ZjAwYzgxNjhjM2MiLCJzdWIiOiIwOWZkMjM4OC1kMjk0LTRjMGYtYmQwNy03ZjAwYzgxNjhjM2MiLCJ0cnVzdGVkZm9yZGVsZWdhdGlvbiI6ImZhbHNlIn0.RRtEaO4WJb-orFuVUZbe_PL4o8EZs0vq_w8fUr7H6dg5rGavB-e9Se6slaaOqDPzvbGCGPqqqdRQRCbw5oFZ3kn1deldPKYpUykgEFeKS78ncXWr1bTgX_y_SkcJU0Tjeocoa1ryLUZhwYulLDiMNw4TKKPmLOXRtkBjQBvDTmC4GxDuh50FFStzi3SNbhcAjyaduYKY52HZndOwY839cxfEIlsmWu9Sg8vLvKhYzgiOgDeZGhPQfSgPDDhNroelu9RBY_wz1ZzmGYNNxMLBjlYL-_yPXnPeNR9NAbiTNfpbXTAnsDYCWPbIdHk9dy5SH7QRA-4UgkxvZMlur1Uq4A";
		    String wsUrl="https://2x106n.sharepoint.com/sites/sharepointjavaintegration/_api/web/GetFolderByServerRelativeUrl('/sites/sharepointjavaintegration/Shared%20Documents/')?$expand=Folders,Files";
					
		 try {
		MultiValueMap<String,String> headers=new LinkedMultiValueMap<>();
			headers.add("content-type", "application/json");
			headers.add("accept", "application/json;odata="); //To get response in JSON

			headers.add("Authorization","Bearer " +accessToken);
					
			
	RestTemplate restTemplate=new RestTemplate();
	
	// String wsUrl= "https://"+".sharepointintegration.com"+".sharepoint.com/"+site+"?$expand=Folders,Files";
	//String wsUrl="https://z0wjs.sharepoint.com/sites/sharepointintegrationjava/Shared%20Documents/Forms/AllItems.aspx";
	
	RequestEntity<String> requestEntity;
		requestEntity = new RequestEntity<String>(" ",headers,HttpMethod.GET,new URI(wsUrl));
		ResponseEntity<String>  responseEntity=restTemplate.exchange(requestEntity,String.class);	
		
		
		System.out.println(responseEntity.getBody());
		
	//	JSONObject json = new JSONObject();	
	//	json.put("responseEntity", responseEntity);
				
		
	} catch (URISyntaxException e) {
		e.printStackTrace();
	}
}
}

//https://z0wjs.sharepoint.com/sites/sharepointintegrationjava/Shared%20Documents/Forms/AllItems.aspx