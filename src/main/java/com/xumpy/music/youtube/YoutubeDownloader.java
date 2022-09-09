package com.xumpy.music.youtube;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;

@Service
public class YoutubeDownloader {
    @Value("${youtube.dl.server}")
    private String youtubeDlServer;

    @Autowired
    private RestTemplate restTemplate;

    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/x-www-form-urlencoded");

        return headers;

    }

    public void downloadYoutube(String youtubeUrl){
        String url = youtubeDlServer + "/download";
        String body = "url=" + youtubeUrl;

        try{
            restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, getHeaders()), Object.class);
        } catch (UnknownContentTypeException ex){
            // content type is responsibility of the youtube dl docker
        }
    }
}
