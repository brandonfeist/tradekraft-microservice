package com.tradekraftcollective.microservice.service.model;

import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.media.Image;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Data
public class SimpleArtist {

    @Autowired
    private static IArtistRepository artistRepository;

    private String name;

    private Image image;

    private String slug;

    public SimpleArtist(Artist artist) {
        this.name = artist.getName();

        this.image = artist.getImage();

        this.slug = artist.getSlug();
    }

    public static Artist convertToArtist(SimpleArtist simpleArtist) {
        Artist artist = artistRepository.findBySlug(simpleArtist.getSlug());

        if(artist == null) {
            log.error("Artist with slug {} does not exist.", simpleArtist.getSlug());

            throw new ServiceException(ErrorCode.INVALID_ARTIST_SLUG,
                    "Artist with slug " + simpleArtist.getSlug() + " does not exist.");
        }

        return artist;
    }
}
