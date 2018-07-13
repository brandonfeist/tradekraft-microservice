package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Year;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
import com.tradekraftcollective.microservice.repository.IYearRepository;
import com.tradekraftcollective.microservice.service.impl.ArtistManagementService;
import com.tradekraftcollective.microservice.util.JsonMapper;
import com.tradekraftcollective.microservice.validator.ArtistValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class ArtistManagementServiceTest {

    private final String SOUNDCLOUD_URL_UPDATE = "http://www.soundcloud.com/changedSoundcloud";

    private final String ARTIST_NAME_UPDATE = "Test Artist Update";

    @InjectMocks
    private ArtistManagementService artistManagementService;

    @Mock
    private ArtistValidator artistValidator;

    @Mock
    private IArtistRepository artistRepository;

    @Mock
    private IYearRepository yearRepository;

    private Artist testArtist;

    @Test
    public void testCreateValidArtist() throws IOException {

        testArtist = JsonMapper.convertJsonToObject("/json/artist/ValidArtist.json", Artist.class);

        Mockito.doNothing().when(artistValidator).validateArtist(Mockito.any(Artist.class));

        Mockito.when(yearRepository.findByYear(Mockito.anyString())).thenReturn(createYear(2018));

        Mockito.when(artistRepository.save(Mockito.any(Artist.class))).thenReturn(testArtist);

        Artist returnTestArtist = artistManagementService.createArtist(testArtist);

        Assert.assertNotNull("Artist should not be null", returnTestArtist);

        Assert.assertEquals("The incorrect artist was returned", testArtist, returnTestArtist);

        Assert.assertEquals("The artist slug is not correct", "test-artist", returnTestArtist.getSlug());
    }

    @Test
    public void testCreateValidArtistWithNoYear() throws IOException {

        testArtist = JsonMapper.convertJsonToObject("/json/artist/ValidArtist.json", Artist.class);

        testArtist.setYearsActive(Collections.emptyList());

        Mockito.doNothing().when(artistValidator).validateArtist(Mockito.any(Artist.class));

        Mockito.when(artistRepository.save(Mockito.any(Artist.class))).thenReturn(testArtist);

        Artist returnTestArtist = artistManagementService.createArtist(testArtist);

        Assert.assertNotNull("Artist should not be null", returnTestArtist);

        Assert.assertEquals("The incorrect artist was returned", testArtist, returnTestArtist);

        Assert.assertNull("Artist yearsActive should be null", returnTestArtist.getYearsActive());

        Assert.assertEquals("The artist slug is not correct", "test-artist", returnTestArtist.getSlug());
    }

    @Test
    public void testUpdateArtistWithValidArtistUpdatesAndValidSlug() throws IOException {

        Artist artistUpdates = JsonMapper.convertJsonToObject("/json/artist/ValidArtist.json", Artist.class);

        artistUpdates.setSoundcloud(SOUNDCLOUD_URL_UPDATE);

        Artist originalArtist = JsonMapper.convertJsonToObject("/json/artist/ValidArtist.json", Artist.class);

        originalArtist.setSlug("test-artist");

        testArtist = JsonMapper.convertJsonToObject("/json/artist/ValidArtist.json", Artist.class);

        testArtist.setSlug("test-artist");

        Mockito.when(artistRepository.findBySlug(Mockito.anyString())).thenReturn(testArtist);

        Mockito.verify(artistRepository, Mockito.never()).findBySlugStartingWith(Mockito.anyString());

        Mockito.doNothing().when(artistValidator).validateArtist(Mockito.any(Artist.class));

        Mockito.when(artistRepository.save(Mockito.any(Artist.class))).thenReturn(testArtist);

        Artist returnArtist = artistManagementService.updateArtist(artistUpdates, "test-artist");

        Assert.assertNotNull("Artist should not be null", returnArtist);

        Assert.assertNotEquals("Artist should have been updated", originalArtist, returnArtist);

        Assert.assertEquals("Original artist and updated artist must have the same slug",
                originalArtist.getSlug(), returnArtist.getSlug());

        Assert.assertEquals("Artist Soundcloud should have been updated", SOUNDCLOUD_URL_UPDATE, returnArtist.getSoundcloud());
    }

    @Test
    public void testUpdateArtistWithValidArtistUpdatesAndValidSlugWithNameChange() throws IOException {

        Artist artistUpdates = JsonMapper.convertJsonToObject("/json/artist/ValidArtist.json", Artist.class);

        artistUpdates.setName(ARTIST_NAME_UPDATE);

        Artist originalArtist = JsonMapper.convertJsonToObject("/json/artist/ValidArtist.json", Artist.class);

        originalArtist.setSlug("test-artist");

        testArtist = JsonMapper.convertJsonToObject("/json/artist/ValidArtist.json", Artist.class);

        testArtist.setSlug("test-artist");

        Mockito.when(artistRepository.findBySlug(Mockito.anyString())).thenReturn(testArtist);

        Mockito.when(artistRepository.findBySlugStartingWith(Mockito.anyString())).thenReturn(Collections.emptyList());

        Mockito.doNothing().when(artistValidator).validateArtist(Mockito.any(Artist.class));

        Mockito.when(artistRepository.save(Mockito.any(Artist.class))).thenReturn(testArtist);

        Artist returnArtist = artistManagementService.updateArtist(artistUpdates, "test-artist");

        Assert.assertNotNull("Artist should not be null", returnArtist);

        Assert.assertNotEquals("Artist should have been updated", originalArtist, returnArtist);

        Assert.assertNotEquals("Artist name should have been updated", originalArtist.getName(), returnArtist.getName());

        Assert.assertNotEquals("Artist slug should have been updated", originalArtist.getSlug(), returnArtist.getSlug());
    }

    @Test(expected = ServiceException.class)
    public void testUpdateArtistWithValidArtistUpdatesAndInvalidSlug() throws IOException {
        Artist artistUpdates = JsonMapper.convertJsonToObject("/json/artist/ValidArtist.json", Artist.class);

        artistUpdates.setName(ARTIST_NAME_UPDATE);

        Mockito.when(artistRepository.findBySlug(Mockito.anyString())).thenReturn(null);

        Mockito.verify(artistRepository, Mockito.never()).findBySlugStartingWith(Mockito.anyString());

        artistManagementService.updateArtist(artistUpdates, "invalid-slug");
    }

    @Test
    public void testGetArtistWithValidSlug() throws IOException {

        testArtist = JsonMapper.convertJsonToObject("/json/artist/ValidArtist.json", Artist.class);
        testArtist.setSlug("test-artist");

        Mockito.when(artistRepository.findBySlug(Mockito.anyString())).thenReturn(testArtist);

        artistManagementService.getArtist("test-artist");
    }

    @Test(expected = ServiceException.class)
    public void testGetArtistWithNullSlug() {

        artistManagementService.getArtist(null);
    }

    private Year createYear(int yearNum) {
        Year year = new Year();

        year.setId(new Long(1));

        year.setYear(new Integer(yearNum));

        return year;
    }
}
