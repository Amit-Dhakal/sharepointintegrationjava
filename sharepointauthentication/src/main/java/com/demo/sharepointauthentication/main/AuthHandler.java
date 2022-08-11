//package com.demo.sharepointauthentication.main;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.io.IOUtils;
//import org.apache.http.Header;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.assertj.core.util.Arrays;
//import org.assertj.core.util.Lists;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.data.util.Pair;
//
//public class AuthHandler {
//
//    private final MyCreds config;
//
//    public String authenticate() {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//
//        Pair<String, String> bearerRealmAndRessourceId = getBearerRealmAndRessourceId(httpClient);
//        String bearerRealm = bearerRealmAndRessourceId.getLeft();
//        String ressourceId = bearerRealmAndRessourceId.getRight();
//
//        String bearerToken = getBearerToken(bearerRealm, ressourceId, httpClient);
//        return bearerToken;
//    }
//
//    private String getBearerToken(String bearerRealm, String ressourceId, CloseableHttpClient httpClient) {
//        String url = String.format("https://accounts.accesscontrol.windows.net/%s/tokens/OAuth/2", bearerRealm);
//
//        HttpPost postRequest = new HttpPost(url);
//        postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
//
//        String clientId = String.format("%s@%s", config.getClientid(), bearerRealm);
//        String resource = String.format("%s/%s@%s", ressourceId, config.getDomain(), bearerRealm);
//        List<NameValuePair> params = Lists.newArrayList(
//            new BasicNameValuePair("grant_type", "client_credentials"),
//            new BasicNameValuePair("client_id", clientId),
//            new BasicNameValuePair("client_secret", config.getClientsecret()),
//            new BasicNameValuePair("resource", resource)
//        );
//
//        try {
//            postRequest.setEntity(new UrlEncodedFormEntity(params));
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException("Parameter falsch formatiert", e);
//        }
//
//        try  {
//            HttpResponse response = httpClient.execute(postRequest);
//
//            String bodyJson = IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset());
//            JSONObject body = new JSONObject(bodyJson);
//            String bearerToken = body.getString("access_token");
//            return bearerToken;
//        } catch (IOException e) {
//            throw new RuntimeException("Post Request zum Holen des Bearer Tokens fehlgeschlagen", e);
//        }
//    }
//
//    private Pair<String, String> getBearerRealmAndRessourceId(CloseableHttpClient httpClient) {
//        // domain = mysharepoint.sharepoint.com
//        String url = String.format("https://%s/_layouts/15/sharepoint.aspx", config.getDomain());
//
//        HttpGet getRequest = new HttpGet(url);
//        getRequest.setHeader("Authorization", "Bearer");
//
//        try {
//            HttpResponse response = httpClient.execute(getRequest);
//            Header[] headers = response.getHeaders("www-authenticate");
//
//            String bearerRealm = extractHeaderElement(headers, "Bearer realm");
//            String ressourceId = extractHeaderElement(headers, "client_id");
//            return Pair.of(bearerRealm, ressourceId);
//        } catch (IOException e) {
//            throw new RuntimeException("Get Request zum Holen von Bearer realm und client_id fehlgeschlagen", e);
//        }
//    }
//
//    private String extractHeaderElement(Header[] headers, String elementName) {
//        return Arrays.asList(headers).stream()
//                .map(header -> header.getElements())
//                .flatMap(elements -> Arrays.asList(elements).stream())
//                .filter(element -> element.getName().equals(elementName))
//                .findFirst()
//                .orElseThrow()
//                .getValue();
//    }
//}