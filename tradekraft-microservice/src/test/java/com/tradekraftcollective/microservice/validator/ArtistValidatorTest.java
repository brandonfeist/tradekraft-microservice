package com.tradekraftcollective.microservice.validator;

import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
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
public class ArtistValidatorTest {

    @InjectMocks
    private ArtistValidator artistValidator;

    @Mock
    private IArtistRepository artistRepository;

    private Artist testArtist;

    @Before
    public void setup() throws IOException {
        testArtist = JsonMapper.convertJsonToObject("/json/artist/ValidArtist.json", Artist.class);
    }

    @Test
    public void testValidateValidArtistSlug() {
        Mockito.when(artistRepository.findBySlug(Mockito.anyString())).thenReturn(new Artist());

        testArtist = artistValidator.validateArtistSlug("test-artist");

        Assert.assertNotNull("An artist object must be returned", testArtist);
    }

    @Test(expected = ServiceException.class)
    public void testValidateInvalidArtistSlug() {
        Mockito.when(artistRepository.findBySlug(Mockito.anyString())).thenReturn(null);

        artistValidator.validateArtistSlug("test-artist");
    }

    @Test
    public void testValidateValidArtistLinks() {
        artistValidator.validateArtist(testArtist);
    }

    @Test(expected = ServiceException.class)
    public void testValidateBlankArtistSoundcloudLink() {
        testArtist.setSoundcloud(" ");

        artistValidator.validateArtist(testArtist);
    }

    @Test(expected = ServiceException.class)
    public void testValidateInvalidArtistSoundcloudLink() {
        testArtist.setSoundcloud("soundcloud.com/testuser");

        artistValidator.validateArtist(testArtist);
    }

    @Test(expected = ServiceException.class)
    public void testValidateBlankArtistFacebookLink() {
        testArtist.setFacebook(" ");

        artistValidator.validateArtist(testArtist);
    }

    @Test(expected = ServiceException.class)
    public void testValidateInvalidArtistFacebookLink() {
        testArtist.setFacebook("facebook.com/testuser");

        artistValidator.validateArtist(testArtist);
    }

    @Test(expected = ServiceException.class)
    public void testValidateBlankArtistTwitterLink() {
        testArtist.setTwitter(" ");

        artistValidator.validateArtist(testArtist);
    }

    @Test(expected = ServiceException.class)
    public void testValidateInvalidArtistTwitterLink() {
        testArtist.setFacebook("twitter.com/testuser");

        artistValidator.validateArtist(testArtist);
    }

    @Test(expected = ServiceException.class)
    public void testValidateBlankArtistInstagramLink() {
        testArtist.setInstagram(" ");

        artistValidator.validateArtist(testArtist);
    }

    @Test(expected = ServiceException.class)
    public void testValidateInvalidArtistInstagramLink() {
        testArtist.setInstagram("twitter.com/testuser");

        artistValidator.validateArtist(testArtist);
    }

    @Test(expected = ServiceException.class)
    public void testValidateBlankArtistSpotifyLink() {
        testArtist.setSpotify(" ");

        artistValidator.validateArtist(testArtist);
    }

    @Test(expected = ServiceException.class)
    public void testValidateInvalidArtistSpotifyLink() {
        testArtist.setSpotify("spotify.com/testuser");

        artistValidator.validateArtist(testArtist);
    }
}
