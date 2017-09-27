package com.tradekraftcollective.microservice.validator;

import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Genre;
import com.tradekraftcollective.microservice.repository.IGenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.regex.Pattern;

/**
 * Created by brandonfeist on 9/26/17.
 */
@Component
public class GenreValidator {
    private static final Logger logger = LoggerFactory.getLogger(GenreValidator.class);

    private final String GENRE_COLOR_REGEX = "^#(?:[0-9a-fA-F]{3}){1,2}$";

    @Inject
    IGenreRepository genreRepository;

    public void validateGenre(Genre genre) {
        validateGenreName(genre);
        validateGenreColor(genre);
    }

    private void validateGenreName(Genre genre) {
        if(genre.getName() == null || genre.getName().isEmpty()) {
            logger.error("Missing genre name.");
            throw new ServiceException(ErrorCode.INVALID_GENRE_NAME, "genre name must be present.");
        }

        if(genreRepository.findByName(genre.getName()) != null) {
            logger.error("Genre with name: {} already exists", genre.getName());
            throw new ServiceException(ErrorCode.INVALID_GENRE_NAME, "genre with name [" + genre.getName() + "] already exists.");
        }
    }

    private void validateGenreColor(Genre genre) {
        if(genre.getColor() == null || genre.getColor().isEmpty()) {
            logger.error("Missing genre color.");
            throw new ServiceException(ErrorCode.INVALID_GENRE_COLOR, "genre color must be present.");
        }

        if(!Pattern.compile(GENRE_COLOR_REGEX).matcher(genre.getColor()).find()) {
            logger.error("Invalid genre color format, must be a hex color");
            throw new ServiceException(ErrorCode.INVALID_GENRE_COLOR, "must be a hex color format.");
        }

        if(genreRepository.findByColor(genre.getColor()) != null) {
            logger.error("Genre with color: {} already exists", genre.getColor());
            throw new ServiceException(ErrorCode.INVALID_GENRE_COLOR, "genre with color [" + genre.getColor() + "] already exists.");
        }
    }
}
