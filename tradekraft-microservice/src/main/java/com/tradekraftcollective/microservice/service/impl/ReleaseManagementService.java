package com.tradekraftcollective.microservice.service.impl;

import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.repository.IReleaseRepository;
import com.tradekraftcollective.microservice.service.IReleaseManagementService;
import com.tradekraftcollective.microservice.service.ISongManagementService;
import com.tradekraftcollective.microservice.specification.ReleaseSpecification;
import com.tradekraftcollective.microservice.specification.SearchCriteria;
import com.tradekraftcollective.microservice.validator.ReleaseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Created by brandonfeist on 10/22/17.
 */
@Slf4j
@Service
public class ReleaseManagementService implements IReleaseManagementService {
    private static final String DESCENDING = "desc";

    @Inject
    private IReleaseRepository releaseRepository;

    @Inject
    private ISongManagementService songManagementService;

    @Inject
    private ReleaseValidator releaseValidator;

    @Override
    public Page<Release> getReleases(int page, int pageSize, String sortField, String sortOrder, String searchQuery, String genreQuery, String typeQuery) {
        log.info("Fetching releases, page: {} pageSize: {} sortField: {} sortOrder: {}", page, pageSize, sortField, sortOrder);

        Sort.Direction order = Sort.Direction.ASC;
        if(sortOrder != null && sortOrder.equalsIgnoreCase(DESCENDING)) {
            order = Sort.Direction.DESC;
        }

        PageRequest request = new PageRequest(page, pageSize, order, sortField);

        Specification<Release> result = getReleaseSpecs(searchQuery, genreQuery, typeQuery);

        Page<Release> releasePage;

        if(result != null) {
            releasePage = releaseRepository.findAll(result, request);
        }

        releasePage = releaseRepository.findAll(request);

        return releasePage;
    }

    @Override
    public Release getRelease(String releaseSlug) {
        if(releaseSlug == null) {
            log.error("Release slug cannot be null");
            throw new ServiceException(ErrorCode.INVALID_RELEASE_SLUG, "release slug cannot be null.");
        }

        releaseSlug = releaseSlug.toLowerCase();

        Release release = releaseRepository.findBySlug(releaseSlug);

        Optional.ofNullable(release)
                .ifPresent(opRelease -> release.setSongs(songManagementService.songLinkAuthorization(release)));

        return release;
    }

    @Override
    public Release createRelease(Release release) {
        log.info("Create release, name: {}", release.getName());

        releaseValidator.validateRelease(release);

        release.setSlug(createReleaseSlug(release.getName()));

        Release returnRelease = releaseRepository.save(release);

        log.info("***** SUCCESSFULLY CREATED RELEASE WITH SLUG = {} *****", returnRelease.getSlug());

        return returnRelease;
    }

    @Override
    public Release updateRelease(final Release releaseUpdates, final String releaseSlug) {

        Release release = releaseRepository.findBySlug(releaseSlug);
        if(release == null) {
            log.error("Release with slug [{}] does not exist", releaseSlug);
            throw new ServiceException(ErrorCode.INVALID_RELEASE_SLUG, "Release with slug [" + releaseSlug + "] does not exist");
        }

        if(!releaseUpdates.getName().equals(release.getName())) {
            release.setSlug(createReleaseSlug(releaseUpdates.getName()));
        }

        release = releaseUpdates(release, releaseUpdates);

        releaseValidator.validateRelease(release);

        releaseRepository.save(release);

        log.info("***** SUCCESSFULLY UPDATED RELEASE WITH SLUG = {} *****", release.getSlug());

        return release;
    }

    @Override
    public void deleteRelease(String releaseSlug) {
        log.info("Delete release, slug: {}", releaseSlug);

        Release release = releaseRepository.findBySlug(releaseSlug);

        if(release == null) {
            log.error("Release with slug [{}] does not exist", releaseSlug);
            throw new ServiceException(ErrorCode.INVALID_RELEASE_SLUG, "Release with slug [" + releaseSlug + "] does not exist");
        }

        for(Song song : release.getSongs()) {
            songManagementService.deleteSong(song);
        }

        releaseRepository.deleteBySlug(releaseSlug);

        log.info("***** SUCCESSFULLY DELETED RELEASE WITH SLUG = {} *****", releaseSlug);
    }

    private Release releaseUpdates(Release originalRelease, final Release releaseUpdates) {
        originalRelease.setName(releaseUpdates.getName());
        originalRelease.setImage(releaseUpdates.getImage());
        originalRelease.setDescription(releaseUpdates.getDescription());
        originalRelease.setReleaseType(releaseUpdates.getReleaseType());
        originalRelease.setReleaseDate(releaseUpdates.getReleaseDate());
        originalRelease.setSoundcloud(releaseUpdates.getSoundcloud());
        originalRelease.setSpotify(releaseUpdates.getSpotify());
        originalRelease.setItunes(releaseUpdates.getItunes());
        originalRelease.setAppleMusic(releaseUpdates.getAppleMusic());
        originalRelease.setGooglePlay(releaseUpdates.getGooglePlay());
        originalRelease.setAmazon(releaseUpdates.getAmazon());
        originalRelease.setFreeRelease(releaseUpdates.isFreeRelease());

        return originalRelease;
    }

    private String createReleaseSlug(String releaseName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(releaseName);

        List<Release> duplicateReleases = releaseRepository.findBySlugStartingWith(result);

        for(Release release : duplicateReleases) {
            if(release.getSlug().equals(result)) {
                return result.concat("-" + (duplicateReleases.size() + 1));
            }
        }

        return result;
    }

    private Specification<Release> getReleaseSpecs(String searchQuery, String genreQuery, String typeQuery) {
        Specification<Release> result = null;

        if(searchQuery != null) {
            log.info("Getting specs for searchQuery [{}]", searchQuery);

            ReleaseSpecification spec1 =
                    new ReleaseSpecification(new SearchCriteria("name", ":", searchQuery));
            ReleaseSpecification spec2 =
                    new ReleaseSpecification(new SearchCriteria("songs", "in",
                            new SearchCriteria("artists", "in", "name," + searchQuery)));

            result = Specifications.where(spec1).or(spec2);
        }

        if(genreQuery != null) {
            log.info("Getting specs for genreQuery [{}]", genreQuery);

            ReleaseSpecification genreSepc =
                    new ReleaseSpecification(new SearchCriteria("songs", "in",
                            new SearchCriteria("genre", "in", "name," + genreQuery)));

            result = Specifications.where(result).and(genreSepc);
        }

        if(typeQuery != null) {
            log.info("Getting specs for typQuery [{}]", typeQuery);

            ReleaseSpecification typeSpec =
                    new ReleaseSpecification(new SearchCriteria("releaseType", ":", typeQuery));

            result = Specifications.where(result).and(typeSpec);
        }

        return result;
    }
}
