package com.tradekraftcollective.microservice.service.impl;

import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.service.IArtistManagementService;
import com.tradekraftcollective.microservice.validator.ArtistValidator;
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

    private enum ARTIST_IMAGE_SIZES {
        ORIGINAL("", 1024, 1024),
        THUMB("thumb", 150, 150);

        private final String sizeName;
        private final int width;
        private final int height;

        ARTIST_IMAGE_SIZES(String sizeName, int width, int height) {
            this.sizeName = sizeName;
            this.width = width;
            this.height = height;
        }

        public String getSizeName() { return sizeName; }

        public int getWidth() { return width; }

        public int getHeight() { return height; }
    }

    private static final String DESCENDING = "desc";
    private static final String ARTIST_IMAGE_PATH = "uploads/artist/image/";

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

    private String createArtistSlug(String artistName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(artistName);

        int duplicateSlugs = artistRepository.findBySlugStartingWith(result).size();
        return duplicateSlugs > 0 ? result.concat("-" + (duplicateSlugs + 1)) : result;
    }

    private String uploadArtistImage(Artist artist, MultipartFile imageFile) {
        String uploadPath = ARTIST_IMAGE_PATH + artist.getSlug() + "/";
        String fileName = imageFile.getOriginalFilename();

        // Need to implement multiple file size uploads and how to store in the database
        // need to get file name and must append image size on file name to have different versions
        // Should store array of image file names in database? Should I also include the filepath in the database?
        // Or can model append image sizes and filepath upon serve to front end?

        // Maybe have default final file path string in model
        // Have function in model to getFilePath()
        // See if model can change image variable before serve to append amazon ulr, getFilePath filepath, and different file sizes?

        // Maybe this function in charge of changing image sizes and file names and putting them into one list
        // Then pass off to AmazonS3Service.upload(List<MultipartFile> multipartFiles, String filePath) to upload all at once
        for (ARTIST_IMAGE_SIZES imageSize : ARTIST_IMAGE_SIZES.values()) {
            if (imageSize != ARTIST_IMAGE_SIZES.ORIGINAL) {
                amazonS3Service.upload(imageFile, uploadPath, (imageSize.getSizeName() + "_" + fileName ));
            }
        }

        return fileName;
    }
}
