package com.tradekraftcollective.microservice.validator;

import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.regex.Pattern;

/**
 * Created by brandonfeist on 9/7/17.
 */
@Slf4j
@Component
public class ArtistValidator {
    private final String SOUNDCLOUD_ARTIST_URL_REGEX = "^(http:\\/\\/|https:\\/\\/)(www.)?soundcloud.com\\/[^\\/]+(\\/)?$";
    private final String FACEBOOK_ARTIST_URL_REGEX = "^(http:\\/\\/|https:\\/\\/)(www.)?facebook.com\\/[^\\/]+(\\/)?$";
    private final String TWITTER_ARTIST_URL_REGEX = "^(http:\\/\\/|https:\\/\\/)(www.)?twitter.com\\/[^\\/]+(\\/)?$";
    private final String INSTAGRAM_ARTIST_URL_REGEX = "^(http:\\/\\/|https:\\/\\/)(www.)?instagram.com\\/[^\\/]+(\\/)?$";
    private final String SPOTIFY_ARTIST_URL_REGEX = "^(http:\\/\\/|https:\\/\\/)(www.)?open.spotify.com\\/[^\\/]+\\/[^\\/]+(\\/)?$";

    @Inject
    IArtistRepository artistRepository;

    public void validateArtist(Artist artist) {
        validateArtistLinks(artist);
    }

    public Artist validateArtistSlug(String artistSlug) {
        Artist artist = artistRepository.findBySlug(artistSlug);

        if(artist == null) {
            log.error("Artist with slug [{}] does not exist", artistSlug);
            throw new ServiceException(ErrorCode.INVALID_ARTIST_SLUG, "Artist with slug [" + artistSlug + "] does not exist");
        }

        return artist;
    }

    private void validateArtistLinks(Artist artist) {
        if(!(artist.getSoundcloud() == null || artist.getSoundcloud().isEmpty()) &&
                !Pattern.compile(SOUNDCLOUD_ARTIST_URL_REGEX).matcher(artist.getSoundcloud()).find()) {

            log.error("Invalid soundcloud url [{}]", artist.getSoundcloud());
            throw new ServiceException(ErrorCode.INVALID_ARTIST_URL, "invalid soundcloud url.");
        }

        if(!(artist.getFacebook() == null || artist.getFacebook().isEmpty()) &&
                !Pattern.compile(FACEBOOK_ARTIST_URL_REGEX).matcher(artist.getFacebook()).find()) {

            log.error("Invalid facebook url [{}]", artist.getFacebook());
            throw new ServiceException(ErrorCode.INVALID_ARTIST_URL, "invalid facebook url.");
        }

        if(!(artist.getTwitter() == null || artist.getTwitter().isEmpty()) &&
                !Pattern.compile(TWITTER_ARTIST_URL_REGEX).matcher(artist.getTwitter()).find()) {

            log.error("Invalid twitter url [{}]", artist.getTwitter());
            throw new ServiceException(ErrorCode.INVALID_ARTIST_URL, "invalid twitter url.");
        }

        if(!(artist.getInstagram() == null || artist.getInstagram().isEmpty()) &&
                !Pattern.compile(INSTAGRAM_ARTIST_URL_REGEX).matcher(artist.getInstagram()).find()) {

            log.error("Invalid instagram url [{}]", artist.getInstagram());
            throw new ServiceException(ErrorCode.INVALID_ARTIST_URL, "invalid instagram url.");
        }

        if(!(artist.getSpotify() == null || artist.getSpotify().isEmpty()) &&
                !Pattern.compile(SPOTIFY_ARTIST_URL_REGEX).matcher(artist.getSpotify()).find()) {

            log.error("Invalid spotify url [{}]", artist.getSpotify());
            throw new ServiceException(ErrorCode.INVALID_ARTIST_URL, "invalid spotify url.");
        }
    }
}
