package com.tradekraftcollective.microservice.service;

import com.github.fge.jsonpatch.JsonPatchOperation;
import com.tradekraftcollective.microservice.persistence.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by brandonfeist on 9/28/17.
 */
public interface IEventManagementService {
    Page<Event> getEvents(int page, int pageSize, String sortField, String sortOrder);

    Event getEvent(String eventSlug);

    Event createEvent(Event event, MultipartFile imageFile);

    Event patchEvent(final List<JsonPatchOperation> patchOperations, final MultipartFile imageFile, final String artistSlug);

    void deleteEvent(String eventSlug);
}
