package com.tradekraftcollective.microservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradekraftcollective.microservice.service.ISpotifyManagementService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

/**
 * Created by brandonfeist on 11/13/17.
 */
@Slf4j
@RestController
@RequestMapping("/v1/spotify")
public class SpotifyManagementController {

    private @Value("${vcap.services.spotify.credentials.client_id}")
    String clientId;

    private @Value("${vcap.services.spotify.credentials.client_secret}")
    String clientSecret;

    @Inject
    RestTemplate restTemplate;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    ISpotifyManagementService spotifyManagementService;

    @RequestMapping(value = "/authorize", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSpotifyToken(
            @RequestParam(value = "code") String authorizationCode,
            @RequestParam(value = "redirect_uri") String redirectUri,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("getSpotifyToken [{}]", xRequestId);

        JSONObject spotifyAuthResponseJson = spotifyManagementService.getSpotifyAuthorizationToken(authorizationCode, redirectUri);

        return new ResponseEntity<>(spotifyAuthResponseJson, HttpStatus.OK);
    }

    @RequestMapping(value = "/authorize/refresh", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refreshSpotifyToken(
            @RequestParam(value = "refresh_token") String refreshToken,
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("refreshSpotifyToken [{}]", xRequestId);

        JSONObject spotifyAuthResponseJson = spotifyManagementService.refreshSpotifyToken(authorization, refreshToken);

        return new ResponseEntity<>(spotifyAuthResponseJson, HttpStatus.OK);
    }
}
