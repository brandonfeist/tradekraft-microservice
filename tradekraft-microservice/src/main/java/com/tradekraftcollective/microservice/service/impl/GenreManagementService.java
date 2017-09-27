package com.tradekraftcollective.microservice.service.impl;

import com.github.fge.jsonpatch.JsonPatchOperation;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Genre;
import com.tradekraftcollective.microservice.repository.IGenreRepository;
import com.tradekraftcollective.microservice.service.IGenreManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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
        return null;
    }

    @Override
    public Genre patchGenre(List<JsonPatchOperation> patchOperations, Long genreId, StopWatch stopWatch) {
        return null;
    }

    @Override
    public void deleteGenre(Long genreId) {

    }
}
