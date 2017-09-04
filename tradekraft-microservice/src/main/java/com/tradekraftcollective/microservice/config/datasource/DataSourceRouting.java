package com.tradekraftcollective.microservice.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by brandonfeist on 9/3/17.
 */
public class DataSourceRouting extends AbstractRoutingDataSource {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceRouting.class);

    private Map<String, TransactionAwareDataSourceProxy> dbUrlProxies = new HashMap<>();
    private final FinalDataSourceConfig finalDataSourceConfig;

    public DataSourceRouting(FinalDataSourceConfig finalDataSourceConfig) {
        this.finalDataSourceConfig = finalDataSourceConfig;
    }

    @Override
    public DataSource determineTargetDataSource() {
        String lookupKey = (String) this.determineCurrentLookupKey();
        logger.debug("Determining DataSource");

        DataSourceConfig dataSourceConfig = finalDataSourceConfig.getDataSourceConfig();
        DataConnectionParams dataConnectionParams = dataSourceConfig.getDataConnectionParams();

        TransactionAwareDataSourceProxy proxy = dbUrlProxies.get(dataSourceConfig.getDataSourceUrl());
        if(proxy == null) {
            synchronized (DataSourceRouting.class) {
                if(dbUrlProxies.get(dataSourceConfig.getDataSourceUrl()) == null) {
                    org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();

                    dataSource.setDriverClassName(finalDataSourceConfig.getDataSourceDriverClassName());
                    dataSource.setUrl(dataSourceConfig.getDataSourceUrl());
                    dataSource.setUsername(dataSourceConfig.getDataSourceUsername());
                    dataSource.setPassword(dataSourceConfig.getDataSourcePassword());
                    dataSource.setDefaultAutoCommit(false);
                    dataSource.setUseDisposableConnectionFacade(true);
                    dataSource.setJmxEnabled(true);
                    dataSource.setTestWhileIdle(false);
                    dataSource.setTestOnBorrow(true);
                    dataSource.setTestOnReturn(false);
                    dataSource.setValidationInterval(dataConnectionParams.getValidationIntervalMillis());
                    dataSource.setValidationQuery("SELECT 1");
                    dataSource.setTimeBetweenEvictionRunsMillis(dataConnectionParams.getTimeBetweenEvictionsMillis());
                    dataSource.setMaxActive(dataConnectionParams.getMaxActive());
                    dataSource.setInitialSize(dataConnectionParams.getInitialConnectionsSize());
                    dataSource.setMaxWait(dataConnectionParams.getMaxWait());
                    dataSource.setRemoveAbandonedTimeout(dataConnectionParams.getRemoveAbandonedTimeout());
                    dataSource.setRemoveAbandoned(true);
                    dataSource.setMinEvictableIdleTimeMillis(dataConnectionParams.getMinEvictableIdleTimeMillis());
                    dataSource.setMinIdle(dataConnectionParams.getMinIdleConnections());
                    dataSource.setMaxIdle(dataConnectionParams.getMaxIdleConnections());
                    dataSource.setLogAbandoned(true);
                    dataSource.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                    dataSource.setJdbcInterceptors(
                            "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                            "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer;" +
                            "org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer;" +
                            "org.apache.tomcat.jdbc.pool.interceptor.SlowQueryReport;");
                    proxy = new TransactionAwareDataSourceProxy(dataSource);
                    dbUrlProxies.put(dataSourceConfig.getDataSourceUrl(), proxy);
                }
            }
        }

        return proxy;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = this.determineTargetDataSource().getConnection();
        logger.info("Connection being returned {}", connection);
        return connection;
    }

    @Override
    public void afterPropertiesSet() { }

    @Override
    protected Object determineCurrentLookupKey() { return null; }
}
