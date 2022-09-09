package com.xumpy.music.dao.model;

import com.xumpy.music.dao.implementations.SongDaoImpl;
import com.xumpy.music.model.Song;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="SPOTIFY_YOUTUBE")
public class SongDao extends Song {

    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;

    @Column(name="ARTIST")
    private String artist;

    @Column(name="TITLE")
    private String title;

    @Column(name="YOUTUBE_URL")
    private String youtubeUrl;

    @Column(name="PROCESSED")
    private Boolean processed;

    @Override
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public SongDao(){}

    public SongDao(SongDaoImpl songDaoImpl, Song song){
        List<SongDao> songsDao = songDaoImpl.selectFromArtistAndTitle(song.getArtist(), song.getTitle());
        if (songsDao.isEmpty()){
            this.setArtist(song.getArtist());
            this.setTitle(song.getTitle());
            this.processed = false;
        } else {
            this.pkId = songsDao.get(0).getPkId();
            this.artist = songsDao.get(0).getArtist();
            this.title = songsDao.get(0).getTitle();
            this.youtubeUrl = songsDao.get(0).getYoutubeUrl();
            this.processed = songsDao.get(0).getProcessed();
        }
    }
}
