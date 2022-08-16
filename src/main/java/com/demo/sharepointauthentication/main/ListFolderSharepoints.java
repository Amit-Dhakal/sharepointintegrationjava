package com.demo.sharepointauthentication.main;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ListFolderSharepoints {
	
	
	
	
	public static void main(String[] args) {
		
		String site="sites/sharepointintegrationjava/_api/web/GetFolderByServerRelativeUrl('/sites/sharepointintegrationjava/Shared%20Documents')";
		
		String bearerToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAvd3d3LnNoYXJlcG9pbnRpbnRlZ3JhdGlvbi5jb21AMmU0OTU4M2MtZDk5Ny00MWM2LWIzNTgtNzAzNzFlZGE0Yzg0IiwiaXNzIjoiMDAwMDAwMDEtMDAwMC0wMDAwLWMwMDAtMDAwMDAwMDAwMDAwQDJlNDk1ODNjLWQ5OTctNDFjNi1iMzU4LTcwMzcxZWRhNGM4NCIsImlhdCI6MTY2MDM3NTM4MiwibmJmIjoxNjYwMzc1MzgyLCJleHAiOjE2NjA0NjIwODIsImlkZW50aXR5cHJvdmlkZXIiOiIwMDAwMDAwMS0wMDAwLTAwMDAtYzAwMC0wMDAwMDAwMDAwMDBAMmU0OTU4M2MtZDk5Ny00MWM2LWIzNTgtNzAzNzFlZGE0Yzg0IiwibmFtZWlkIjoiMzFjYzFkNTAtYjY2OC00YzdkLWI2YjMtNjdmOWI3NmRlYjE2QDJlNDk1ODNjLWQ5OTctNDFjNi1iMzU4LTcwMzcxZWRhNGM4NCIsIm9pZCI6IjE5NGEzM2Q5LWQ1Y2ItNGZjMy05NGFlLWQxMzg0NzM2YTM0MSIsInN1YiI6IjE5NGEzM2Q5LWQ1Y2ItNGZjMy05NGFlLWQxMzg0NzM2YTM0MSIsInRydXN0ZWRmb3JkZWxlZ2F0aW9uIjoiZmFsc2UifQ.H6eaEv78py1FRTSeCgsxKAbXRzdjgYpgTh2KcoEN2J-TzMJ4b8PmfL0yKA8NcPPD3VDG0kORLdB_diYCk6H24hl44oS5qDVmT_GZ1K21IHQMYDpyF9WS9NqTLitfFbJP9bc82-PQfUrPdld6PUx9PBpxwOjDtVSm5i-VwRre2ZJ6FxoHqciqml719HE-_lE9zlTuFusTrGs5k_JO7gxjCakt5YjEB8ggBDWxtjHVji3uLQKEpp3JwQpaC7qFkanTedXIUaj3bgEZ3fCjRJdUDxUVBNRLytqXK5JiMFzrrwpfs57Ypk3uy8Jbby4AWviYzO-zbbGnzERrEQnya2hakA";
				
		MultiValueMap<String,String> headers=new LinkedMultiValueMap<String, String>();
		
		headers.set("Content-type", "application/json;odata=verbose");
		headers.set("Authorization","Bearer " + bearerToken);
		RestTemplate restTemplate=new RestTemplate();
		
		//String path="https://"+"z0wjs.sharepoint.com/"+site+"?$expand=Folders,Files";
		
		String path="https://z0wjs.sharepoint.com/sites/sharepointintegrationjava/Shared%20Documents/Forms/AllItems.aspx";
			
		RequestEntity<String> requestEntity;
		try {
			requestEntity = new RequestEntity<String>("",headers,HttpMethod.GET,new URI(path));
			ResponseEntity<String> responseEntity=restTemplate.exchange(requestEntity,String.class);
			System.out.println(responseEntity.getBody());

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	

}
