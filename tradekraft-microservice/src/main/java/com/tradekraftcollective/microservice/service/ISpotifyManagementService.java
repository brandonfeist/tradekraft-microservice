package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.model.spotify.SpotifyAlbum;
import net.sf.json.JSONObject;

/**
 * Created by brandonfeist on 11/13/17.
 */
public interface ISpotifyManagementService {
    JSONObject getSpotifyAuthorizationToken(String authCode, String redirectUri);

    JSONObject refreshSpotifyToken(String authorization, String refreshToken);

    SpotifyAlbum getSpotifyAlbumInformation(String albumId);
}
