package com.tradekraftcollective.microservice.service;

import com.github.fge.jsonpatch.JsonPatchOperation;
import com.tradekraftcollective.microservice.persistence.entity.Event;

import java.util.List;

/**
 * Created by brandonfeist on 10/8/17.
 */
public interface IEventPatchService {
    Event patchEvent(List<JsonPatchOperation> patchOperations, Event event);
}
