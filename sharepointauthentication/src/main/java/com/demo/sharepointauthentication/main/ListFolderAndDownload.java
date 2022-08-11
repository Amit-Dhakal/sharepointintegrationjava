package com.demo.sharepointauthentication.main;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


public class ListFolderAndDownload {
	
	
	public static void main(String[] args) {
		
		String bearerToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAvd3d3LnNoYXJlcG9pbnRpbnRlZ3JhdGlvbi5jb21cbkAyZTQ5NTgzYy1kOTk3LTQxYzYtYjM1OC03MDM3MWVkYTRjODQiLCJpc3MiOiIwMDAwMDAwMS0wMDAwLTAwMDAtYzAwMC0wMDAwMDAwMDAwMDBAMmU0OTU4M2MtZDk5Ny00MWM2LWIzNTgtNzAzNzFlZGE0Yzg0IiwiaWF0IjoxNjYwMTI0NzEwLCJuYmYiOjE2NjAxMjQ3MTAsImV4cCI6MTY2MDIxMTQxMCwiaWRlbnRpdHlwcm92aWRlciI6IjAwMDAwMDAxLTAwMDAtMDAwMC1jMDAwLTAwMDAwMDAwMDAwMEAyZTQ5NTgzYy1kOTk3LTQxYzYtYjM1OC03MDM3MWVkYTRjODQiLCJuYW1laWQiOiIzMWNjMWQ1MC1iNjY4LTRjN2QtYjZiMy02N2Y5Yjc2ZGViMTZAMmU0OTU4M2MtZDk5Ny00MWM2LWIzNTgtNzAzNzFlZGE0Yzg0Iiwib2lkIjoiMTk0YTMzZDktZDVjYi00ZmMzLTk0YWUtZDEzODQ3MzZhMzQxIiwic3ViIjoiMTk0YTMzZDktZDVjYi00ZmMzLTk0YWUtZDEzODQ3MzZhMzQxIiwidHJ1c3RlZGZvcmRlbGVnYXRpb24iOiJmYWxzZSJ9.BxYWgbec0qzBbO-3HlktxNA9KqSqcuz1Kuf8bC9Kjn14YvKpkbJRYdkx7pEpsbpIjhfl13S_U7RGNH-iS-v8sGcgFf8ZyFz3MTjVXbEvvG7E2aXkfoMNpYHatdXJ5wXFbam7E3f2qM3s3B2073FQ6Xu9DrFkZISRRaXMslLxzz5tJXiawiUNdblpgetw-juyfeFLbNruGOupsusDFRUb8W16IaqyocrFHfj9BCYh2i8VOBacvZWl7Aifq0XTB6GLc_8R6VY193kg1m0QZ62qPLdpHVaknuH3zhCS4pvFS_6e5K3yiA2QnIQixSCAu2omuTlVgJML_3jcklSiiq6KvQ";
		
		try {
			
		MultiValueMap<String,String> headers=new LinkedMultiValueMap<>();
			
			headers.add("content-type", "application/json");
			headers.add("Authorization", "Bearer"+bearerToken);
	RestTemplate restTemplate=new RestTemplate();

	//String path="https://"+MyCreds.domain+".sharepoints.com/"+MyCreds.site+"?$expand-Folders,Files";

	String path="https://"+"www.sharepointintegration.com"+".sharepoints.com/"+"https://www.sharepointintegration.com/default.aspx"+"?$expand-Folders,Files";

	
	
	
	RequestEntity<String> requestEntity;

		requestEntity = new RequestEntity<String>("",headers,HttpMethod.GET,new URI(path));
		ResponseEntity<String>  responseEntity=restTemplate.exchange(requestEntity,String.class);	
		System.out.println(responseEntity.getBody());

	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		}
	
	
	
	
	

}
