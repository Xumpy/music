package com.xumpy.music.spotify.model;

import com.xumpy.music.model.Song;

public class SpotifySong extends Song {
    private String artist;
    private String title;

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
}
