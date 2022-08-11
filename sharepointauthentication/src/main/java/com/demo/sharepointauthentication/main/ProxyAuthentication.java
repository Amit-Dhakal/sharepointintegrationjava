//package com.demo.sharepointauthentication.main;
//
//import org.apache.http.auth.NTCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.ProxyAuthenticationStrategy;
//
//public class ProxyAuthentication {
//
//	
//	
//	
//	public static void main(String[] args) {
//		
//		NTCredentials ntCreds = new NTCredentials(ntUsername, ntPassword,localMachineName, domainName );
//		CredentialsProvider credsProvider = new BasicCredentialsProvider();
//		credsProvider.setCredentials(new AuthScope(proxyHost,proxyPort), ntCreds );
//		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
//		clientBuilder.useSystemProperties();
//		clientBuilder.setProxy(new HttpHost(pxInfo.getProxyURL(), pxInfo.getProxyPort()));
//		clientBuilder.setDefaultCredentialsProvider(credsProvider);
//		clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
//
//		CloseableHttpClient client = clientBuilder.build();
//		
//		
//	}
//	
//	
//}
