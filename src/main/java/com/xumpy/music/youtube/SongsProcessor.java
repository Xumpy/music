package com.xumpy.music.youtube;

import com.xumpy.music.dao.implementations.SongDaoImpl;
import com.xumpy.music.dao.model.SongDao;
import com.xumpy.music.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class SongsProcessor {
    @Autowired SongDaoImpl songDaoImpl;
    @Autowired YoutubeSearchEngine youtubeSearchEngine;
    @Autowired YoutubeDownloader youtubeDownloader;

    public void process(List<? extends Song> songs){
        for(Song song: songs){
            SongDao songDao = new SongDao(songDaoImpl, song);

            if (songDao.getYoutubeUrl() == null){
                try{
                    String youtubeUrl = youtubeSearchEngine.searchYoutube(song);
                    songDao.setYoutubeUrl(youtubeUrl);
                    songDaoImpl.save(songDao);
                } catch(HttpClientErrorException exception){
                    System.out.println("limit of youtube API recieved");
                    break;
                }
            }
            System.out.println("Processed: " + songDao.toString() + " (" + songDao.getPkId() + ")");
        }

        List<SongDao> notProcessedSongs = songDaoImpl.selectNotProcessed();
        for (SongDao songDao: notProcessedSongs){
            youtubeDownloader.downloadYoutube(songDao.getYoutubeUrl());

            songDao.setProcessed(true);
            songDaoImpl.save(songDao);

            System.out.println("Downloaded: " + songDao.toString() + " (" + songDao.getPkId() + ")");
        }
    }
}
