package com.tradekraftcollective.microservice.service;

import com.github.fge.jsonpatch.JsonPatchOperation;
import com.tradekraftcollective.microservice.persistence.entity.Event;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by brandonfeist on 9/28/17.
 */
public interface IEventManagementService {

    /**
     * Gets a page of events and sorts by given event field and sort order.
     *
     * @param page The page number of events
     * @param pageSize The number of events in a single page
     * @param sortField The event field to sort by
     * @param sortOrder Sort by either asc or desc order
     * @param officialEventsOnly Query only official tradekraft events
     * @param pastEvents Query only events after current time
     * @param futureEvents Query only events past current time
     *
     * @return A page of events
     */
    Page<Event> getEvents(int page, int pageSize, String sortField, String sortOrder, boolean officialEventsOnly, boolean pastEvents, boolean futureEvents);

    /**
     * Returns a single event with specified slug.
     *
     * @param eventSlug The event slug
     *
     * @return Event who's slug matches eventSlug
     */
    Event getEvent(String eventSlug);

    /**
     * Create and save an event in the database.
     *
     * @param event The event to be created and saved
     *
     * @return The event that is created
     */
    Event createEvent(Event event);

    /**
     * Updates event with given slug.
     *
     * @param eventUpdates Updates to be made to the event
     * @param eventSlug Slug of event that is being updated
     * @return The updated event
     */
    Event updateEvent(final Event eventUpdates, final String eventSlug);

    /**
     * Delete the event who's slug matches the event slug String.
     *
     * @param eventSlug The event slug
     */
    void deleteEvent(String eventSlug);
}
