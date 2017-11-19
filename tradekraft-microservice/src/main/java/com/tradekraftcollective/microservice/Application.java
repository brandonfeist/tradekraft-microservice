package com.tradekraftcollective.microservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Created by brandonfeist on 8/30/17.
 */
@Slf4j
@SpringBootApplication
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).run(args);
        log.info("Starting TradeKraft Micro-service");
    }
}
