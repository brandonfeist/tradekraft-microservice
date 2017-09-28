package com.tradekraftcollective.microservice.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.*;
import com.tradekraftcollective.microservice.constants.PatchOperationConstants;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Genre;
import com.tradekraftcollective.microservice.service.IGenrePatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brandonfeist on 9/27/17.
 */
@Service
public class GenrePatchService implements IGenrePatchService {
    private static final Logger logger = LoggerFactory.getLogger(GenrePatchService.class);

    public static final String GENRE_NAME_PATH = "/name";
    public static final String GENRE_COLOR_PATH = "/color";
    public static final String GENRE_HUE_PATH = "/hue";

    @Override
    public Genre patchGenre(List<JsonPatchOperation> patchOperations, Genre genre) {
        if(genre == null) {
            logger.error("Genre cannot be null");
            throw new ServiceException(ErrorCode.INVALID_GENRE, "Genre cannot be null");
        }

        Map<String, String> pathMap = new HashMap<>();
        pathMap.put(GENRE_NAME_PATH, GENRE_NAME_PATH);
        pathMap.put(GENRE_COLOR_PATH, GENRE_COLOR_PATH);
        pathMap.put(GENRE_HUE_PATH, GENRE_HUE_PATH);

        for(JsonPatchOperation operation : patchOperations) {
            if(PatchOperationConstants.COPY.equals(operation.getOp())
                    || PatchOperationConstants.MOVE.equals(operation.getOp())) {
                DualPathOperation dualPathOperation = (DualPathOperation) operation;

            } else if(PatchOperationConstants.REMOVE.equals(operation.getOp())) {
                RemoveOperation removeOperation = (RemoveOperation) operation;

            } else {
                PathValueOperation pathValueOperation = (PathValueOperation) operation;

            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode genreJsonNode = objectMapper.valueToTree(genre);

        try {
            JsonPatch patcher = new JsonPatch(patchOperations);
            genreJsonNode = patcher.apply(genreJsonNode);
        } catch(JsonPatchException e) {
            e.printStackTrace();
        }

        Genre patchedGenre = objectMapper.convertValue(genreJsonNode, Genre.class);

        return patchedGenre;
    }
}
