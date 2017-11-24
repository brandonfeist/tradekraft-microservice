package com.tradekraftcollective.microservice.utilities;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SpotifyUrlUtil {
//    ﻿(https://open.spotify.com/album/)1OzK0zQhU286ricxsIxA1R
    private final String ALBUM_ID_REGEX = "^(https://open.spotify.com/album/)(\\d|\\w+)$";

    public String getAlbumId(String url) {
        String id = null;
        Pattern pattern = Pattern.compile(ALBUM_ID_REGEX);
        Matcher matcher = pattern.matcher(url);

        if(matcher.find()) {
            id = matcher.group(2);
        }

        return id;
    }
}
