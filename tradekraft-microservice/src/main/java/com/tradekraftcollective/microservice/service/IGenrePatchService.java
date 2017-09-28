package com.tradekraftcollective.microservice.service;

import com.github.fge.jsonpatch.JsonPatchOperation;
import com.tradekraftcollective.microservice.persistence.entity.Genre;

import java.util.List;

/**
 * Created by brandonfeist on 9/27/17.
 */
public interface IGenrePatchService {
    Genre patchGenre(List<JsonPatchOperation> patchOperations, Genre genre);
}
