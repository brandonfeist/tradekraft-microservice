package com.tradekraftcollective.microservice.service.impl;

import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Event;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
import com.tradekraftcollective.microservice.repository.IEventRepository;
import com.tradekraftcollective.microservice.service.IEventManagementService;
import com.tradekraftcollective.microservice.specification.EventSpecification;
import com.tradekraftcollective.microservice.specification.SearchCriteria;
import com.tradekraftcollective.microservice.validator.EventValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by brandonfeist on 9/28/17.
 */
@Slf4j
@Service
public class EventManagementService implements IEventManagementService {
    private static final String DESCENDING = "desc";

    @Inject
    IEventRepository eventRepository;

    @Inject
    EventValidator eventValidator;

    @Inject
    IArtistRepository artistRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Event> getEvents(int page, int pageSize, String sortField, String sortOrder, boolean officialEventsOnly, boolean pastEvents, boolean futureEvents) {
        log.info("Fetching events, page: {} pageSize: {} sortField: {} sortOrder: {} officialEventsOnly: {} pastEvents: {} futureEvents: {}", page, pageSize, sortField, sortOrder, officialEventsOnly, pastEvents, futureEvents);

        Sort.Direction order = Sort.Direction.ASC;
        if(sortOrder != null && sortOrder.equalsIgnoreCase(DESCENDING)) {
            order = Sort.Direction.DESC;
        }

        Specification<Event> result = getEventSpecs(officialEventsOnly, pastEvents, futureEvents);

        PageRequest request = new PageRequest(page, pageSize, order, sortField);

        Page<Event> eventPage;

        if(result != null) {
            eventPage = eventRepository.findAll(result, request);
        } else {
            eventPage = eventRepository.findAll(request);
        }

        return eventPage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Event getEvent(String eventSlug) {
        if(eventSlug == null) {
            log.error("Event slug cannot be null");
            throw new ServiceException(ErrorCode.INVALID_EVENT_SLUG, "event slug cannot be null.");
        }

        eventSlug = eventSlug.toLowerCase();

        Event event = eventRepository.findBySlug(eventSlug);

        return event;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Event createEvent(Event event) {
        log.info("Create event, name: {}", event.getName());

        eventValidator.validateEvent(event);

        event.setArtists(findAndSetEventArtists(event));

        event.setSlug(createEventSlug(event.getName()));

        Event returnEvent = eventRepository.save(event);

        log.info("***** SUCCESSFULLY CREATED EVENT WITH SLUG = {} *****", returnEvent.getSlug());

        return returnEvent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Event updateEvent(final Event eventUpdates, final String eventSlug) {
        Event event = eventRepository.findBySlug(eventSlug);
        if(event == null) {
            log.error("Event with slug [{}] does not exist", eventSlug);
            throw new ServiceException(ErrorCode.INVALID_EVENT_SLUG, "Event with slug [" + eventSlug + "] does not exist");
        }

        if(!eventUpdates.getName().equals(event.getName())) {
            event.setSlug(createEventSlug(eventUpdates.getName()));
        }

        event = eventUpdates(event, eventUpdates);

        eventValidator.validateEvent(event);

        eventRepository.save(event);

        log.info("***** SUCCESSFULLY UPDATED EVENT WITH SLUG = {} *****", event.getSlug());

        return event;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEvent(String eventSlug) {
        log.info("Delete event, slug: {}", eventSlug);

        eventValidator.validateEventSlug(eventSlug);

        eventRepository.deleteBySlug(eventSlug);

        log.info("***** SUCCESSFULLY DELETED EVENT WITH SLUG = {} *****", eventSlug);
    }

    private Event eventUpdates(Event originalEvent, final Event eventUpdates) {
        originalEvent.setName(eventUpdates.getName());
        originalEvent.setImage(eventUpdates.getImage());
        originalEvent.setDescription(eventUpdates.getDescription());
        originalEvent.setTicketLink(eventUpdates.getTicketLink());
        originalEvent.setEntryAge(eventUpdates.getEntryAge());
        originalEvent.setVenueName(eventUpdates.getVenueName());
        originalEvent.setAddress(eventUpdates.getAddress());
        originalEvent.setCity(eventUpdates.getCity());
        originalEvent.setState(eventUpdates.getState());
        originalEvent.setZip(eventUpdates.getZip());
        originalEvent.setCountry(eventUpdates.getCountry());
        originalEvent.setLatitude(eventUpdates.getLatitude());
        originalEvent.setLongitude(eventUpdates.getLongitude());
        originalEvent.setStartDateTime(eventUpdates.getStartDateTime());
        originalEvent.setEndDateTime(eventUpdates.getEndDateTime());
        originalEvent.setOfficialEvent(eventUpdates.isOfficialEvent());
        originalEvent.setArtists(findAndSetEventArtists(eventUpdates));

        return originalEvent;
    }

    private String createEventSlug(String eventName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(eventName);

        List<Event> duplicateEvents = eventRepository.findBySlugStartingWith(result);

        for(Event event : duplicateEvents) {
            if(event.getSlug().equals(result)) {
                return result.concat("-" + (duplicateEvents.size() + 1));
            }
        }

        return result;
    }

    private List<Artist> findAndSetEventArtists(Event event) {
        List<Artist> eventArtists = new ArrayList<>();

        if(event.getArtists() != null) {
            for (Artist artist : event.getArtists()) {
                Artist checkedArtist = artistRepository.findBySlug(artist.getSlug());
                if (checkedArtist == null) {
                    log.error("Event artist with slug [{}] does not exist.", artist.getSlug());
                    throw new ServiceException(ErrorCode.INVALID_ARTIST_SLUG, "artist with slug [" + artist.getSlug() + "] does not exist.");
                }

                eventArtists.add(checkedArtist);
            }
        }

        return eventArtists;
    }

    private Specification<Event> getEventSpecs(boolean officialEventsOnly, boolean pastEvents, boolean futureEvents) {
        Specification<Event> result = null;

        if(officialEventsOnly) {
            log.info("Getting specs for officialEvents [{}]", officialEventsOnly);

            EventSpecification officialEventSpec =
                    new EventSpecification(new SearchCriteria("officialEvent", ":", officialEventsOnly));

            result = Specifications.where(officialEventSpec);
        }

        if(pastEvents) {
            log.info("Getting specs for pastEvents [{}]", pastEvents);

            EventSpecification pastEventSpec =
                    new EventSpecification(new SearchCriteria("endDateTime", "<", new Timestamp(System.currentTimeMillis())));

            result = Specifications.where(result).and(pastEventSpec);
        }

        if(futureEvents) {
            log.info("Getting specs for futureEvents [{}]", futureEvents);

            EventSpecification futureEventSpec =
                    new EventSpecification(new SearchCriteria("endDateTime", ">=", new Timestamp(System.currentTimeMillis())));

            if(pastEvents) {
                result = Specifications.where(result).or(futureEventSpec);
            } else {
                result = Specifications.where(result).and(futureEventSpec);
            }
        }

        return result;
    }
}
