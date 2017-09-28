package com.tradekraftcollective.microservice.service;

import com.github.fge.jsonpatch.JsonPatchOperation;
import com.tradekraftcollective.microservice.persistence.entity.Artist;

import java.util.List;

/**
 * Created by brandonfeist on 9/14/17.
 */
public interface IArtistPatchService {
    Artist patchArtist(List<JsonPatchOperation> patchOperations, Artist artist);
}
