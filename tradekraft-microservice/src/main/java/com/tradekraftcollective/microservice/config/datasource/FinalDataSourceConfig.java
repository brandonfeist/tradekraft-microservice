package com.tradekraftcollective.microservice.config.datasource;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by brandonfeist on 9/4/17.
 */
@Component
@Data
public class FinalDataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(FinalDataSourceConfig.class);

    private final String dataSourceDriverClassName;
    private final String dataSourceUrl;
    private final String dataSourceUsername;
    private final String dataSourcePassword;
    private final DataConnectionParams dataConnectionParams;
    private final DataSourceConfig dataSourceConfig;

    @Autowired
    public FinalDataSourceConfig(@Value("${spring.datasource.driver-class-name:org.postgresql.Driver}") String dataSourceDriverClassName,
                                 @Value("${vcap.services.postgres.credentials.database}") String dbName,
                                 @Value("${vcap.services.postgres.credentials.hostname:null}") String hostname,
                                 @Value("${vcap.services.postgres.credentials.host:null}") String host,
                                 @Value("${vcap.services.postgres.credentials.port}") Integer port,
                                 @Value("${vcap.services.postgres.credentials.username}") String dataSourceUsername,
                                 @Value("${vcap.services.postgres.credentials.password}") String dataSourcePassword,
                                 DataConnectionParams dataConnectionParams) {
        this.dataSourceDriverClassName = dataSourceDriverClassName;
        String dbHost = (hostname == null || "null".equals(hostname)) ? host : hostname;
        this.dataSourceUrl = String.format("jdbc:postgresql://%s:%s/%s?ApplicationName=%s", dbHost, port, dbName, "public");

        logger.info("Connecting to jdbc {}", this.dataSourceUrl);
        this.dataSourceUsername = dataSourceUsername;
        this.dataSourcePassword = dataSourcePassword;
        this.dataConnectionParams = dataConnectionParams;
        this.dataSourceConfig = loadDataSourceConfig();
    }

    public String getDataSourceDriverClassName() { return dataSourceDriverClassName; }

    public DataSourceConfig getDataSourceConfig() { return dataSourceConfig; }

    public DataSourceConfig loadDataSourceConfig() {
        return (new DataSourceConfig(dataSourceUrl, dataSourceUsername, dataSourcePassword, dataConnectionParams));
    }
}
