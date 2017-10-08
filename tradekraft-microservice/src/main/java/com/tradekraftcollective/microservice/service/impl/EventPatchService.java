package com.tradekraftcollective.microservice.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.*;
import com.tradekraftcollective.microservice.constants.PatchOperationConstants;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Event;
import com.tradekraftcollective.microservice.service.IEventPatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brandonfeist on 10/8/17.
 */
public class EventPatchService implements IEventPatchService {
    private final Logger logger = LoggerFactory.getLogger(EventPatchService.class);

    public static final String EVENT_NAME_PATH = "/name";
    public static final String EVENT_IMAGE_PATH = "/image";
    public static final String EVENT_DESCRIPTION_PATH = "/description";
    public static final String EVENT_TICKET_LINK_PATH = "/ticketLink";
    public static final String EVENT_ENTRY_AGE_PATH = "/entryAge";
    public static final String EVENT_ADDRESS_PATH = "/address";
    public static final String EVENT_CITY_PATH = "/city";
    public static final String EVENT_STATE_PATH = "/state";
    public static final String EVENT_ZIP_PATH = "/zip";

    @Override
    public Event patchEvent(List<JsonPatchOperation> patchOperations, Event event) {
        if(event == null) {
            logger.error("Event cannot be null");
            throw new ServiceException(ErrorCode.INVALID_ARTIST, "Event cannot be null");
        }

        Map<String, String> pathMap = new HashMap<>();
        pathMap.put(EVENT_NAME_PATH, EVENT_NAME_PATH);
        pathMap.put(EVENT_IMAGE_PATH, EVENT_IMAGE_PATH);

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
        JsonNode eventJsonNode = objectMapper.valueToTree(event);

        try {
            JsonPatch patcher = new JsonPatch(patchOperations);
            eventJsonNode = patcher.apply(eventJsonNode);
        } catch(JsonPatchException e) {
            e.printStackTrace();
        }

        Event patchedEvent = objectMapper.convertValue(eventJsonNode, Event.class);

        return patchedEvent;
    }
}
