package com.tradekraftcollective.microservice.utilities;

import com.tradekraftcollective.microservice.model.Image;
import com.tradekraftcollective.microservice.model.spotify.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import java.util.*;

public class SpotifyJsonUtil {
    public static SpotifyClientCredentials createApplicationAuthenticationToken(JSONObject jsonObject) {
        final SpotifyClientCredentials token = new SpotifyClientCredentials();

        token.setAccessToken(jsonObject.getString("access_token"));
        token.setExpiresIn(jsonObject.getInt("expires_in"));
        token.setTokenType(jsonObject.getString("token_type"));

        return token;
    }

    public static SpotifyAlbum createAlbum(String json) {
        return createAlbum(JSONObject.fromObject(json));
    }

    private static SpotifyAlbum createAlbum(JSONObject albumJson) {
        if (albumJson == null || albumJson.isNullObject()) {
            return null;
        }

        SpotifyAlbum album = new SpotifyAlbum();

        album.setAlbumType(createAlbumType(albumJson.getString("album_type")));
        album.setArtists(createSimpleArtists(albumJson.getJSONArray("artists")));
        album.setAvailableMarkets(createAvailableMarkets(albumJson.getJSONArray("available_markets")));
        album.setCopyrights(createCopyrights(albumJson.getJSONArray("copyrights")));
        album.setExternalIds(createExternalIds(albumJson.getJSONObject("external_ids")));
        album.setExternalUrls(createExternalUrls(albumJson.getJSONObject("external_urls")));
        album.setGenres(createGenres(albumJson.getJSONArray("genres")));
        album.setHref(albumJson.getString("href"));
        album.setId(albumJson.getString("id"));
        album.setImages(createImages(albumJson.getJSONArray("images")));
        album.setName(albumJson.getString("name"));
        album.setPopularity(albumJson.getInt("popularity"));
        album.setReleaseDate(albumJson.getString("release_date"));
        album.setReleaseDatePrecision(albumJson.getString("release_date_precision"));
        album.setTracks(createSimpleTrackPage(albumJson.getJSONObject("tracks")));
        album.setType(createSpotifyEntityType(albumJson.getString("type")));
        album.setUri(albumJson.getString("uri"));

        return album;
    }

    public static List<SpotifySimpleTrack> createSimpleTracks(JSONArray tracksJson) {
        List<SpotifySimpleTrack> tracks = new ArrayList<>();
        for (int i = 0; i < tracksJson.size(); i++) {
            tracks.add(createSimpleTrack(tracksJson.getJSONObject(i)));
        }
        return tracks;
    }

    public static SpotifySimpleTrack createSimpleTrack(JSONObject simpleTrackJson) {
        SpotifySimpleTrack track = new SpotifySimpleTrack();

        track.setArtists(createSimpleArtists(simpleTrackJson.getJSONArray("artists")));
        track.setAvailableMarkets(createAvailableMarkets(simpleTrackJson.getJSONArray("available_markets")));
        track.setDiscNumber(simpleTrackJson.getInt("disc_number"));
        track.setDuration(simpleTrackJson.getInt("duration_ms"));
        track.setExplicit(simpleTrackJson.getBoolean("explicit"));
        track.setExternalUrls(createExternalUrls(simpleTrackJson.getJSONObject("externalUrls")));
        track.setHref(simpleTrackJson.getString("href"));
        track.setId(simpleTrackJson.getString("id"));
        track.setName(simpleTrackJson.getString("name"));
        track.setPreviewUrl(simpleTrackJson.getString("preview_url"));
        track.setTrackNumber(simpleTrackJson.getInt("track_number"));
        track.setType(createSpotifyEntityType(simpleTrackJson.getString("type")));
        track.setUri(simpleTrackJson.getString("uri"));

        return track;
    }

    public static List<SpotifyCopyright> createCopyrights(JSONArray copyrightsJson) {
        List<SpotifyCopyright> copyrights = new ArrayList<>();
        for (int i = 0; i < copyrightsJson.size(); i++) {
            SpotifyCopyright copyright = new SpotifyCopyright();
            JSONObject copyrightJson = copyrightsJson.getJSONObject(i);
            if (existsAndNotNull("text", copyrightJson)) {
                copyright.setText(copyrightJson.getString("text"));
            }
            if (existsAndNotNull("type", copyrightJson)) {
                copyright.setType(copyrightJson.getString("type"));
            }
            copyrights.add(copyright);
        }
        return copyrights;
    }

    private static List<SpotifySimpleArtist> createSimpleArtists(JSONArray artists) {
        List<SpotifySimpleArtist> returnedArtists = new ArrayList<>();
        for (int i = 0; i < artists.size(); i++) {
            returnedArtists.add(createSimpleArtist(artists.getJSONObject(i)));
        }
        return returnedArtists;
    }

    public static SpotifySimpleArtist createSimpleArtist(JSONObject simpleArtistJson) {
        if (simpleArtistJson == null || simpleArtistJson.isNullObject()) {
            return null;
        }

        SpotifySimpleArtist simpleArtist = new SpotifySimpleArtist();

        simpleArtist.setExternalUrls(createExternalUrls(simpleArtistJson.getJSONObject("external_urls")));
        simpleArtist.setHref(simpleArtistJson.getString("href"));
        simpleArtist.setId(simpleArtistJson.getString("id"));
        simpleArtist.setName(simpleArtistJson.getString("name"));
        simpleArtist.setType(createSpotifyEntityType(simpleArtistJson.getString("type")));
        simpleArtist.setUri(simpleArtistJson.getString("uri"));

        return simpleArtist;
    }

    public static SpotifyExternalIds createExternalIds(JSONObject externalIds) {
        SpotifyExternalIds returnedExternalIds = new SpotifyExternalIds();
        Map<String,String> addedIds = returnedExternalIds.getExternalIds();

        for (Object keyObject : externalIds.keySet()) {
            String key = (String) keyObject;
            addedIds.put(key, externalIds.getString(key));
        }

        return returnedExternalIds;
    }

    public static SpotifyExternalUrls createExternalUrls(JSONObject externalUrls) {
        SpotifyExternalUrls returnedExternalUrls = new SpotifyExternalUrls();
        Map<String,String> addedExternalUrls = returnedExternalUrls.getExternalUrls();
        for (Object keyObject : externalUrls.keySet()) {
            String key = (String) keyObject;
            addedExternalUrls.put(key, externalUrls.getString(key));
        }
        return returnedExternalUrls;
    }

    public static SpotifyAlbumType createAlbumType(String albumType) {
        if ("null".equalsIgnoreCase(albumType)) {
            return null;
        }
        return SpotifyAlbumType.valueOf(albumType.toUpperCase());
    }

    public static SpotifyEntityType createSpotifyEntityType(String type) {
        return SpotifyEntityType.valueOf(type.toUpperCase());
    }

    public static List<String> createAvailableMarkets(JSONArray availableMarketsJson) {
        List<String> availableMarkets = new ArrayList<>();
        for (int i = 0; i < availableMarketsJson.size(); i++) {
            availableMarkets.add(availableMarketsJson.getString(i));
        }
        return availableMarkets;
    }

    public static List<String> createGenres(JSONArray genres) {
        List<String> returnedGenres = new ArrayList<>();
        for (int i = 0; i < genres.size(); i++) {
            returnedGenres.add(genres.getString(i));
        }
        return returnedGenres;
    }

    public static List<Image> createImages(JSONArray images) {
        List<Image> returnedImages = new ArrayList<Image>();
        for (int i = 0; i < images.size(); i++) {
            returnedImages.add(createImage(images.getJSONObject(i)));
        }
        return returnedImages;
    }

    private static SpotifyPage<SpotifySimpleTrack> createSimpleTrackPage(JSONObject simpleTrackPageJson) {
        SpotifyPage<SpotifySimpleTrack> page = createItemlessSimpleTrackPage(simpleTrackPageJson);
        page.setItems(createSimpleTracks(simpleTrackPageJson.getJSONArray("items")));
        return page;
    }

    private static SpotifyPage<SpotifySimpleTrack> createItemlessSimpleTrackPage(JSONObject pageJson) {
        SpotifyPage<SpotifySimpleTrack> page = new SpotifyPage<>();
        page.setHref(pageJson.getString("href"));
        page.setLimit(pageJson.getInt("limit"));
        if (existsAndNotNull("next", pageJson)) {
            page.setNext(pageJson.getString("next"));
        }
        page.setOffset(pageJson.getInt("offset"));
        if (existsAndNotNull("previous", pageJson)) {
            page.setPrevious(pageJson.getString("previous"));
        }
        page.setTotal(pageJson.getInt("total"));
        return page;
    }

    private static Image createImage(JSONObject image) {
        if (JSONNull.getInstance().equals(image)) {
            return null;
        }

        final Image returnedImage = new Image();
        if (image.containsKey("height") && !image.get("height").equals(JSONNull.getInstance())) {
            returnedImage.setHeight(image.getInt(("height")));
        }
        if (image.containsKey("width") && !image.get("width").equals(JSONNull.getInstance())) {
            returnedImage.setWidth(image.getInt(("width")));
        }
        if (image.containsKey("url") && !image.get("url").equals(JSONNull.getInstance())) {
            returnedImage.setUrl(image.getString("url"));
        }
        return returnedImage;
    }

    private static boolean existsAndNotNull(String key, JSONObject jsonObject) {
        return jsonObject.containsKey(key) &&
                !JSONNull.getInstance().equals(jsonObject.get(key));
    }
}
