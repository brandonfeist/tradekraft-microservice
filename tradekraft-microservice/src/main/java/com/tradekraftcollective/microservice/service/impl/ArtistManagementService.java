package com.tradekraftcollective.microservice.service.impl;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.service.IArtistManagementService;
import com.tradekraftcollective.microservice.utilities.ImageProcessingUtil;
import com.tradekraftcollective.microservice.validator.ArtistValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
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

    @Inject
    ArtistValidator artistValidator;

    @Inject
    ArtistPatchService artistPatchService;

    @Inject
    ImageProcessingUtil imageProcessingUtil;

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
            logger.error("Artist slug cannot be null");
            throw new ServiceException(ErrorCode.INVALID_ARTIST_SLUG, "artist slug cannot be null.");
        }

        artistSlug = artistSlug.toLowerCase();

        Artist artist = artistRepository.findBySlug(artistSlug);

        return artist;
    }

    @Override
    public Artist createArtist(Artist artist, MultipartFile imageFile, StopWatch stopWatch) {
        logger.info("Create artist, name: {}", artist.getName());


        stopWatch.start("validateArtist");
        artistValidator.validateArtist(artist, imageFile);
        stopWatch.stop();

        stopWatch.start("saveArtist");

        artist.setSlug(createArtistSlug(artist.getName()));
        artist.setImage(uploadArtistImage(artist, imageFile));
        Artist returnArtist = artistRepository.save(artist);

        stopWatch.stop();
        logger.info("***** SUCCESSFULLY CREATED ARTIST WITH SLUG = {}", returnArtist.getSlug());

        return returnArtist;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class})
    public Artist patchArtist(List<JsonPatchOperation> patchOperations, MultipartFile imageFile, String artistSlug) {
        Artist oldArtist = artistRepository.findBySlug(artistSlug);
        if(oldArtist == null) {
            logger.error("Artist with slug [{}] does not exist", artistSlug);
            throw new ServiceException(ErrorCode.INVALID_ARTIST_SLUG, "Artist with slug [" + artistSlug + "] does not exist");
        }

        if(imageFile != null) {
//            JsonPatchOperation imagePatch = new Jo();

//            patchOperations.add(imagePatch);
        }

        Artist patchedArtist = artistPatchService.patchArtist(patchOperations, oldArtist);

        // Should upload images last just in case of rollback, same with createArtist

        return patchedArtist;
    }

    @Override
    public void deleteArtist(String artistSlug) {
        logger.info("Delete artist, slug: {}", artistSlug);

        artistValidator.validateArtistSlug(artistSlug);

        ObjectListing directoryImages = amazonS3Service.getDirectoryContent((ARTIST_IMAGE_PATH + artistSlug + "/"), null);
        for (S3ObjectSummary summary: directoryImages.getObjectSummaries()) {
            amazonS3Service.delete(summary.getKey());
        }

        artistRepository.deleteBySlug(artistSlug);
    }

    private String createArtistSlug(String artistName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(artistName);

        int duplicateSlugs = artistRepository.findBySlugStartingWith(result).size();
        return duplicateSlugs > 0 ? result.concat("-" + (duplicateSlugs + 1)) : result;
    }

    private String uploadArtistImage(Artist artist, MultipartFile imageFile) {
        String uploadPath = ARTIST_IMAGE_PATH + artist.getSlug() + "/";
        String fileName = imageFile.getOriginalFilename();

        try {
            for (Artist.FileSizes imageSize : Artist.FileSizes.values()) {
                if (imageSize != Artist.FileSizes.ORIGINAL) {
                    logger.debug("Uploading {} image size ({}, {})", imageSize.getSizeName(), imageSize.getWidth(), imageSize.getHeight());

                    File tmpFile = new File((imageSize.getSizeName() + "_" + fileName));

                    tmpFile.createNewFile();

                    amazonS3Service.upload(imageProcessingUtil.resizeToLimit(imageSize.getWidth(), imageSize.getHeight(), 1.0, imageFile, tmpFile),
                            uploadPath, (imageSize.getSizeName() + "_" + fileName ));

                    tmpFile.delete();
                } else {
                    logger.debug("Uploading original image size ({}, {})", imageSize.getWidth(), imageSize.getHeight());

                    File tmpFile = new File(fileName);

                    tmpFile.createNewFile();

                    amazonS3Service.upload(imageProcessingUtil.resizeToLimit(imageSize.getWidth(), imageSize.getHeight(), 1.0, imageFile, tmpFile),
                            uploadPath, fileName);

                    tmpFile.delete();
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }
}
