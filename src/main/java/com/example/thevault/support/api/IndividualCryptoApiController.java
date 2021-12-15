// Created by S.C. van Gils
// Creation date 6-12-2021

package com.example.thevault.support.api;

import com.example.thevault.domain.model.CryptoWaarde;
import com.example.thevault.domain.model.Cryptomunt;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.Crypt;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class IndividualCryptoApiController {

    private final Logger logger = LoggerFactory.getLogger(IndividualCryptoApiController.class);

    public IndividualCryptoApiController() {
        super();
        logger.info("New IndividualCryptoApiController");
    }
    private static HttpURLConnection connection;
    private static String apiKey = "951b4fec-c450-4516-88be-f58990fecbf4";

    public static void main(String[] args)  {
        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();

        parameters.add(new BasicNameValuePair("slug","bitcoinWaarde"));
        parameters.add(new BasicNameValuePair("convert","EUR"));

        CryptoWaarde bitcoinWaarde = new CryptoWaarde();
        Cryptomunt ethereum = new Cryptomunt();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try { //TODO betere parsing
            String result = makeAPICall(uri, parameters);
            System.out.println(result);
            JsonNode bitcoinNode = objectMapper.readTree(result);
        //    bitcoinWaarde.setCryptoWaardeId(bitcoinNode.at("/data/1/id").intValue());
        //    bitcoinWaarde.setCryptomunt(bitcoinNode.at("/data/1/name").toString());
         //   bitcoinWaarde.setCryptomunt(bitcoinNode.at("/data/1/symbol").toString());
            bitcoinWaarde.setWaarde(bitcoinNode.at("/data/1/quote/EUR/price").doubleValue());
            System.out.println(bitcoinWaarde);

        } catch (IOException e) {
            System.out.println("Error: cannot access content - " + e.toString());
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
        }
    }

    public static String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException {
        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);

        CloseableHttpResponse response = client.execute(request);
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        return response_content;
    }
}
