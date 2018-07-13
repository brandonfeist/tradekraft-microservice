package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.model.Credentials;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.repository.ISongRepository;
import com.tradekraftcollective.microservice.service.impl.SongManagementService;
import com.tradekraftcollective.microservice.util.JsonMapper;
import com.tradekraftcollective.microservice.utilities.SpotifyUrlUtil;
import com.tradekraftcollective.microservice.validator.ArtistValidator;
import com.tradekraftcollective.microservice.validator.GenreValidator;
import com.tradekraftcollective.microservice.validator.ReleaseValidator;
import com.tradekraftcollective.microservice.validator.SongValidator;
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
public class SongManagementServiceTest {

    @InjectMocks
    private SongManagementService songManagementService;

    @Mock
    private ISongRepository songRepository;

    @Mock
    private SongValidator songValidator;

    @Mock
    private ReleaseValidator releaseValidator;

    @Mock
    private GenreValidator genreValidator;

    @Mock
    private ArtistValidator artistValidator;

    @Mock
    private ISpotifyManagementService spotifyManagementService;

    @Mock
    private SpotifyUrlUtil spotifyUrlUtil;

    @Mock
    private Credentials credentials;

    private Song testSong;

    @Before
    public void setup() throws IOException {
        testSong = JsonMapper.convertJsonToObject("/json/song/ValidSong.json", Song.class);
    }

    @Test
    public void testCreateValidSong() {

        Mockito.when(releaseValidator.validateReleaseSlug(Mockito.anyString())).thenReturn(testSong.getRelease());

        Mockito.doNothing().when(songValidator).validateSong(Mockito.any(Song.class));

        Mockito.when(genreValidator.validateGenreNameExists(Mockito.anyString())).thenReturn(testSong.getGenre());

        Mockito.when(artistValidator.validateArtistSlug(Mockito.anyString())).thenReturn(testSong.getArtists().get(0));

        Mockito.when(songRepository.save(Mockito.any(Song.class))).thenReturn(testSong);

        Song returnTestSong = songManagementService.createSong("test-release", testSong);

        Assert.assertNotNull("Song should not be null", returnTestSong);

        Assert.assertEquals("The incorrect song was returned", testSong, returnTestSong);

        Assert.assertEquals("The song slug is not correct", "test-song", returnTestSong.getSlug());

        Assert.assertNotNull("Song release should not be null", returnTestSong.getRelease());

        Assert.assertNotNull("Song artists should not be null", returnTestSong.getArtists());

        Assert.assertEquals("Song should have 1 artist", 1, returnTestSong.getArtists().size());

        Assert.assertNotNull("Song genre should not be null", returnTestSong.getGenre());
    }
}
