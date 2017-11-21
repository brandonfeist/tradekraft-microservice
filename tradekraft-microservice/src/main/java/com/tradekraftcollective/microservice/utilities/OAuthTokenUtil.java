package com.tradekraftcollective.microservice.utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created by brandonfeist on 11/20/17.
 */
@Slf4j
@Component
@NoArgsConstructor
public class OAuthTokenUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> parseToken(String inputToken) {
        Jwt jwt;
        Map<String, Object> claims = null;

        try {
            if(inputToken != null) {
                String token = inputToken.contains(" ") ? inputToken.split(" ")[1].trim() : inputToken;
                jwt = JwtHelper.decode(token);

                if(jwt != null) {
                    jwt.getClaims();
                    claims = objectMapper.readValue(jwt.getClaims(), new TypeReference<Map<String, Object>>() {
                    });
                }
            }
        } catch (IOException e) {
            log.error("Error parsing token: " + inputToken, e);
        }
        return claims;
    }
}
