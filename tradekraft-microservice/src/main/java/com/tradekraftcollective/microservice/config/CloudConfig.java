package com.tradekraftcollective.microservice.config;

import com.tradekraftcollective.microservice.config.datasource.DataSourceRouting;
import com.tradekraftcollective.microservice.config.datasource.FinalDataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by brandonfeist on 9/3/17.
 */
@Slf4j
@Configuration
public class CloudConfig {
    @Autowired
    FinalDataSourceConfig finalDataSourceConfig;

    @Bean
    public DataSource dataSource() {
        log.info("Using cloud DataSource");
        return new DataSourceRouting(finalDataSourceConfig);
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        log.info("Using cloud entityManagerFactory");
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPersistenceUnitName("tradekraftPersistenceUnit");
        return entityManagerFactoryBean;
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        log.info("Using cloud TransactionManager");
        return new JpaTransactionManager(entityManagerFactory);
    }
}
