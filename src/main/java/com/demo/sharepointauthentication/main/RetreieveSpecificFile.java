package com.demo.sharepointauthentication.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class RetreieveSpecificFile {
	
	public static void main(String[] args) {
	
	try {		
		
		
		String bearerToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAvd3d3LnNoYXJlcG9pbnRpbnRlZ3JhdGlvbi5jb21AMmU0OTU4M2MtZDk5Ny00MWM2LWIzNTgtNzAzNzFlZGE0Yzg0IiwiaXNzIjoiMDAwMDAwMDEtMDAwMC0wMDAwLWMwMDAtMDAwMDAwMDAwMDAwQDJlNDk1ODNjLWQ5OTctNDFjNi1iMzU4LTcwMzcxZWRhNGM4NCIsImlhdCI6MTY2MDM3NTM4MiwibmJmIjoxNjYwMzc1MzgyLCJleHAiOjE2NjA0NjIwODIsImlkZW50aXR5cHJvdmlkZXIiOiIwMDAwMDAwMS0wMDAwLTAwMDAtYzAwMC0wMDAwMDAwMDAwMDBAMmU0OTU4M2MtZDk5Ny00MWM2LWIzNTgtNzAzNzFlZGE0Yzg0IiwibmFtZWlkIjoiMzFjYzFkNTAtYjY2OC00YzdkLWI2YjMtNjdmOWI3NmRlYjE2QDJlNDk1ODNjLWQ5OTctNDFjNi1iMzU4LTcwMzcxZWRhNGM4NCIsIm9pZCI6IjE5NGEzM2Q5LWQ1Y2ItNGZjMy05NGFlLWQxMzg0NzM2YTM0MSIsInN1YiI6IjE5NGEzM2Q5LWQ1Y2ItNGZjMy05NGFlLWQxMzg0NzM2YTM0MSIsInRydXN0ZWRmb3JkZWxlZ2F0aW9uIjoiZmFsc2UifQ.H6eaEv78py1FRTSeCgsxKAbXRzdjgYpgTh2KcoEN2J-TzMJ4b8PmfL0yKA8NcPPD3VDG0kORLdB_diYCk6H24hl44oS5qDVmT_GZ1K21IHQMYDpyF9WS9NqTLitfFbJP9bc82-PQfUrPdld6PUx9PBpxwOjDtVSm5i-VwRre2ZJ6FxoHqciqml719HE-_lE9zlTuFusTrGs5k_JO7gxjCakt5YjEB8ggBDWxtjHVji3uLQKEpp3JwQpaC7qFkanTedXIUaj3bgEZ3fCjRJdUDxUVBNRLytqXK5JiMFzrrwpfs57Ypk3uy8Jbby4AWviYzO-zbbGnzERrEQnya2hakA".trim();

		   //Frame SharePoint siteURL			
		   String siteURL = "https://z0wjs.sharepoint.com/sites/sharepointintegrationjava";
				
		   //Frame SharePoint URL to retrieve all of the files in a folder
		   //URL-Type-1
		 //  String wsUrl = siteURL + "/_api/web/GetFileByServerRelativeUrl('/sites/sharepointintegrationjava/Shared%20Documents/Document.docx')/$value";
		   
		//   String wsUrl="https://z0wjs.sharepoint.com/sites/sharepointintegrationjava/Shared%20Documents/Forms/AllItems.aspx";
		   
	//	   String wsUrl= "https://z0wjs.sharepoint.com/sites/sharepointintegrationjava/_api/web/GetFolderByServerRelativeUrl('/amit-documents')/Files";
		   
		   //https://xxxx.sharepoint.com/_api/web/GetFileByServerRelativeUrl('/sites/hub/dept/COTraining/Wisam/Shared%20Documents/BushfireAreas.txt')/$value
		   //https://<sitename>.sharepoint.com/_api/web?$select=Title
		   
		   String wsUrl="https://z0wjs.sharepoint.com/_api/web/GetFileByServerRelativeUrl('/amitdocs/Document.docx')/$value";
			   
			   
				   //Create HttpURLConnection
		   URL url = new URL(wsUrl);
		   URLConnection connection = url.openConnection();
		   HttpURLConnection httpConn = (HttpURLConnection) connection;
								
		   //Set Header		
		   httpConn.setRequestMethod("GET");				
		   httpConn.setRequestProperty("Authorization", "Bearer " + bearerToken);
		  
		   //Read the response
		   String httpResponseStr = "";
		   InputStreamReader isr = null;
		   if (httpConn.getResponseCode() == 200) {
		     isr = new InputStreamReader(httpConn.getInputStream());
		   } else {
		     isr = new InputStreamReader(httpConn.getErrorStream());
		   }		
		   BufferedReader in = new BufferedReader(isr); 	 		
		   String strLine = "";
		   while ((strLine = in.readLine()) != null) {
		     httpResponseStr = httpResponseStr + strLine;
		   }
		   System.out.println(httpResponseStr);	//Print response
		} catch (Exception e) {
		   //System.out.println("Error while reading file: " + e.getMessage());
		}		
	
	}
	
}
