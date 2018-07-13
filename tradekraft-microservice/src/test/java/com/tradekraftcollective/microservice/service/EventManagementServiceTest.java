package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Event;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
import com.tradekraftcollective.microservice.repository.IEventRepository;
import com.tradekraftcollective.microservice.service.impl.EventManagementService;
import com.tradekraftcollective.microservice.util.JsonMapper;
import com.tradekraftcollective.microservice.validator.EventValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class EventManagementServiceTest {

    @InjectMocks
    EventManagementService eventManagementService;

    @Mock
    EventValidator eventValidator;

    @Mock
    IEventRepository eventRepository;

    @Mock
    IArtistRepository artistRepository;

    private Event testEvent;

    @Test
    public void testCreateValidEventWithNoArtists() throws IOException {

        testEvent = JsonMapper.convertJsonToObject("/json/event/ValidEvent.json", Event.class);

        Mockito.doNothing().when(eventValidator).validateEvent(Mockito.any(Event.class));

        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(testEvent);

        Event returnTestEvent = eventManagementService.createEvent(testEvent);

        Assert.assertNotNull("Event should not be null", returnTestEvent);

        Assert.assertEquals("The incorrect event was returned", testEvent, returnTestEvent);

        Assert.assertEquals("The event should have no artists", 0, returnTestEvent.getArtists().size());

        Assert.assertEquals("The event slug is not correct", "test-event", returnTestEvent.getSlug());
    }

    @Test(expected = ServiceException.class)
    public void testCreateValidEventWithInvalidArtistSlug() throws IOException {
        testEvent = JsonMapper.convertJsonToObject("/json/event/ValidEvent.json", Event.class);

        Artist testArtist = JsonMapper.convertJsonToObject("/json/artist/ValidArtist.json", Artist.class);
        testArtist.setSlug("test-artist");

        List<Artist> artistList = new ArrayList<>();
        artistList.add(testArtist);
        testEvent.setArtists(artistList);

        Mockito.doNothing().when(eventValidator).validateEvent(Mockito.any(Event.class));

        Mockito.when(artistRepository.findBySlug(Mockito.anyString())).thenReturn(null);

        eventManagementService.createEvent(testEvent);
    }

    @Test
    public void testCreateValidEventWithArtists() throws IOException {
        testEvent = JsonMapper.convertJsonToObject("/json/event/ValidEvent.json", Event.class);

        Artist testArtist = JsonMapper.convertJsonToObject("/json/artist/ValidArtist.json", Artist.class);
        testArtist.setSlug("test-artist");

        List<Artist> artistList = new ArrayList<>();
        artistList.add(testArtist);
        testEvent.setArtists(artistList);

        Mockito.doNothing().when(eventValidator).validateEvent(Mockito.any(Event.class));

        Mockito.when(artistRepository.findBySlug(Mockito.anyString())).thenReturn(testArtist);

        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(testEvent);

        Event returnTestEvent = eventManagementService.createEvent(testEvent);

        Assert.assertNotNull("Event should not be null", returnTestEvent);

        Assert.assertEquals("The incorrect event was returned", testEvent, returnTestEvent);

        Assert.assertEquals("The event should have an artist", 1, returnTestEvent.getArtists().size());

        Assert.assertEquals("The event slug is not correct", "test-event", returnTestEvent.getSlug());
    }

    @Test
    public void testGetEventWithValidSlug() throws IOException {

        testEvent = JsonMapper.convertJsonToObject("/json/event/ValidEvent.json", Event.class);
        testEvent.setSlug("test-event");

        eventManagementService.getEvent("test-event");

        Mockito.when(eventRepository.findBySlug(Mockito.anyString())).thenReturn(testEvent);
    }

    @Test(expected = ServiceException.class)
    public void testGetEventWithNullSlug() {

        eventManagementService.getEvent(null);
    }
}
