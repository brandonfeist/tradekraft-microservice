package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.exception.WebApiException;
import com.tradekraftcollective.microservice.utilities.UtilProtos.Url;

import java.io.IOException;

/**
 * Created by brandonfeist on 11/16/17.
 */
public interface IHttpManager {
    String get(Url url) throws WebApiException, IOException;

    String post(Url url) throws WebApiException, IOException;

    String delete(Url url) throws WebApiException, IOException;

    String put(Url url) throws WebApiException, IOException;
}
