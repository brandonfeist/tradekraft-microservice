package com.tradekraftcollective.microservice.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResourceUtil {

    public static String convertResourceToString(String destination) throws IOException {
        Resource resource = new ClassPathResource(destination);

        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()),1024);

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line).append('\n');
        }

        br.close();

        return stringBuilder.toString();
    }
}
