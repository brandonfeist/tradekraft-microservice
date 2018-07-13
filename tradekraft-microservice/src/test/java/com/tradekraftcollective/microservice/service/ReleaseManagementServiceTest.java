package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.repository.IReleaseRepository;
import com.tradekraftcollective.microservice.service.impl.ReleaseManagementService;
import com.tradekraftcollective.microservice.service.impl.SongManagementService;
import com.tradekraftcollective.microservice.util.JsonMapper;
import com.tradekraftcollective.microservice.validator.ReleaseValidator;
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
public class ReleaseManagementServiceTest {

    @InjectMocks
    private ReleaseManagementService releaseManagementService;

    @Mock
    private IReleaseRepository releaseRepository;

    @Mock
    private SongManagementService songManagementService;

    @Mock
    private ReleaseValidator releaseValidator;

    private Release testRelease;

    @Before
    public void setup() throws IOException {
        testRelease = JsonMapper.convertJsonToObject("/json/release/ValidRelease.json", Release.class);
    }

    @Test
    public void testCreateValidRelease() {

        Mockito.doNothing().when(releaseValidator).validateRelease(Mockito.any(Release.class));

        Mockito.when(releaseRepository.save(Mockito.any(Release.class))).thenReturn(testRelease);

        Release returnTestRelease = releaseManagementService.createRelease(testRelease);

        Assert.assertNotNull("Release should not be null", returnTestRelease);

        Assert.assertEquals("The incorrect release was returned", testRelease, returnTestRelease);

        Assert.assertEquals("The release slug is not correct", "test-release", returnTestRelease.getSlug());
    }

    @Test
    public void testGetReleaseWithValidSlug() {

        testRelease.setSlug("test-release");

        Mockito.when(releaseRepository.findBySlug(Mockito.anyString())).thenReturn(testRelease);

        releaseManagementService.getRelease("test-release");
    }

    @Test(expected = ServiceException.class)
    public void testGetReleaseWithNullSlug() {

        releaseManagementService.getRelease(null);
    }
}
