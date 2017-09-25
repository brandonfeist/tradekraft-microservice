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

    INVALID_IMAGE_DEMINSIONS(HttpStatus.BAD_REQUEST.value(), "Invalid image dimensions"),
    INVALID_IMAGE_EXTENSION(HttpStatus.BAD_REQUEST.value(), "Invalid image extension");

    private final int errorCode;

    private final String defaultMessage;

    ErrorCode(int errorCode, String defaultMessage) {
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    public int getErrorCode() { return errorCode; }

    public String getDefaultMessage() { return defaultMessage; }
}
