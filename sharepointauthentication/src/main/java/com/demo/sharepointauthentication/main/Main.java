package com.demo.sharepointauthentication.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.assertj.core.util.Arrays;
import org.assertj.core.util.Lists;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.stream.*;
import java.util.*;
public class Main {
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
try {
	CloseableHttpClient httpClient=HttpClients.createDefault();

	List<String> bearerRealmAndResourceId=getBearerRealmAndResourceId(httpClient);
	
	String bearerRealm=bearerRealmAndResourceId.get(0);
	String resourceId=bearerRealmAndResourceId.get(1);
	
	String  bearerToken=generateBearerToken(bearerRealm,resourceId,httpClient);
	
	
	listFolders(bearerToken);
	
	System.out.println("My Bearer Token is:"+bearerToken);
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}
	
	
	private static void listFolders(String bearerToken) {
		
	try {
		
	MultiValueMap<String,String> headers=new LinkedMultiValueMap<>();
		
		headers.add("content-type", "application/json");
		headers.add("Authorization", "Bearer"+bearerToken);
RestTemplate restTemplate=new RestTemplate();

String path="https://"+MyCreds.domain+".sharepoints.com/"+MyCreds.site+"?$expand-Folders,Files";

RequestEntity<String> requestEntity;

	requestEntity = new RequestEntity<String>("",headers,HttpMethod.GET,new URI(path));
	ResponseEntity<String>  responseEntity=restTemplate.exchange(requestEntity,String.class);	
	System.out.println(responseEntity.getBody());

} catch (URISyntaxException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

	}
	
	
	

	private static String generateBearerToken(String bearerRealm, String resourceId, CloseableHttpClient httpClient)  {
		// TODO Auto-generated method stub
		String url=String.format("https://accounts.windows.net/%s/tokens/OAuth/2",bearerRealm);

		HttpPost postRequest=new HttpPost(url);
		postRequest.setHeader("content-type","application/x-www-form-urlencoded");	
		String clientId=String.format("%s@%s", MyCreds.clientId,bearerRealm);
		
		String resource=String.format("%s/%s@%s", resourceId,MyCreds.domain+".sharepoint.com",bearerRealm);
		
		List<NameValuePair> params=Lists.newArrayList(new BasicNameValuePair("grant_type", "client_credentials"),
				
				new BasicNameValuePair("client_id",clientId),
				new BasicNameValuePair("client_secret", MyCreds.clientSecret),
				new BasicNameValuePair("resource", resource)
							
				);
		try {
			postRequest.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			
			throw new RuntimeException("parameter false format",e);
		}
			
		try {
			HttpResponse response = httpClient.execute(postRequest);
			String bodyJson=IOUtils.toString(response.getEntity().getContent(),Charset.defaultCharset());
			JSONObject body=new JSONObject(bodyJson);
			System.out.println("This is body ::"+body);	
			String bearerToken=body.getString("access_token");
		     return bearerToken;

		} catch (Exception e) {
			// TODO: handle exception	
			throw new RuntimeException("parameter false format",e);
		}
		
	}

	private static List<String> getBearerRealmAndResourceId(CloseableHttpClient httpClient) throws Exception {
		
		
		try {
			
			String url=String.format("https://%s/_layouts/15/sharepoint.aspx", MyCreds.domain+".sharepoint.com");
			
			List<String> res=new ArrayList<String>();	
			HttpGet getRequest=new HttpGet(url);
			
			getRequest.setHeader("Authorization", "Bearer");
			
			HttpResponse response=httpClient.execute(getRequest);
			
			Header[] headers=response.getHeaders("www-authenticate");		
			String bearerRealm=extractHeaderElement(headers,"Bearer realm");
			String resourceId=extractHeaderElement(headers,"client_id");
			
			res.add(bearerRealm);
			res.add(resourceId);
			return res;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
	}	
	}

	private static String extractHeaderElement(Header[] headers, String bearer_realm) throws Exception{
		
		
		  return ((NameValuePair) Arrays.asList(headers).stream() .map(header->
		  ((Header) header).getElements())
		  .flatMap(elements->Arrays.asList(elements).stream())
		  .filter(element->((NameValuePair) element).getName().equals(bearer_realm))
		 .findFirst() .orElseThrow(null)) 
				  .getValue();
		 
		
						
		
		
	}

}
