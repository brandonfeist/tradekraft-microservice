package com.tradekraftcollective.microservice.exception;

/**
 * Created by brandonfeist on 9/7/17.
 */
public class ServiceException extends RuntimeException {
    private final ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCode, String message, Throwable e) {
        super(message, e);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() { return errorCode; }

    public String getPreparedErrorMessage() {
        return (getErrorCode() != null) ? getErrorCode().getDefaultMessage() + ": " + getMessage() : getMessage();
    }
}
