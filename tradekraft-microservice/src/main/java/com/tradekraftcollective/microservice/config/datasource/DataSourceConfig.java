package com.tradekraftcollective.microservice.config.datasource;

import lombok.Data;

/**
 * Created by brandonfeist on 9/3/17.
 */
@Data
public class DataSourceConfig {
    private final String dataSourceUrl;
    private final String username;
    private final String password;
    private final DataConnectionParams dataConnectionParams;

    public DataSourceConfig(String dataSourceUrl, String username, String password, DataConnectionParams dataConnectionParams) {
        this.dataSourceUrl = dataSourceUrl;
        this.username = username;
        this.password = password;
        this.dataConnectionParams = dataConnectionParams;
    }

    public String getDataSourceUrl() { return dataSourceUrl; }

    public String getDataSourceUsername() { return username; }

    public String getDataSourcePassword() { return password; }

    public DataConnectionParams getDataConnectionParams() { return dataConnectionParams; }
}
