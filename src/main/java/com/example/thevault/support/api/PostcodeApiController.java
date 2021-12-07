/*@author Wim 20211202
ophalen van straat en woonplaats obv postcode huisnummer
https://geodata.nationaalgeoregister.nl/
op 07-12-2021 werkt het nog niet, nice to have, dus als er tijd is kan iemand dit verder oppakken

 */

package com.example.thevault.support.api;

import com.example.thevault.domain.model.Adres;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class PostcodeApiController {

    public static String adresBijPostcode(String postcode, String huisnummer) throws URISyntaxException, IOException {
        String response = "";
        String urlString = "https://geodata.nationaalgeoregister.nl/locatieserver/free?fq=postcode:" + postcode + "&fq=huisnummer~"+ huisnummer + "*";

        URIBuilder ub = new URIBuilder(urlString);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(urlString);

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("", "");

        CloseableHttpResponse response_ = client.execute(request);
        try {
            System.out.println(response_.getStatusLine());
            HttpEntity entity = response_.getEntity();
            response = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response_.close();
        }
        return response;
//        HttpURLConnection conn;
//        String urlString = "https://geodata.nationaalgeoregister.nl/locatieserver/free?fq=postcode:" + postcode + "&fq=huisnummer~"+ huisnummer + "*";
//        URIBuilder uri = new URIBuilder(urlString);
//        InputStream is = conn.getInputStream();
//        String response = adresVanuitInput(is);
//        return response;

    }

    private static String adresVanuitInput(InputStream is){
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try{
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
           if (br != null){
               try {
                   br.close();
               }catch (IOException e){
                   e.printStackTrace();

               }
           }
        }
        return sb.toString();
    }
}

