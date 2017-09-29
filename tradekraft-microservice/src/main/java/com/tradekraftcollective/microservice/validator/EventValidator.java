package com.tradekraftcollective.microservice.validator;

import com.tradekraftcollective.microservice.persistence.entity.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by brandonfeist on 9/28/17.
 */
@Component
public class EventValidator {
    private static final Logger logger = LoggerFactory.getLogger(EventValidator.class);

    public void validateEvent(Event event, MultipartFile image) {

    }
}
