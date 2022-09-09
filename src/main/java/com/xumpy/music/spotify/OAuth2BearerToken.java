package com.xumpy.music.spotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OAuth2BearerToken {

    @Value("${spotify.client_id}")
    private String clientId;

    @Value("${spotify.client_secret}")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;

    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/x-www-form-urlencoded");

        return headers;

    }

    public String fetchToken(){
        String url = "https://accounts.spotify.com/api/token";

        String body = "client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&grant_type=client_credentials";

        Map result = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, getHeaders()), Map.class).getBody();

        return result.get("access_token").toString();
    }
}
