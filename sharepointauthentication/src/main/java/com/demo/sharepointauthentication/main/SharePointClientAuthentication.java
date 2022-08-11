package com.demo.sharepointauthentication.main;

import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.auth.AuthScope;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
//username is :  AmitDhakal@SwivtTechnologies745.onmicrosoft.com
//-then how to authenticate in sharepoint from java/spring.(completed)
//Connect to sharepoint folders, list docs and allow download (to do)

public class SharePointClientAuthentication {
	
	
public static void main(String[] args) throws Exception {
	
	// Abstract credentials provider that maintains a collection of user credentials. 
    CredentialsProvider credsProvider = new BasicCredentialsProvider();
    
    // Default scope matching any host, port, realm and authentication scheme.
    // The class represents an authentication scope consisting of a host name, a port number, a realm name and an authentication scheme name which Credentials apply to. 
    credsProvider.setCredentials(new AuthScope(AuthScope.ANY),new NTCredentials("amitdhakal", "Eddie456?dhakal456", "https://www.sharepointintegration.com/default.aspx", "www.sharepointintegration.com"));
    
    
    //CloseableHttpClient is the base class of the httpclient library, the one all implementations use.
    CloseableHttpClient httpclient = HttpClients.custom()
            .setDefaultCredentialsProvider(credsProvider)
            .build();
    try {
        HttpGet httpget = new HttpGet("http://hostname/_api/web/lists");
        System.out.println("Executing request " + httpget.getRequestLine());
        CloseableHttpResponse response = httpclient.execute(httpget);
        try {
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            EntityUtils.consume(response.getEntity());
            
        } finally {
            response.close();
        }
    } finally {
        httpclient.close();
    }
}

}