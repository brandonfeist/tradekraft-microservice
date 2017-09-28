package com.tradekraftcollective.microservice.service.impl;

import com.github.fge.jsonpatch.JsonPatchOperation;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Genre;
import com.tradekraftcollective.microservice.repository.IGenreRepository;
import com.tradekraftcollective.microservice.service.IGenreManagementService;
import com.tradekraftcollective.microservice.service.IGenrePatchService;
import com.tradekraftcollective.microservice.utilities.ColorUtility;
import com.tradekraftcollective.microservice.validator.GenreValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by brandonfeist on 9/26/17.
 */
@Service
public class GenreManagementService implements IGenreManagementService {
    private static final Logger logger = LoggerFactory.getLogger(GenreManagementService.class);

    private static final String DESCENDING = "desc";

    @Inject
    IGenreRepository genreRepository;

    @Inject
    IGenrePatchService genrePatchService;

    @Inject
    GenreValidator genreValidator;

    @Inject
    ColorUtility colorUtility;

    @Override
    public Page<Genre> getGenres(int page, int pageSize, String sortField, String sortOrder) {
        logger.info("Fetching genres, page: {} pageSize {} sortField {} sortOrder {}", page, pageSize, sortField, sortOrder);

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
            logger.error("Genre id cannot be null");
            throw new ServiceException(ErrorCode.INVALID_GENRE_ID, "genre id cannot be null.");
        }

        Genre genre = genreRepository.findOne(genreId);

        return genre;
    }

    @Override
    public Genre createGenre(Genre genre, StopWatch stopWatch) {
        logger.info("Create genre, name: {}", genre.getName());

        stopWatch.start("validateGenre");
        genreValidator.validateGenre(genre);
        stopWatch.stop();

        stopWatch.start("saveGenre");

        genre.setHue(colorUtility.rgbToHue(colorUtility.hexToRgb(genre.getColor())));
        Genre returnGenre = genreRepository.save(genre);

        stopWatch.stop();
        logger.info("***** SUCCESSFULLY CREATED GENRE WITH ID = {} *****", genre.getId());

        return returnGenre;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Genre patchGenre(List<JsonPatchOperation> patchOperations, Long genreId, StopWatch stopWatch) {
        stopWatch.start("getOldGenre");
        Genre oldGenre = genreRepository.findOne(genreId);
        if(oldGenre == null) {
            logger.error("Genre with id [{}] does not exist", genreId);
            throw new ServiceException(ErrorCode.INVALID_GENRE_ID, "Genre with id [" + genreId + "] does not exist");
        }
        stopWatch.stop();

        stopWatch.start("patchArtist");

        Genre patchedGenre = genrePatchService.patchGenre(patchOperations, oldGenre);

        genreValidator.validateGenre(patchedGenre);

        genreRepository.save(patchedGenre);

        stopWatch.stop();

        logger.info("***** SUCCESSFULLY PATCHED GENRE WITH ID = {} *****", patchedGenre.getId());

        return patchedGenre;
    }

    @Override
    public void deleteGenre(Long genreId) {
        logger.info("Delete genre, id: {}", genreId);

        genreRepository.delete(genreId);

        logger.info("***** SUCCESSFULLY DELETED GENRE WITH ID = {} *****", genreId);
    }
}
