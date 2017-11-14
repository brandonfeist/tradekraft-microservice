package com.tradekraftcollective.microservice.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tradekraftcollective.microservice.service.ISpotifyManagementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by brandonfeist on 11/13/17.
 */
@Slf4j
@Service
public class SpotifyManagementService implements ISpotifyManagementService {
    private @Value("${vcap.services.spotify.credentials.client_id}")
    String clientId;

    private @Value("${vcap.services.spotify.credentials.client_secret}")
    String clientSecret;

    @Inject
    RestTemplate restTemplate;

    @Inject
    ObjectMapper objectMapper;

    @Override
    public String getSpotifyAuthorizationToken(String authCode, String redirectUri) {
        String uri = "https://accounts.spotify.com/api/token";
//        String stringToEncode = clientId + ":" + clientSecret;
//        byte[] encodedBytes = Base64.encodeBase64(stringToEncode.getBytes());
//        System.out.println("encodedBytes " + new String(encodedBytes));

//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        JsonNode postBody = objectMapper.createObjectNode();
        ((ObjectNode)postBody).put("grant_type", "authorization_code");
        ((ObjectNode)postBody).put("code", authCode);
        ((ObjectNode)postBody).put("redirect_uri", redirectUri);

        String urlEncodedBody = "grant_type=authorization_code&" +
                "code=" + authCode + "&" +
                "redirect_uri=" + redirectUri;
//
//        HttpEntity<String> entity = new HttpEntity<>(postBody.toString(), headers);
//
//        log.info("WTF: {} {}", entity.getBody(), entity.getHeaders());

//        ResponseEntity<String> response = restTemplate.getForEntity("https://accounts.spotify.com/authorize", String.class);
//        ResponseEntity<JsonNode> response = restTemplate.postForEntity(uri, postBody, JsonNode.class);
//        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
//
//        JsonNode responseJson = null;
//        try {
//            responseJson = objectMapper.readTree(response.getBody());
//        } catch(IOException e) {
//            log.error(e.toString());
//        }

        final HttpPost method = new HttpPost(uri);

        String idSecret = clientId + ":" + clientSecret;
        String idSecretEncoded = new String(Base64.encodeBase64(idSecret.getBytes()));

        method.setHeader("Authorization", "Basic " + idSecretEncoded);

        StringEntity stringEntity = new StringEntity(urlEncodedBody, ContentType.APPLICATION_FORM_URLENCODED);
        method.setEntity(stringEntity);

        HttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        final ConnectionConfig connectionConfig = ConnectionConfig
                .custom()
                .setCharset(Charset.forName("UTF-8"))
                .build();
        final RequestConfig requestConfig = RequestConfig
                .custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .build();
        final CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultConnectionConfig(connectionConfig)
                .setDefaultRequestConfig(requestConfig)
                .build();

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(method);

            String responseBody = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

            log.info("METHOD: {}", method.getEntity().getContent());
            log.info("RESPONSE BODY: {}", responseBody);

            return responseBody;
        } catch(IOException e) {
            log.error(e.toString());
        } finally {
            method.releaseConnection();
        }

        return null;
    }
}
