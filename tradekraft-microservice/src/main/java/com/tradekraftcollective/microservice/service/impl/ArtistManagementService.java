package com.tradekraftcollective.microservice.service.impl;

import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.service.IArtistManagementService;
import com.tradekraftcollective.microservice.validator.ArtistValidator;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;

/**
 * Created by brandonfeist on 9/5/17.
 */
@Service
public class ArtistManagementService implements IArtistManagementService {
    private static final Logger logger = LoggerFactory.getLogger(ArtistManagementService.class);

    private static final String DESCENDING = "desc";

    @Inject
    IArtistRepository artistRepository;

    @Inject
    ArtistValidator artistValidator;

    @Inject
    AmazonS3Service amazonS3Service;

    @Override
    public Page<Artist> getArtists(int page, int pageSize, String sortField, String sortOrder) {
        logger.info("Fetching artists, page: {} pageSize {} sortField {} sortOrder {}", page, pageSize, sortField, sortOrder);

        Sort.Direction order = Sort.Direction.ASC;
        if(sortOrder != null && sortOrder.equalsIgnoreCase(DESCENDING)) {
            order = Sort.Direction.DESC;
        }

        PageRequest request = new PageRequest(page, pageSize, order, sortField);

        return artistRepository.findAll(request);
    }

    @Override
    public Artist getArtist(String artistSlug) {
        if(artistSlug == null) {
            throw new ServiceException(ErrorCode.INVALID_ARTIST_SLUG, "artist slug cannot be null.");
        }

        artistSlug = artistSlug.toLowerCase();

        Artist artist = artistRepository.findBySlug(artistSlug);

        return artist;
    }

    @Override
    public Artist createArtist(Artist artist, MultipartFile imageFile, StopWatch stopWatch) {
        logger.info("Create artist, name: {}", artist.getName());

        amazonS3Service.upload(imageFile, "test/");

        stopWatch.start("validateArtist");
        artistValidator.validateArtist(artist);
        stopWatch.stop();

        stopWatch.start("saveArtist");
        artist.setSlug(createArtistSlug(artist.getName()));
        Artist returnArtist = artistRepository.save(artist);
        stopWatch.stop();
        logger.info("***** SUCCESSFULLY CREATED ARTIST WITH SLUG = {}", returnArtist.getSlug());

        return returnArtist;
    }

    private String createArtistSlug(String artistName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(artistName);

        int duplicateSlugs = artistRepository.findBySlugStartingWith(result).size();
        return duplicateSlugs > 0 ? result.concat("-" + (duplicateSlugs + 1)) : result;
    }
}
