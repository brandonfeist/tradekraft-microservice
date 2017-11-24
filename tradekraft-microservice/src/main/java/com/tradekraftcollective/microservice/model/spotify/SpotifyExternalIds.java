package com.tradekraftcollective.microservice.model.spotify;

import java.util.HashMap;
import java.util.Map;

public class SpotifyExternalIds {
    private final Map<String,String> externalIds = new HashMap<String,String>();

    public Map<String,String> getExternalIds() {
        return externalIds;
    }
}
