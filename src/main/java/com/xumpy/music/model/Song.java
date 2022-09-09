package com.xumpy.music.model;

public abstract class Song {
    public abstract String getArtist();
    public  abstract String getTitle();

    @Override
    public String toString(){
        return getArtist() + " - " + getTitle();
    }
}
