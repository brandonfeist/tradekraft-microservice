package com.tradekraftcollective.microservice.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by brandonfeist on 9/7/17.
 */
public enum ErrorCode {

    INVALID_ARTIST(HttpStatus.BAD_REQUEST.value(), "Invalid artist"),
    INVALID_ARTIST_SLUG(HttpStatus.BAD_REQUEST.value(), "Invalid artist slug"),
    INVALID_ARTIST_NAME(HttpStatus.BAD_REQUEST.value(), "Invalid artist name"),
    INVALID_ARTIST_URL(HttpStatus.BAD_REQUEST.value(), "Invalid artist url"),

    INVALID_GENRE(HttpStatus.BAD_REQUEST.value(), "Invalid genre"),
    INVALID_GENRE_ID(HttpStatus.BAD_REQUEST.value(), "Invalid genre id"),
    INVALID_GENRE_NAME(HttpStatus.BAD_REQUEST.value(), "Invalid genre name"),
    INVALID_GENRE_COLOR(HttpStatus.BAD_REQUEST.value(), "Invalid genre color"),

    INVALID_EVENT(HttpStatus.BAD_REQUEST.value(), "Invalid event"),
    INVALID_EVENT_SLUG(HttpStatus.BAD_REQUEST.value(), "Invalid event slug"),
    INVALID_EVENT_NAME(HttpStatus.BAD_REQUEST.value(), "Invalid event name"),
    INVALID_EVENT_VENUE_NAME(HttpStatus.BAD_REQUEST.value(), "Invalid event name"),
    INVALID_EVENT_START_DATE(HttpStatus.BAD_REQUEST.value(), "Invalid event start date"),
    INVALID_EVENT_END_DATE(HttpStatus.BAD_REQUEST.value(), "Invalid event end date"),
    INVALID_EVENT_ADDRESS(HttpStatus.BAD_REQUEST.value(), "Invalid event address"),
    INVALID_EVENT_ENTRY_AGE(HttpStatus.BAD_REQUEST.value(), "Invalid event entry age"),

    INVALID_RELEASE_NAME(HttpStatus.BAD_REQUEST.value(), "Invalid release name"),
    INVALID_RELEASE_TYPE(HttpStatus.BAD_REQUEST.value(), "Invalid release type"),
    INVALID_RELEASE_SLUG(HttpStatus.BAD_REQUEST.value(), "Invalid release slug"),

    INVALID_SONG_NAME(HttpStatus.BAD_REQUEST.value(), "Invalid song name"),
    INVALID_SONG_TRACK_NUMBER(HttpStatus.BAD_REQUEST.value(), "Invalid song track number"),
    INVALID_SONG_COUNT(HttpStatus.BAD_REQUEST.value(), "Invalid song count"),
    INVALID_SONG_FILE(HttpStatus.BAD_REQUEST.value(), "Invalid song file"),
    INVALID_SONG_SLUG(HttpStatus.BAD_REQUEST.value(), "Invalid song slug"),

    INVALID_VIDEO(HttpStatus.BAD_REQUEST.value(), "Invalid video"),
    INVALID_VIDEO_PREVIEW_TIME(HttpStatus.BAD_REQUEST.value(), "Invalid video preview time"),
    INVALID_VIDEO_EXTERNAL_LINK(HttpStatus.BAD_REQUEST.value(), "Invalid external video link"),
    INVALID_VIDEO_SLUG(HttpStatus.BAD_REQUEST.value(), "Invalid video slug"),

    INVALID_YEAR(HttpStatus.BAD_REQUEST.value(), "Invalid year"),

    INVALID_IMAGE_DEMINSIONS(HttpStatus.BAD_REQUEST.value(), "Invalid image dimensions"),
    INVALID_IMAGE_EXTENSION(HttpStatus.BAD_REQUEST.value(), "Invalid image extension"),

    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST.value(), "Invalid date range"),

    INVALID_PATCH(HttpStatus.BAD_REQUEST.value(), "Invalid patch"),

    EXTERNAL_SERVICE_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "External service, internal server error"),
    EXTERNAL_SERVICE_BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "External service, bad request"),
    EXTERNAL_SERVICE_EMPTY_RESPONSE(HttpStatus.NO_CONTENT.value(), "External service, empty response");

    private final int errorCode;

    private final String defaultMessage;

    ErrorCode(int errorCode, String defaultMessage) {
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    public int getErrorCode() { return errorCode; }

    public String getDefaultMessage() { return defaultMessage; }
}
