package com.tradekraftcollective.microservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public class JsonMapper {

    public static <T> T convertJsonToObject(String destination, Class<T> clazz) throws IOException {
        InputStream input = new ClassPathResource(destination).getInputStream();

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(input, clazz);
    }
}
