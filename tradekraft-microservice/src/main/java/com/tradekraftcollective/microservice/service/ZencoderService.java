package com.tradekraftcollective.microservice.service;

import com.brightcove.zencoder.client.ZencoderClient;
import com.brightcove.zencoder.client.ZencoderClientException;
import com.brightcove.zencoder.client.model.ContainerFormat;
import com.brightcove.zencoder.client.request.ZencoderCreateJobRequest;
import com.brightcove.zencoder.client.request.ZencoderOutput;
import com.brightcove.zencoder.client.response.ZencoderCreateJobResponse;
import com.brightcove.zencoder.client.response.ZencoderJobDetail;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ZencoderService {

    @Value("${vcap.services.zencoder.credentials.api_key}")
    private String apiKey;

    /**
     * Transcodes audio to given format and saves it to given output string.
     * @param inputUrl Input file to transcode.
     * @param outputUrlNoExtension Output location for transcoded file without file extension.
     * @param formats Formats to transcode the file to.
     *
     * @return Job ID of created transcoding job.
     */
    public ZencoderJobDetail transcodeAudio(String inputUrl, String outputUrlNoExtension, String[] formats) throws ZencoderClientException {

        ZencoderClient client = new ZencoderClient(apiKey);

        ZencoderCreateJobRequest job = new ZencoderCreateJobRequest();

        job.setInput(inputUrl);

        List<ZencoderOutput> outputs = new ArrayList<>();

        for(int formatIndex = 0; formatIndex < formats.length; formatIndex++) {
            try {
                ZencoderOutput output = new ZencoderOutput();

                output.setFormat(ContainerFormat.fromValue(formats[formatIndex]));
                output.setUrl(outputUrlNoExtension + "." + formats[formatIndex]);
                outputs.add(output);

            } catch (JsonMappingException e) {
                // Mapping exception
            }
        }

        job.setOutputs(outputs);

        ZencoderCreateJobResponse response = client.createZencoderJob(job);

        return client.getZencoderJob(response.getId());
    }
}
