package com.tradekraftcollective.microservice.validator;

import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Event;
import com.tradekraftcollective.microservice.repository.IEventRepository;
import com.tradekraftcollective.microservice.utilities.ImageValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by brandonfeist on 9/28/17.
 */
@Slf4j
@Component
public class EventValidator {
    private static final String[] VALID_EVENT_AGES = {
            "all", "16+", "18+", "21+"
    };

    @Inject
    IEventRepository eventRepository;

    @Inject
    ImageValidationUtil imageValidationUtil;

    public void validateEvent(Event event, MultipartFile image) {
        validateEventName(event);
        validateEventEntryAge(event);
        validateEventStartAndEndDate(event);
        validateEventVenueName(event);
        validateEventAddress(event);
        validateEventImage(image);
    }

    public void validateEvent(Event event) {
        validateEventName(event);
        validateEventEntryAge(event);
        validateEventStartAndEndDate(event);
        validateEventVenueName(event);
        validateEventAddress(event);
    }

    public void validateEventSlug(String eventSlug) {
        if(eventRepository.findBySlug(eventSlug) == null) {
            log.error("Event with slug [{}] does not exist", eventSlug);
            throw new ServiceException(ErrorCode.INVALID_EVENT_SLUG, "Event with slug [" + eventSlug + "] does not exist");
        }
    }

    private void validateEventName(Event event) {
        if(event.getName() == null || event.getName().isEmpty()) {
            log.error("Missing event name.");
            throw new ServiceException(ErrorCode.INVALID_EVENT_NAME, "event name must be present.");
        }
    }

    private void validateEventEntryAge(Event event) {
        if(event.getEntryAge() == null || event.getEntryAge().isEmpty()) {
            log.error("Missing event entry age.");
            throw new ServiceException(ErrorCode.INVALID_EVENT_ENTRY_AGE, "event entry age must be present.");
        }

        boolean validEventAge = false;
        for(String age : VALID_EVENT_AGES) {
            if(event.getEntryAge().toLowerCase().equals(age)) {
                validEventAge = true;
            }
        }

        if(!validEventAge) {
            log.error("Invalid event entry age. Valid ages are [all, 16+, 18+, 21+]");
            throw new ServiceException(ErrorCode.INVALID_EVENT_ENTRY_AGE, "valid event ages are [all, 16+, 18+, 21+].");
        }
    }

    private void validateEventStartAndEndDate(Event event) {
        if(event.getStartDateTime() == null) {
            log.error("Missing event start date.");
            throw new ServiceException(ErrorCode.INVALID_EVENT_START_DATE, "event start date must be present.");
        }

        if(event.getEndDateTime() == null) {
            log.error("Missing event end date.");
            throw new ServiceException(ErrorCode.INVALID_EVENT_END_DATE, "event end date must be present.");
        }

        if(event.getEndDateTime().before(event.getStartDateTime())) {
            log.error("Event start date must be before the end date.");
            throw new ServiceException(ErrorCode.INVALID_DATE_RANGE, "event start date must be before the end date.");
        }
    }

    private void validateEventVenueName(Event event) {
        if(event.getVenueName() == null || event.getVenueName().isEmpty()) {
            log.error("Missing event venue name.");
            throw new ServiceException(ErrorCode.INVALID_EVENT_VENUE_NAME, "event venue name must be present.");
        }
    }

    private void validateEventAddress(Event event) {
        if(event.getCity() == null || event.getCity().isEmpty()) {
            log.error("Missing event city.");
            throw new ServiceException(ErrorCode.INVALID_EVENT_ADDRESS, "event city must be present.");
        }

        if(event.getState() == null || event.getState().isEmpty()) {
            log.error("Missing event state.");
            throw new ServiceException(ErrorCode.INVALID_EVENT_ADDRESS, "event state must be present.");
        }

        if(event.getCountry() == null || event.getCountry().isEmpty()) {
            log.error("Missing event country.");
            throw new ServiceException(ErrorCode.INVALID_EVENT_ADDRESS, "event country must be present.");
        }
    }

    private void validateEventImage(MultipartFile image) {
        try {
            imageValidationUtil.validateImageExtension(image);
            imageValidationUtil.minimumImageSize(1000, 592, ImageIO.read(image.getInputStream()));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
