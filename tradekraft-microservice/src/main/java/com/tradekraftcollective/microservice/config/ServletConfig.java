package com.tradekraftcollective.microservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;

/**
 * Created by brandonfeist on 10/23/17.
 */
@Configuration
public class ServletConfig {
    private final Logger logger = LoggerFactory.getLogger(ServletConfig.class);

    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        logger.info("Configuring servlet.");

        final ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
        final String location = System.getProperty("java.io.tmpdir");
        final long maxFileSize = 500000000;
        final long maxRequestSize = 1000000000;
        final MultipartConfigElement multipartConfig  = new MultipartConfigElement(location, maxFileSize, maxRequestSize, 0);
        registration.setMultipartConfig(multipartConfig);

        return registration;
    }
}
