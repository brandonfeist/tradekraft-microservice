package com.tradekraftcollective.microservice.config.datasource;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by brandonfeist on 9/3/17.
 */
@Component
@Data
public class DataConnectionParams {

    @Value("${db_max_idle_connections:1}")
    private Integer maxIdleConnections;

    @Value("${db_min_idle_connections:1}")
    private Integer minIdleConnections;

    @Value("${db_initial_connections:1}")
    private Integer initialConnectionsSize;

    @Value("${db_abandon_timeout_sec:300}")
    private Integer abandonTimeoutSec;

    @Value("${db_max_wait:30000}")
    private Integer maxWait;

    @Value("${db_validation_interval_ms:3000}")
    private Integer validationIntervalMillis;

    @Value("${db_time_between_evications_ms:5000}")
    private Integer timeBetweenEvictionsMillis;

    @Value("${db_max_active:5}")
    private Integer maxActive;

    @Value("${db_remove_abandoned_timeout_sec:240}")
    private Integer removeAbandonedTimeout;

    @Value("${db_evictable_idle_time_ms:30000}")
    private Integer minEvictableIdleTimeMillis;
}
