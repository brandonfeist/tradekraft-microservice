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
    Page<Event> getEvents(int page, int pageSize, String sortField, String sortOrder, boolean officialEventsOnly, boolean pastEvents, boolean futureEvents);

    Event getEvent(String eventSlug);

    Event createEvent(Event event);

    Event uploadEventImage(String eventSlug, MultipartFile imageFile);

    Event patchEvent(final List<JsonPatchOperation> patchOperations, final String artistSlug);

    void deleteEvent(String eventSlug);
}
