package com.tradekraftcollective.microservice.service.impl;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.constants.PatchOperationConstants;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Year;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
import com.tradekraftcollective.microservice.repository.IYearRepository;
import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.service.IArtistManagementService;
import com.tradekraftcollective.microservice.service.IArtistPatchService;
import com.tradekraftcollective.microservice.service.IYearManagementService;
import com.tradekraftcollective.microservice.utilities.ImageProcessingUtil;
import com.tradekraftcollective.microservice.validator.ArtistValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brandonfeist on 9/5/17.
 */
@Service
public class ArtistManagementService implements IArtistManagementService {
    private static final Logger logger = LoggerFactory.getLogger(ArtistManagementService.class);

    private static final String DESCENDING = "desc";
    private static final String ARTIST_IMAGE_PATH = "uploads/artist/image/";

    @Inject
    IArtistRepository artistRepository;

    @Autowired
    IYearRepository yearRepository;

    @Inject
    ArtistValidator artistValidator;

    @Inject
    IArtistPatchService artistPatchService;

    @Autowired
    IYearManagementService yearManagementService;

    @Inject
    ImageProcessingUtil imageProcessingUtil;

    @Inject
    AmazonS3Service amazonS3Service;

    @Inject
    ObjectMapper objectMapper;

    @Override
    public Page<Artist> getArtists(int page, int pageSize, String sortField, String sortOrder, String artistQuery, String yearQuery) {
        logger.info("Fetching artists, page: {} pageSize: {} sortField: {} sortOrder: {}", page, pageSize, sortField, sortOrder);

        Sort.Direction order = Sort.Direction.ASC;
        if(sortOrder != null && sortOrder.equalsIgnoreCase(DESCENDING)) {
            order = Sort.Direction.DESC;
        }

        PageRequest request = new PageRequest(page, pageSize, order, sortField);

        Year year = yearRepository.findByYear(yearQuery);
        if(artistQuery != null && year != null) {
            return artistRepository.findByNameContainingIgnoreCaseAndYearsActive(artistQuery, year, request);
        } else if(artistQuery != null && year == null) {
            return artistRepository.findByNameContainingIgnoreCase(artistQuery, request);
        } else if(artistQuery == null && year != null) {
            return artistRepository.findByYearsActive(year, request);
        }

        return artistRepository.findAll(request);
    }

    @Override
    public Artist getArtist(String artistSlug) {
        if(artistSlug == null) {
            logger.error("Artist slug cannot be null");
            throw new ServiceException(ErrorCode.INVALID_ARTIST_SLUG, "artist slug cannot be null.");
        }

        artistSlug = artistSlug.toLowerCase();

        Artist artist = artistRepository.findBySlug(artistSlug);

        return artist;
    }

    @Override
    public Artist createArtist(Artist artist) {
        logger.info("Create artist, name: {}", artist.getName());

        artistValidator.validateArtist(artist);

        artist.setYearsActive(retrieveActiveYears(artist));

        artist.setSlug(createArtistSlug(artist.getName()));

        Artist returnArtist = artistRepository.save(artist);

        logger.info("***** SUCCESSFULLY CREATED ARTIST WITH SLUG = {} *****", returnArtist.getSlug());

        return returnArtist;
    }

    @Override
    public Artist uploadArtistImage(String artistSlug, MultipartFile imageFile) {
        logger.info("Uploading image for artist slug [{}]", artistSlug);

        Artist returnArtist = artistRepository.findBySlug(artistSlug);

        deleteAllArtistsImages(artistSlug);

        returnArtist.setImage(imageProcessingUtil.processImageAndUpload(returnArtist.getImageSizes(),
                (returnArtist.ARTIST_IMAGE_UPLOAD_PATH + returnArtist.getSlug() + "/"),
                imageFile, 1.0));

        returnArtist = artistRepository.save(returnArtist);

        logger.info("***** SUCCESSFULLY UPLOADED IMAGE FOR ARTIST = {} *****", returnArtist.getSlug());

        return returnArtist;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class, IOException.class})
    public Artist patchArtist(List<JsonPatchOperation> patchOperations, String artistSlug) {

        Artist oldArtist = artistRepository.findBySlug(artistSlug);
        if(oldArtist == null) {
            logger.error("Artist with slug [{}] does not exist", artistSlug);
            throw new ServiceException(ErrorCode.INVALID_ARTIST_SLUG, "Artist with slug [" + artistSlug + "] does not exist");
        }

//        boolean uploadingNewImage = (imageFile != null);
//        if(uploadingNewImage) {
//            JsonPatchOperation imageOperation;
//
//            try {
//                if (oldArtist.getImage() != null) {
//                    logger.info("New image found, creating imagePatch operation: [{}], overwriting image: {}",
//                            PatchOperationConstants.REPLACE, oldArtist.getImageName());
//
//                    String jsonImageReplacePatch = "{\"op\": \"" + PatchOperationConstants.REPLACE + "\", " +
//                            "\"path\": \"" + ArtistPatchService.ARTIST_IMAGE_PATH + "\", " +
//                            "\"value\": \"" + imageFile.getOriginalFilename() + "\"}";
//
//                    imageOperation = objectMapper.readValue(jsonImageReplacePatch, JsonPatchOperation.class);
//
//                    patchOperations.add(imageOperation);
//                } else {
//                    logger.info("New image found, creating imagePatch operation: {}", PatchOperationConstants.ADD);
//
//                    String jsonImageReplacePatch = "{\"op\": \"" + PatchOperationConstants.ADD + "\", " +
//                            "\"path\": \"" + ArtistPatchService.ARTIST_IMAGE_PATH + "\", " +
//                            "\"value\": \"" + imageFile.getOriginalFilename() + "\"}";
//
//                    imageOperation = objectMapper.readValue(jsonImageReplacePatch, JsonPatchOperation.class);
//
//                    patchOperations.add(imageOperation);
//                }
//            } catch(IOException e) {
//                e.printStackTrace();
//            }
//        }

        Artist patchedArtist = artistPatchService.patchArtist(patchOperations, oldArtist);

        if(!oldArtist.getName().equals(patchedArtist.getName())) {
            patchedArtist.setSlug(createArtistSlug(patchedArtist.getName()));

            ObjectListing directoryImages = amazonS3Service.getDirectoryContent(oldArtist.getAWSKey(), null);
            for (S3ObjectSummary summary: directoryImages.getObjectSummaries()) {
                String[] splitKey = summary.getKey().split("/");

                amazonS3Service.moveObject(summary.getKey(), patchedArtist.getAWSKey() + splitKey[splitKey.length - 1]);
            }
        }

        if(!oldArtist.getYearsActive().equals(patchedArtist.getYearsActive())) {
            patchedArtist.setYearsActive(yearManagementService.getExistingYears(patchedArtist.getYearsActive()));
        }

//        if(uploadingNewImage) {
//            artistValidator.validateArtist(patchedArtist, imageFile);
//
//            ObjectListing directoryImages = amazonS3Service.getDirectoryContent((ARTIST_IMAGE_PATH + artistSlug + "/"), null);
//            for (S3ObjectSummary summary: directoryImages.getObjectSummaries()) {
//                amazonS3Service.delete(summary.getKey());
//            }
//
//            patchedArtist.setImage(imageProcessingUtil.processImageAndUpload(patchedArtist.getImageSizes(),
//                    (patchedArtist.ARTIST_IMAGE_UPLOAD_PATH + patchedArtist.getSlug() + "/"),
//                    imageFile, 1.0));
//        } else {
        artistValidator.validateArtist(patchedArtist);
//        }

        artistRepository.save(patchedArtist);

        logger.info("***** SUCCESSFULLY PATCHED ARTIST WITH SLUG = {} *****", patchedArtist.getSlug());

        return patchedArtist;
    }

    @Override
    public void deleteArtist(String artistSlug) {
        logger.info("Delete artist, slug: {}", artistSlug);

        artistValidator.validateArtistSlug(artistSlug);

        deleteAllArtistsImages(artistSlug);

        artistRepository.deleteBySlug(artistSlug);

        logger.info("***** SUCCESSFULLY DELETED ARTIST WITH SLUG = {} *****", artistSlug);
    }

    private String createArtistSlug(String artistName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(artistName);

        int duplicateSlugs = artistRepository.findBySlugStartingWith(result).size();
        return duplicateSlugs > 0 ? result.concat("-" + (duplicateSlugs + 1)) : result;
    }

    private List<Year> retrieveActiveYears(Artist artist) {
        List<Year> artistYears = artist.getYearsActive();
        List<Year> years = new ArrayList<>();

        if(artistYears == null) {
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

    private void deleteAllArtistsImages(Artist artist) {
        ObjectListing directoryImages = amazonS3Service.getDirectoryContent(artist.getAWSKey(), null);
        for (S3ObjectSummary summary: directoryImages.getObjectSummaries()) {
            if(amazonS3Service.doesObjectExist(summary.getKey())) {
                amazonS3Service.delete(summary.getKey());
            }
        }
    }

    private void deleteAllArtistsImages(String artistSlug) {
        ObjectListing directoryImages = amazonS3Service.getDirectoryContent((ARTIST_IMAGE_PATH + artistSlug + "/"), null);
        for (S3ObjectSummary summary: directoryImages.getObjectSummaries()) {
            if(amazonS3Service.doesObjectExist(summary.getKey())) {
                amazonS3Service.delete(summary.getKey());
            }
        }
    }
}
