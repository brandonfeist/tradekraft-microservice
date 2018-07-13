package com.tradekraftcollective.microservice.controller;

import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.service.IArtistManagementService;
import com.tradekraftcollective.microservice.service.IImageManagementService;
import com.tradekraftcollective.microservice.util.JsonMapper;
import com.tradekraftcollective.microservice.util.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ArtistManagementController.class })
@WebAppConfiguration
@Slf4j
@EnableWebMvc
public class ArtistManagementControllerTest {

    private static final String ARTIST_URL = "/v1/artists";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    IArtistManagementService artistManagementService;

    @MockBean
    IImageManagementService imageManagementService;

    private Artist testArtist;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        testArtist = JsonMapper.convertJsonToObject("/json/artist/ValidArtist.json", Artist.class);
    }

    @Test
    public void testCreateNewValidArtistSuccess() throws Exception {
        String artistJson = ResourceUtil.convertResourceToString("/json/artist/ValidArtist.json");

        Mockito.when(artistManagementService.createArtist(Mockito.any(Artist.class))).thenReturn(testArtist);

        mockMvc.perform(
                post("/v1/artists")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(artistJson)
        ).andExpect(status().isCreated());
    }

    @Test
    public void testCreateNewInvalidArtistJsonBadRequest() throws Exception {
        String artistJson = "{ \"ame\": \"Test Artist\", \"invalidVar\": 42 ";

        mockMvc.perform(
            post(ARTIST_URL)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(artistJson)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testGetArtistSuccess() throws Exception {
        Mockito.when(artistManagementService.getArtist(Mockito.anyString())).thenReturn(testArtist);

        mockMvc.perform(
            get(ARTIST_URL + "/test-artist")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testGetArtistsSuccess() throws Exception {
        mockMvc.perform(
            get(ARTIST_URL)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testDeleteArtistSuccess() throws Exception {
        Mockito.doNothing().when(artistManagementService).deleteArtist(Mockito.anyString());

        mockMvc.perform(
            delete(ARTIST_URL + "/test-artist")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }
}
