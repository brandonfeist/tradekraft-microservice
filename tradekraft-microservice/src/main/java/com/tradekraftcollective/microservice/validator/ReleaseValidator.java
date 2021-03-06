package com.tradekraftcollective.microservice.validator;

import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.repository.IReleaseRepository;
import com.tradekraftcollective.microservice.utilities.ImageValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by brandonfeist on 10/22/17.
 */
@Slf4j
@Component
public class ReleaseValidator {

    @Inject
    private IReleaseRepository releaseRepository;

    private static final String[] VALID_RELEASE_TYPES = {
            "single", "ep", "lp"
    };

    public void validateRelease(Release release) {
        validateReleaseType(release);
        validateReleaseLinks(release);
    }

    public Release validateReleaseSlug(String releaseSlug) {
        Release release = releaseRepository.findBySlug(releaseSlug);

        if(release == null) {
            log.error("Release with slug [{}] does not exist", releaseSlug);
            throw new ServiceException(ErrorCode.INVALID_RELEASE_SLUG, "Release with slug [" + releaseSlug + "] does not exist");
        }

        return release;
    }

    private void validateReleaseType(Release release) {
        if(release.getReleaseType() == null || release.getReleaseType().isEmpty()) {
            log.error("Missing release type.");
            throw new ServiceException(ErrorCode.INVALID_RELEASE_TYPE, "release type must be present.");
        }

        boolean validReleaseType = false;
        for(String releaseType : VALID_RELEASE_TYPES) {
            if(release.getReleaseType().toLowerCase().equals(releaseType)) {
                validReleaseType = true;
            }
        }

        if(!validReleaseType) {
            log.error("Invalid release type. Valid release types are [single, ep, lp]");
            throw new ServiceException(ErrorCode.INVALID_RELEASE_TYPE, "valid release types are [single, ep, lp].");
        }
    }

    private void validateReleaseLinks(Release release) {

    }
}
