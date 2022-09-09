package com.xumpy.music.youtube;

import com.xumpy.music.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class YoutubeSearchEngine {
    @Value("${youtube.api_key}")
    private String apiKye;

    @Autowired
    private RestTemplate restTemplate;

    private String createUrl(Song song){
        String url = "https://content.googleapis.com/youtube/v3/search?part=snippet&order=relevance&q="
                + song.getArtist() + " - "
                + song.getTitle() + " Official&type=video&key=" + apiKye;
        return url;
    }

    public String searchYoutube(Song song) throws HttpClientErrorException {
        String url = createUrl(song);
        Map response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, null), Map.class).getBody();
        List items = (List) response.get("items");

        if (items != null && !items.isEmpty()){
            Map item = (Map) items.get(0);
            Map id = (Map) item.get("id");
            String videoId = id.get("videoId").toString();

            return "https://www.youtube.com/watch?v=" + videoId;
        }

        return null;
    }
}
