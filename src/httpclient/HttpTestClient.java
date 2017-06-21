/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.OutputStreamWriter;

/**
 *
 * @author Talina
 */
public class HttpTestClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        HttpTestClient http = new HttpTestClient();

        System.out.println("\nTesting - Sending Http POST request to server");
        String input = "test";
        try {
            String res = http.postRequest("http://localhost/ignorePath", input);
            System.out.println(res);
        } catch (IOException ex) {
            Logger.getLogger(HttpTestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String postRequest(String urlstr, String content) throws IOException {
        BufferedReader in = null;
        String data = null;
        try {
            final URL url = new URL(urlstr);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            // connection.setRequestProperty("User-Agent", "");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "text/html");
            connection.setRequestProperty("Content-Length", String.valueOf(content.length()));            
            
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                  new OutputStreamWriter(os, "UTF-8"));
            writer.write(content);
            writer.flush();
            writer.close();
            os.close();
         
            int responseCode = connection.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);
                
            in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
            }
            data = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
        return data;
    }

}