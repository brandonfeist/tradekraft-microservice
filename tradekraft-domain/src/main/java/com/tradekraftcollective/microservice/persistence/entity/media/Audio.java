package com.tradekraftcollective.microservice.persistence.entity.media;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Audio {
    public Audio() {}

    public Audio(String name, String link) {
        this.name = name;
        this.link = link;
    }

    private String name;

    private String link;

}
