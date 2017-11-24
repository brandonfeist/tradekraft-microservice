package com.tradekraftcollective.microservice.model;

import com.tradekraftcollective.microservice.utilities.OAuthTokenUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
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

    private List<String> permissions;

    public Credentials(String authorization) {
        this.authorization = authorization;
        parseAuth();
    }

    public void parseAuth() {
        Map<String, Object> authParse = OAuthTokenUtil.parseToken(authorization);
        if(authParse != null) {
            this.username = (String) authParse.get("user_name");
            this.email = (String) authParse.get("email");
            this.permissions = (List<String>) authParse.get("authorities");
        } else {
            this.username = null;
            this.email = null;
            this.permissions = null;
        }
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
        parseAuth();
    }

    public boolean hasPermission(String permission) {
        if(permissions == null) {
            return false;
        }

        for(String listPermission : permissions) {
            if(listPermission.equals(permission)) {
                return true;
            }
        }

        return false;
    }
}
