package com.tradekraftcollective.microservice.exception;

/**
 * Created by brandonfeist on 11/16/17.
 */
public class WebApiException extends Exception {

    public WebApiException(String message) {
        super(message);
    }

    public WebApiException() {
        super();
    }

}
