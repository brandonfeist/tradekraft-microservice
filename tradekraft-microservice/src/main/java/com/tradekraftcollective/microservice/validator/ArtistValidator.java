package com.tradekraftcollective.microservice.validator;

import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by brandonfeist on 9/7/17.
 */
@Component
public class ArtistValidator {
    private static final Logger logger = LoggerFactory.getLogger(ArtistValidator.class);

    public void validateArtist(Artist artist) {
        validateArtistName(artist);
    }

    private void validateArtistName(Artist artist) {
        if(artist.getName() == null) {
            logger.error("Missing artist name.");
            throw new ServiceException(ErrorCode.INVALID_ARTIST_NAME, "artist name must be present.");
        }
    }
}
