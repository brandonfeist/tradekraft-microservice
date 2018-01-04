package com.tradekraftcollective.microservice.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonpatch.*;
import com.tradekraftcollective.microservice.constants.PatchOperationConstants;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.service.IArtistPatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brandonfeist on 9/14/17.
 */
@Slf4j
@Service
public class ArtistPatchService implements IArtistPatchService {
    public static final String ARTIST_NAME_PATH = "/name";
    public static final String ARTIST_DESCRIPTION_PATH = "/description";
    public static final String ARTIST_IMAGE_PATH = "/image";
    public static final String ARTIST_SOUNDCLOUD_PATH = "/soundcloud";
    public static final String ARTIST_FACEBOOK_PATH = "/facebook";
    public static final String ARTIST_TWITTER_PATH = "/twitter";
    public static final String ARTIST_INSTAGRAM_PATH = "/instagram";
    public static final String ARTIST_SPOTIFY_PATH = "/spotify";
    public static final String YEARS_ACTIVE_PATH = "/yearsActive";

    @Inject
    ObjectMapper objectMapper;

    @Override
    public Artist patchArtist(List<JsonPatchOperation> patchOperations, Artist artist) {
        if(artist == null) {
            log.error("Artist cannot be null");
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
        pathMap.put(YEARS_ACTIVE_PATH, YEARS_ACTIVE_PATH);

        for(JsonPatchOperation operation : patchOperations) {
            if(((PathValueOperation) operation).getPath().equals(ARTIST_IMAGE_PATH)) {
                log.error("Cannot patch image from patch endpoint. Must use artist photo endpoint.");
                throw new ServiceException(ErrorCode.INVALID_PATCH,
                        "Cannot patch image from patch endpoint. Must use artist image endpoint.");
            }

            if(PatchOperationConstants.COPY.equals(operation.getOp())
                    || PatchOperationConstants.MOVE.equals(operation.getOp())) {
                DualPathOperation dualPathOperation = (DualPathOperation) operation;

            } else if(PatchOperationConstants.REMOVE.equals(operation.getOp())) {
                RemoveOperation removeOperation = (RemoveOperation) operation;

            } else {
                PathValueOperation pathValueOperation = (PathValueOperation) operation;
            }
        }

        JsonNode artistJsonNode = objectMapper.valueToTree(artist);

        ((ObjectNode) artistJsonNode).remove("releases");

        ((ObjectNode) artistJsonNode).put("image", artist.getImageName());

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
