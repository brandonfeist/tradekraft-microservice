package com.tradekraftcollective.microservice.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.persistence.entity.media.Image;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Data
public class ReleaseBean {

    public ReleaseBean() {}

    public ReleaseBean(Release release) {
        this.id = release.getId();
        this.name = release.getName();
        this.image = release.getImage();
        this.description = release.getDescription();
        this.releaseType = release.getReleaseType();
        this.releaseDate = release.getReleaseDate();
        this.soundcloud = release.getSoundcloud();
        this.spotify = release.getSpotify();
        this.itunes = release.getItunes();
        this.appleMusic = release.getAppleMusic();
        this.googlePlay = release.getGooglePlay();
        this.amazon = release.getAmazon();
        this.songs = convertSongsToSongBeans(release.getSongs());
        this.freeRelease = release.isFreeRelease();
        this.slug = release.getSlug();
        this.createdAt = release.getCreatedAt();
        this.updatedAt = release.getUpdatedAt();
    }

    private Long id;

    private String name;

    private Image image;

    private String description;

    private String releaseType;

    private Date releaseDate;

    private String soundcloud;

    private String spotify;

    private String itunes;

    private String appleMusic;

    private String googlePlay;

    private String amazon;

    @JsonIgnoreProperties("release")
    private List<SongBean> songs;

    private boolean freeRelease;

    private String slug;

    private Date createdAt;

    private Date updatedAt;

    public Release convertToRelease() {
        Release release = new Release();

        release.setId(this.id);
        release.setName(this.name);
        release.setImage(this.image);
        release.setDescription(this.description);
        release.setReleaseType(this.releaseType);
        release.setReleaseDate(this.releaseDate);
        release.setSoundcloud(this.soundcloud);
        release.setSpotify(this.spotify);
        release.setItunes(this.itunes);
        release.setAppleMusic(this.appleMusic);
        release.setGooglePlay(this.googlePlay);
        release.setAmazon(this.amazon);
        release.setSongs(convertSongBeansToSongs(this.songs));
        release.setFreeRelease(this.freeRelease);
        release.setSlug(this.slug);
        release.setCreatedAt(this.createdAt);
        release.setUpdatedAt(this.updatedAt);

        return release;
    }

    private List<SongBean> convertSongsToSongBeans(List<Song> songs) {
        List<SongBean> songBeans = null;

        if(songs.size() > 0) {
            songBeans = new ArrayList<>();

            for(Song song : songs) {
                songBeans.add(new SongBean(song));
            }
        }

        return songBeans;
    }

    private List<Song> convertSongBeansToSongs(List<SongBean> songBeans) {
        List<Song> songs = null;

        if(songBeans.size() > 0) {
            songs = new ArrayList<>();

            for(SongBean songBean : songBeans) {
                songs.add(songBean.convertToSong());
            }
        }

        return songs;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ReleaseBean)) {
            return false;
        }
        ReleaseBean releaseBean = (ReleaseBean) obj;
        return id == releaseBean.id &&
                Objects.equals(name, releaseBean.name) &&
                Objects.equals(description, releaseBean.description) &&
                Objects.equals(image, releaseBean.image) &&
                Objects.equals(releaseType, releaseBean.releaseType) &&
                Objects.equals(releaseDate, releaseBean.releaseDate) &&
                Objects.equals(soundcloud, releaseBean.soundcloud) &&
                Objects.equals(spotify, releaseBean.spotify) &&
                Objects.equals(itunes, releaseBean.itunes) &&
                Objects.equals(appleMusic, releaseBean.appleMusic) &&
                Objects.equals(googlePlay, releaseBean.googlePlay) &&
                Objects.equals(amazon, releaseBean.amazon) &&
                Objects.equals(slug, releaseBean.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, image, releaseType, releaseDate,
                soundcloud, spotify, itunes, appleMusic, googlePlay, appleMusic,
                slug);
    }
}
