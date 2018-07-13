package com.tradekraftcollective.microservice.utilities;

import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class SpotifyUrlUtil {
//    ﻿                                     (https://open.spotify.com/album/)1OzK0zQhU286ricxsIxA1R
//                                           https://open.spotify.com/album/2zZpOO4U1RPPlsazUZHGAp?si=lCXBuHICRNilZDj6eUC72
    private final String ALBUM_ID_REGEX = "^(https://open.spotify.com/album/)(\\d+|\\w+)(\\?\\S*)?$";

    public String getAlbumId(String url) {
        String id = null;

        Pattern pattern = Pattern.compile(ALBUM_ID_REGEX);
        Matcher matcher = pattern.matcher(url);

        if(matcher.find()) {
            id = matcher.group(2);
        } else {
            log.error("Regex error, could not find a match for Spotify albumId.");
            throw new ServiceException(ErrorCode.INVALID_REGEX, "Could not find a match for given Spotify albumId.");
        }

        return id;
    }
}
