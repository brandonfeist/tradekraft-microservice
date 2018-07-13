package com.tradekraftcollective.microservice.validator;

import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.repository.IReleaseRepository;
import com.tradekraftcollective.microservice.util.JsonMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class ReleaseValidatorTest {

    @InjectMocks
    private ReleaseValidator releaseValidator;

    @Mock
    private IReleaseRepository releaseRepository;

    private Release testRelease;

    @Before
    public void setup() throws IOException {
        testRelease = JsonMapper.convertJsonToObject("/json/release/ValidRelease.json", Release.class);
    }

    @Test
    public void testValidateReleaseSlug() {
        Mockito.when(releaseRepository.findBySlug(Mockito.anyString())).thenReturn(new Release());

        testRelease = releaseValidator.validateReleaseSlug("test-release");

        Assert.assertNotNull("A release object must be returned", testRelease);
    }

    @Test(expected = ServiceException.class)
    public void testValidateInvalidReleaseSlug() {
        Mockito.when(releaseRepository.findBySlug(Mockito.anyString())).thenReturn(null);

        releaseValidator.validateReleaseSlug("test-release");
    }

    @Test
    public void testValidateValidRelease() {
        releaseValidator.validateRelease(testRelease);
    }

    @Test(expected = ServiceException.class)
    public void testValidateNullReleaseType() {
        testRelease.setReleaseType(null);

        releaseValidator.validateRelease(testRelease);
    }

    @Test(expected = ServiceException.class)
    public void testValidateEmptyReleaseType() {
        testRelease.setReleaseType(" ");

        releaseValidator.validateRelease(testRelease);
    }

    @Test(expected = ServiceException.class)
    public void testValidateInvalidReleaseType() {
        testRelease.setReleaseType("invalidReleaseType");

        releaseValidator.validateRelease(testRelease);
    }
}
