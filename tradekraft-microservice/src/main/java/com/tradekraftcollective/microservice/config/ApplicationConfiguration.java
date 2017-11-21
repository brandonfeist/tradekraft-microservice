package com.tradekraftcollective.microservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradekraftcollective.microservice.Credentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

/**
 * Created by brandonfeist on 11/6/17.
 */
@Slf4j
@Configuration
@EnableAutoConfiguration
public class ApplicationConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        return restTemplate;
    }

    @Bean
    public ObjectMapper objectMapper() { return new ObjectMapper(); }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public StopWatch stopWatch() { return new StopWatch(); }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Credentials credentials() { return new Credentials(); }
}
