package com.tradekraftcollective.microservice.validator;

import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Event;
import com.tradekraftcollective.microservice.repository.IEventRepository;
import com.tradekraftcollective.microservice.util.JsonMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class EventValidatorTest {

    @InjectMocks
    private EventValidator eventValidator;

    @Mock
    private IEventRepository eventRepository;

    private Event testEvent;

    @Before
    public void setup() throws IOException {
        testEvent = JsonMapper.convertJsonToObject("/json/event/ValidEvent.json", Event.class);
    }

    @Test
    public void testValidateEventSlug() {
        Mockito.when(eventRepository.findBySlug(Mockito.anyString())).thenReturn(new Event());

        testEvent = eventValidator.validateEventSlug("test-event");

        Assert.assertNotNull("An event object must be returned.", testEvent);
    }

    @Test(expected = ServiceException.class)
    public void testValidateInvalidEventSlug() {
        Mockito.when(eventRepository.findBySlug(Mockito.anyString())).thenReturn(null);

        eventValidator.validateEventSlug("test-event");
    }

    @Test
    public void testValidateValidEvent() {
        eventValidator.validateEvent(testEvent);
    }

    @Test(expected = ServiceException.class)
    public void testValidateNullEventAge() {
        testEvent.setEntryAge(null);

        eventValidator.validateEvent(testEvent);
    }

    @Test(expected = ServiceException.class)
    public void testValidateInvalidEventAge() {
        testEvent.setEntryAge("invalid_age");

        eventValidator.validateEvent(testEvent);
    }

    @Test(expected = ServiceException.class)
    public void testValidateNullStartDate() {
        testEvent.setStartDateTime(null);

        eventValidator.validateEvent(testEvent);
    }

    @Test(expected = ServiceException.class)
    public void testValidateNullEndDate() {
        testEvent.setEndDateTime(null);

        eventValidator.validateEvent(testEvent);
    }

    @Test(expected = ServiceException.class)
    public void testValidateInvalidEventDates() {
        Date tempStartDate = testEvent.getStartDateTime();

        testEvent.setStartDateTime(testEvent.getEndDateTime());
        testEvent.setEndDateTime(tempStartDate);

        eventValidator.validateEvent(testEvent);
    }

    @Test(expected = ServiceException.class)
    public void testValidateNullVenueName() {
        testEvent.setVenueName(null);

        eventValidator.validateEvent(testEvent);
    }

    @Test(expected = ServiceException.class)
    public void testValidateNullCity() {
        testEvent.setCity(null);

        eventValidator.validateEvent(testEvent);
    }

    @Test(expected = ServiceException.class)
    public void testValidateNullState() {
        testEvent.setState(null);

        eventValidator.validateEvent(testEvent);
    }

    @Test(expected = ServiceException.class)
    public void testValidateNullCountry() {
        testEvent.setCountry(null);

        eventValidator.validateEvent(testEvent);
    }
}
