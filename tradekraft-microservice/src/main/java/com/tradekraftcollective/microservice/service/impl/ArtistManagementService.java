package com.tradekraftcollective.microservice.service.impl;

import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Year;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
import com.tradekraftcollective.microservice.repository.IYearRepository;
import com.tradekraftcollective.microservice.service.IArtistManagementService;
import com.tradekraftcollective.microservice.service.IYearManagementService;
import com.tradekraftcollective.microservice.validator.ArtistValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by brandonfeist on 9/5/17.
 */
@Slf4j
@Service
public class ArtistManagementService implements IArtistManagementService {

    private static final String DESCENDING = "desc";

    @Inject
    IArtistRepository artistRepository;

    @Autowired
    IYearRepository yearRepository;

    @Inject
    ArtistValidator artistValidator;

    @Autowired
    IYearManagementService yearManagementService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Artist> getArtists(int page, int pageSize, String sortField, String sortOrder, String artistQuery, String yearQuery) {
        log.info("Fetching artists, page: {} pageSize: {} sortField: {} sortOrder: {}", page, pageSize, sortField, sortOrder);

        Sort.Direction order = Sort.Direction.ASC;
        if(sortOrder != null && sortOrder.equalsIgnoreCase(DESCENDING)) {
            order = Sort.Direction.DESC;
        }

        PageRequest request = new PageRequest(page, pageSize, order, sortField);

        Page<Artist> artistPage;

        Year year = yearRepository.findByYear(yearQuery);
        if(artistQuery != null && year != null) {
            artistPage = artistRepository.findByNameContainingIgnoreCaseAndYearsActive(artistQuery, year, request);
        } else if(artistQuery != null && year == null) {
            artistPage = artistRepository.findByNameContainingIgnoreCase(artistQuery, request);
        } else if(artistQuery == null && year != null) {
            artistPage = artistRepository.findByYearsActive(year, request);
        } else {
            artistPage = artistRepository.findAll(request);
        }

        artistPage.forEach(Artist::convertSongsToReleases);

        return artistPage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Artist getArtist(String artistSlug) {
        if(artistSlug == null) {
            log.error("Artist slug cannot be null");
            throw new ServiceException(ErrorCode.INVALID_ARTIST_SLUG, "artist slug cannot be null.");
        }

        artistSlug = artistSlug.toLowerCase();

        Artist artist = artistRepository.findBySlug(artistSlug);

        artist.convertSongsToReleases();

        return artist;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Artist createArtist(Artist artist) {
        log.info("Create artist, name: {}", artist.getName());

        artistValidator.validateArtist(artist);

        artist.setYearsActive(retrieveActiveYears(artist));

        artist.setSlug(createArtistSlug(artist.getName()));

        Artist returnArtist = artistRepository.save(artist);

        log.info("***** SUCCESSFULLY CREATED ARTIST WITH SLUG = {} *****", returnArtist.getSlug());

        return returnArtist;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Artist updateArtist(Artist artistUpdates, String artistSlug) {

        Artist artist = artistRepository.findBySlug(artistSlug);
        if(artist == null) {
            log.error("Artist with slug [{}] does not exist", artistSlug);
            throw new ServiceException(ErrorCode.INVALID_ARTIST_SLUG, "Artist with slug [" + artistSlug + "] does not exist");
        }

        if(!artistUpdates.getName().equals(artist.getName())) {
            artist.setSlug(createArtistSlug(artistUpdates.getName()));
        }

        artist = artistUpdates(artist, artistUpdates);

        artistValidator.validateArtist(artist);

        artistRepository.save(artist);

        log.info("***** SUCCESSFULLY UPDATED ARTIST WITH SLUG = {} *****", artist.getSlug());

        return artist;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteArtist(String artistSlug) {
        log.info("Delete artist, slug: {}", artistSlug);

        artistValidator.validateArtistSlug(artistSlug);

        artistRepository.deleteBySlug(artistSlug);

        log.info("***** SUCCESSFULLY DELETED ARTIST WITH SLUG = {} *****", artistSlug);
    }

    private String createArtistSlug(String artistName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(artistName);

        List<Artist> duplicateArtists = artistRepository.findBySlugStartingWith(result);

        for(Artist artist : duplicateArtists) {
            if(artist.getSlug().equals(result)) {
                return result.concat("-" + (duplicateArtists.size() + 1));
            }
        }

        return result;
    }

    private List<Year> retrieveActiveYears(Artist artist) {
        List<Year> artistYears = artist.getYearsActive();
        List<Year> years = new ArrayList<>();

        if(artistYears == null || artistYears.isEmpty()) {
            return null;
        }

        for(int yearIndex = 0; yearIndex < artistYears.size(); yearIndex++) {
            Year tmpYear = yearRepository.findByYear(artistYears.get(yearIndex).getYear().toString());

            if(tmpYear != null) {
                years.add(tmpYear);
            } else {
                Year newYear = yearManagementService.createYear(artistYears.get(yearIndex));
                years.add(newYear);
            }
        }

        return years;
    }

    private Artist artistUpdates(Artist originalArtist, final Artist artistUpdates) {
        originalArtist.setName(artistUpdates.getName());
        originalArtist.setDescription(artistUpdates.getDescription());
        originalArtist.setImage(artistUpdates.getImage());
        originalArtist.setSoundcloud(artistUpdates.getSoundcloud());
        originalArtist.setFacebook(artistUpdates.getFacebook());
        originalArtist.setTwitter(artistUpdates.getTwitter());
        originalArtist.setInstagram(artistUpdates.getInstagram());
        originalArtist.setSpotify(artistUpdates.getSpotify());
        originalArtist.setYearsActive(retrieveActiveYears(artistUpdates));

        return originalArtist;
    }
}
