package com.tradekraftcollective.microservice.strategies;

import lombok.Data;

/**
 * Created by brandonfeist on 10/27/17.
 */
@Data
public class MetaData {
    private String key;

    private String value;

    public MetaData(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
