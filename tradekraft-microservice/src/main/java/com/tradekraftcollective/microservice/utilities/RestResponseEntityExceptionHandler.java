package com.tradekraftcollective.microservice.utilities;

import com.tradekraftcollective.microservice.exception.ServiceException;
import org.eclipse.persistence.jpa.rs.exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by brandonfeist on 9/9/17.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ServiceException.class})
    protected ResponseEntity<Object> handleServiceException(ServiceException e, WebRequest request) {
        int httpStatus = e.getErrorCode().getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode().name(), e.getPreparedErrorMessage(), request.getContextPath());
        errorResponse.setHttpStatus(httpStatus);

        logger.info("", e);
        return new ResponseEntity<>(errorResponse,
                HttpStatus.valueOf(httpStatus));
    }
}
