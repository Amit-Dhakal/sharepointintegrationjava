package com.demo.sharepointauthentication.main;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
	
	/**
	 * A utility that downloads a file from a URL.
	 * @author www.codejava.net
	 *
	 */
	public class HttpDownloadUtility {
	    private static final int BUFFER_SIZE = 4096;

	    /**
	     * Downloads a file from a URL
	     * @param fileURL HTTP URL of the file to be downloaded
	     * @param saveDir path of the directory to save the file
	     * @throws IOException
	     */
	    public static void downloadFile(String fileURL, String saveDir)
	   
	            throws IOException {
	    	
	    	
			 String accessToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAvMngxMDZuLnNoYXJlcG9pbnQuY29tQGRhOWUzYjdiLTFhMjMtNDI3OS1iOWZiLWRmZWU3NDRkZmYxMCIsImlzcyI6IjAwMDAwMDAxLTAwMDAtMDAwMC1jMDAwLTAwMDAwMDAwMDAwMEBkYTllM2I3Yi0xYTIzLTQyNzktYjlmYi1kZmVlNzQ0ZGZmMTAiLCJpYXQiOjE2NjA1NTI5MjgsIm5iZiI6MTY2MDU1MjkyOCwiZXhwIjoxNjYwNjM5NjI4LCJpZGVudGl0eXByb3ZpZGVyIjoiMDAwMDAwMDEtMDAwMC0wMDAwLWMwMDAtMDAwMDAwMDAwMDAwQGRhOWUzYjdiLTFhMjMtNDI3OS1iOWZiLWRmZWU3NDRkZmYxMCIsIm5hbWVpZCI6IjJlN2I3MjlmLTgxOWItNDJlZC05NGRlLTI0Y2ZkNTE2MTJmZkBkYTllM2I3Yi0xYTIzLTQyNzktYjlmYi1kZmVlNzQ0ZGZmMTAiLCJvaWQiOiIwOWZkMjM4OC1kMjk0LTRjMGYtYmQwNy03ZjAwYzgxNjhjM2MiLCJzdWIiOiIwOWZkMjM4OC1kMjk0LTRjMGYtYmQwNy03ZjAwYzgxNjhjM2MiLCJ0cnVzdGVkZm9yZGVsZWdhdGlvbiI6ImZhbHNlIn0.RRtEaO4WJb-orFuVUZbe_PL4o8EZs0vq_w8fUr7H6dg5rGavB-e9Se6slaaOqDPzvbGCGPqqqdRQRCbw5oFZ3kn1deldPKYpUykgEFeKS78ncXWr1bTgX_y_SkcJU0Tjeocoa1ryLUZhwYulLDiMNw4TKKPmLOXRtkBjQBvDTmC4GxDuh50FFStzi3SNbhcAjyaduYKY52HZndOwY839cxfEIlsmWu9Sg8vLvKhYzgiOgDeZGhPQfSgPDDhNroelu9RBY_wz1ZzmGYNNxMLBjlYL-_yPXnPeNR9NAbiTNfpbXTAnsDYCWPbIdHk9dy5SH7QRA-4UgkxvZMlur1Uq4A";

	        URL url = new URL(fileURL);
	        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	        httpConn.setRequestMethod("GET");				
			   httpConn.setRequestProperty("Authorization", "Bearer " + accessToken);
			   httpConn.setRequestProperty("accept", "application/json;odata=");
	        
	        	        
	        int responseCode = httpConn.getResponseCode();
	        
	            
	          
	        

	 
	        // always check HTTP response code first
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	        	
	        	
	        	
	            String fileName = "";
	            String disposition = httpConn.getHeaderField("Content-Disposition");
	            String contentType = httpConn.getContentType();
	            int contentLength = httpConn.getContentLength();
	 
	            if (disposition != null) {
	                // extracts file name from header field
	                int index = disposition.indexOf("filename=");
	                if (index > 0) {
	                    fileName = disposition.substring(index + 10,
	                            disposition.length() - 1);
	                }
	            } else {
	                // extracts file name from URL
	                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
	                        fileURL.length());
	            }
	 
	            System.out.println("Content-Type = " + contentType);
	            System.out.println("Content-Disposition = " + disposition);
	            System.out.println("Content-Length = " + contentLength);
	            System.out.println("fileName = " + fileName);
	 
	            // opens input stream from the HTTP connection
	            InputStream inputStream = httpConn.getInputStream();
	            String saveFilePath = saveDir + File.separator + fileName;
	             
	            // opens an output stream to save into file
	            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
	 
	            int bytesRead = -1;
	            byte[] buffer = new byte[BUFFER_SIZE];
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }
	 
	            outputStream.close();
	            inputStream.close();
	 
	            System.out.println("File downloaded");
	        } else {
	            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
	        }
	        httpConn.disconnect();
	    }
	
	

}
