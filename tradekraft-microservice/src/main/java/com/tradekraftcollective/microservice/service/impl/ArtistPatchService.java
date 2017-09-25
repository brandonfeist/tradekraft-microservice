package com.tradekraftcollective.microservice.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.service.IArtistPatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brandonfeist on 9/14/17.
 */
@Service
public class ArtistPatchService implements IArtistPatchService {
    private final Logger logger = LoggerFactory.getLogger(ArtistPatchService.class);

    public static final String ARTIST_NAME_PATH = "/name";
    public static final String ARTIST_DESCRIPTION_PATH = "/description";
    public static final String ARTIST_IMAGE_PATH = "/image";
    public static final String ARTIST_SOUNDCLOUD_PATH = "/soundcloud";
    public static final String ARTIST_FACEBOOK_PATH = "/facebook";
    public static final String ARTIST_TWITTER_PATH = "/twitter";
    public static final String ARTIST_INSTAGRAM_PATH = "/instagram";
    public static final String ARTIST_SPOTIFY_PATH = "/spotify";

    @Override
    public Artist patchArtist(List<JsonPatchOperation> patchOperations, Artist artist) {
        if(artist == null) {
            logger.error("Artist cannot be null");
            throw new ServiceException(ErrorCode.INVALID_ARTIST, "Artist cannot be null");
        }

        Map<String, String> pathMap = new HashMap<>();
        pathMap.put(ARTIST_NAME_PATH, ARTIST_NAME_PATH);
        pathMap.put(ARTIST_DESCRIPTION_PATH, ARTIST_DESCRIPTION_PATH);
        pathMap.put(ARTIST_IMAGE_PATH, ARTIST_IMAGE_PATH);
        pathMap.put(ARTIST_SOUNDCLOUD_PATH, ARTIST_SOUNDCLOUD_PATH);
        pathMap.put(ARTIST_FACEBOOK_PATH, ARTIST_FACEBOOK_PATH);
        pathMap.put(ARTIST_TWITTER_PATH, ARTIST_TWITTER_PATH);
        pathMap.put(ARTIST_INSTAGRAM_PATH, ARTIST_INSTAGRAM_PATH);
        pathMap.put(ARTIST_SPOTIFY_PATH, ARTIST_SPOTIFY_PATH);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode artistJsonNode = objectMapper.valueToTree(artist);

        try {
            JsonPatch patcher = new JsonPatch(patchOperations);
            artistJsonNode = patcher.apply(artistJsonNode);
        } catch(JsonPatchException e) {
            e.printStackTrace();
        }

        Artist patchedArtist = objectMapper.convertValue(artistJsonNode, Artist.class);

        return patchedArtist;
    }
}
