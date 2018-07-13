package com.tradekraftcollective.microservice.service.impl;

import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Genre;
import com.tradekraftcollective.microservice.repository.IGenreRepository;
import com.tradekraftcollective.microservice.service.IGenreManagementService;
import com.tradekraftcollective.microservice.utilities.ColorUtility;
import com.tradekraftcollective.microservice.validator.GenreValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by brandonfeist on 9/26/17.
 */
@Slf4j
@Service
public class GenreManagementService implements IGenreManagementService {
    private static final String DESCENDING = "desc";

    @Inject
    IGenreRepository genreRepository;

    @Inject
    GenreValidator genreValidator;

    @Inject
    ColorUtility colorUtility;

    @Override
    public Page<Genre> getGenres(int page, int pageSize, String sortField, String sortOrder) {
        log.info("Fetching genres, page: {} pageSize {} sortField {} sortOrder {}", page, pageSize, sortField, sortOrder);

        Sort.Direction order = Sort.Direction.ASC;
        if(sortOrder != null && sortOrder.equalsIgnoreCase(DESCENDING)) {
            order = Sort.Direction.DESC;
        }

        PageRequest request = new PageRequest(page, pageSize, order, sortField);

        return genreRepository.findAll(request);
    }

    @Override
    public Genre getGenre(Long genreId) {
        if(genreId == null) {
            log.error("Genre id cannot be null");
            throw new ServiceException(ErrorCode.INVALID_GENRE_ID, "genre id cannot be null.");
        }

        Genre genre = genreRepository.findOne(genreId);

        return genre;
    }

    @Override
    public Genre createGenre(Genre genre) {
        log.info("Create genre, name: {}", genre.getName());

        genreValidator.validateGenre(genre);

        genre.setHue(colorUtility.rgbToHue(colorUtility.hexToRgb(genre.getColor())));
        Genre returnGenre = genreRepository.save(genre);

        log.info("***** SUCCESSFULLY CREATED GENRE WITH ID = {} *****", genre.getId());

        return returnGenre;
    }

    @Override
    public Genre updateGenre(final Genre genreUpdates, final Long genreId) {
        Genre genre = genreRepository.findOne(genreId);
        if(genre == null) {
            log.error("Genre with id [{}] does not exist", genreId);
            throw new ServiceException(ErrorCode.INVALID_GENRE_ID, "Genre with id [" + genreId + "] does not exist");
        }

        genre = genreUpdates(genre, genreUpdates);

        genreValidator.validateGenre(genre);

        genreRepository.save(genre);

        log.info("***** SUCCESSFULLY UPDATED GENRE WITH ID = {} *****", genre.getId());

        return genre;
    }

    @Override
    public void deleteGenre(Long genreId) {
        log.info("Delete genre, id: {}", genreId);

        genreRepository.delete(genreId);

        log.info("***** SUCCESSFULLY DELETED GENRE WITH ID = {} *****", genreId);
    }

    private Genre genreUpdates(Genre originalGenre, final Genre genreUpdates) {
        originalGenre.setName(genreUpdates.getName());
        originalGenre.setColor(genreUpdates.getColor());
        originalGenre.setHue(colorUtility.rgbToHue(colorUtility.hexToRgb(genreUpdates.getColor())));

        return originalGenre;
    }
}
