package com.java.sharepointintegrationapi.service;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.assertj.core.util.Lists;

import com.java.sharepointintegrationapi.connection.SharePointCredential;

public class SharepointConnection {

    public static String getToken() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        List<String> bearerRealmAndResourceId = getBearerRealmAndResourceId(httpClient);

        String bearerRealm = bearerRealmAndResourceId.get(0);
        String resourceId = bearerRealmAndResourceId.get(1);

        return generateBearerToken(bearerRealm, resourceId, httpClient);
    }

    private static String generateBearerToken(String bearerRealm, String resourceId, CloseableHttpClient httpClient) {
        String url = "https://accounts.accesscontrol.windows.net/" + bearerRealm + "/tokens/OAuth/2";

        HttpPost postRequest = new HttpPost(url);
        postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");

        String clientId = String.format("%s@%s", SharePointCredential.clientId, bearerRealm);
        String resource = String.format("%s/%s@%s", resourceId, SharePointCredential.domain + ".sharepoint.com", bearerRealm);

        List<NameValuePair> params = Lists.newArrayList(
                new BasicNameValuePair("grant_type", "client_credentials"),
                new BasicNameValuePair("client_id", clientId),
                new BasicNameValuePair("client_secret", SharePointCredential.clientSecret),
                new BasicNameValuePair("resource", resource)
        );

        try {
            postRequest.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse response = httpClient.execute(postRequest);

            String bodyJson = IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset());
            String bearerToken = "";
            if (bodyJson.contains("access_token\":\"")) {
                int i1 = bodyJson.indexOf("access_token\":\"");
                String str1 = bodyJson.substring(i1 + 15);
                int i2 = str1.indexOf("\"}");
                String str2 = str1.substring(0, i2);
                bearerToken = str2;
            }
            return bearerToken;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static List<String> getBearerRealmAndResourceId(CloseableHttpClient httpClient) {
        String url = String.format("https://%s/_layouts/15/sharepoint.aspx", SharePointCredential.domain + ".sharepoint.com");

        List<String> res = new ArrayList<>();
        HttpGet getRequest = new HttpGet(url);
        getRequest.setHeader("Authorization", "Bearer");

        try {
            HttpResponse response = httpClient.execute(getRequest);
            Header[] headers = response.getHeaders("www-authenticate");

            String bearerRealm = extractHeaderElement(headers, "Bearer realm");
            String resourceId = extractHeaderElement(headers, "client_id");
            res.add(bearerRealm);
            res.add(resourceId);
            return res;
        } catch (Exception ex) {
            throw new RuntimeException("error : ", ex);
        }
    }

    private static String extractHeaderElement(Header[] headers, String bearer_realm) throws Exception {
        return Arrays.stream(headers)
                .map(Header::getElements)
                .flatMap(Arrays::stream)
                .filter(f -> f.getName().equals(bearer_realm))
                .findFirst()
                .orElseThrow(Exception::new)
                .getValue();
    }
}
