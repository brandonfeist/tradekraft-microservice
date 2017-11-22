package com.tradekraftcollective.microservice.validator;

import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Genre;
import com.tradekraftcollective.microservice.repository.IGenreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.regex.Pattern;

/**
 * Created by brandonfeist on 9/26/17.
 */
@Slf4j
@Component
public class GenreValidator {
    private final String GENRE_COLOR_REGEX = "^#(?:[0-9a-fA-F]{3}){1,2}$";

    @Inject
    IGenreRepository genreRepository;

    public void validateGenre(Genre genre) {
        validateGenreColor(genre);
    }

//    private void validateGenreName(Genre genre) {
//        if(genre.getName() == null || genre.getName().isEmpty()) {
//            log.error("Missing genre name.");
//            throw new ServiceException(ErrorCode.INVALID_GENRE_NAME, "genre name must be present.");
//        }
//
//        Genre validationGenre = genreRepository.findByName(genre.getName());
//        if(validationGenre != null && !validationGenre.getId().equals(genre.getId())) {
//            log.error("Genre with name: {} already exists", genre.getName());
//            throw new ServiceException(ErrorCode.INVALID_GENRE_NAME, "genre with name [" + genre.getName() + "] already exists.");
//        }
//    }

    private void validateGenreColor(Genre genre) {
        if(genre.getColor() == null || genre.getColor().isEmpty()) {
            log.error("Missing genre color.");
            throw new ServiceException(ErrorCode.INVALID_GENRE_COLOR, "genre color must be present.");
        }

        if(!Pattern.compile(GENRE_COLOR_REGEX).matcher(genre.getColor()).find()) {
            log.error("Invalid genre color format, must be a hex color");
            throw new ServiceException(ErrorCode.INVALID_GENRE_COLOR, "must be a hex color format.");
        }

        Genre validationGenre = genreRepository.findByColor(genre.getColor());
        if(validationGenre != null && !validationGenre.getId().equals(genre.getId())) {
            log.error("Genre with color: {} already exists", genre.getColor());
            throw new ServiceException(ErrorCode.INVALID_GENRE_COLOR, "genre with color [" + genre.getColor() + "] already exists.");
        }
    }
}
