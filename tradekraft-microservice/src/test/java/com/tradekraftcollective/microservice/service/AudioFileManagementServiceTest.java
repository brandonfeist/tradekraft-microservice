package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.repository.IAudioFileRepository;
import com.tradekraftcollective.microservice.service.impl.AudioFileManagementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AudioFileManagementServiceTest {

    @InjectMocks
    private AudioFileManagementService audioFileManagementService;

    @Mock
    private IAudioFileRepository audioFileRepository;

    @Mock
    private ZencoderService zencoderService;

    @Mock
    private AmazonS3Service amazonS3Service;

    @Test
    public void uploadValidAudioFileTest() {

    }

    @Test
    public void uploadInvalidAudioFileExtensionTest() {

    }
}
