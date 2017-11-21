package com.tradekraftcollective.microservice;

import com.tradekraftcollective.microservice.utilities.OAuthTokenUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by brandonfeist on 11/20/17.
 */
@Getter
@NoArgsConstructor
public class Credentials {
    private String authorization;

    private String email;

    private String username;

    public Credentials(String authorization) {
        this.authorization = authorization;
        parseAuth();
    }

    public void parseAuth() {
        Map<String, Object> authParse = OAuthTokenUtil.parseToken(authorization);
        if(authParse != null) {
            this.username = (String) authParse.get("user_name");
            this.email = (String) authParse.get("email");
        } else {
            this.username = null;
            this.email = null;
        }
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
        parseAuth();
    }
}
