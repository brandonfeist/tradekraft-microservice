package com.tradekraftcollective.microservice.utilities.apiRequests;

import com.tradekraftcollective.microservice.service.IHttpManager;
import com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Scheme;
import com.tradekraftcollective.microservice.utilities.UtilProtos.Url;

/**
 * Created by brandonfeist on 11/16/17.
 */
public interface Request {
    interface Builder {
        Builder httpManager(IHttpManager httpManager);
        Builder host(String host);
        Builder port(int port);
        Builder scheme(Scheme scheme);
        AbstractRequest build();
    }

    Url toUrl();
}
