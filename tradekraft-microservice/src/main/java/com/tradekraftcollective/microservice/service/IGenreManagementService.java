package com.tradekraftcollective.microservice.service;

import com.github.fge.jsonpatch.JsonPatchOperation;
import com.tradekraftcollective.microservice.persistence.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * Created by brandonfeist on 9/26/17.
 */
public interface IGenreManagementService {
    Page<Genre> getGenres(int page, int pageSize, String sortField, String sortOrder);

    Genre getGenre(Long genreId);

    Genre createGenre(Genre genre, StopWatch stopWatch);

    Genre patchGenre(final List<JsonPatchOperation> patchOperations, final Long genreId, StopWatch stopWatch);

    void deleteGenre(Long genreId);
}
