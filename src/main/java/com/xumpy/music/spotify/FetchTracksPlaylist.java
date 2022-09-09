package com.xumpy.music.spotify;

import com.xumpy.music.spotify.model.SpotifySong;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FetchTracksPlaylist {
    @Autowired
    private RestTemplate restTemplate;

    private String bearerToken;
    private static final String URL_BASE = "https://api.spotify.com/v1/playlists/";

    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + this.bearerToken);

        return headers;

    }

    private List<SpotifySong> responseToSpotifyList(List<Map> responses){
        List<SpotifySong> spotifySongs = new ArrayList<>();

        for(Map response: responses){
            List<Map> items = (List<Map>) response.get("items");
            for(Map item: items){
                Map track = (Map) item.get("track");

                List<Map> artists = (List<Map>) track.get("artists");
                SpotifySong spotifySong = new SpotifySong();
                spotifySong.setArtist(artists.get(0).get("name").toString());
                spotifySong.setTitle(track.get("name").toString());

                spotifySongs.add(spotifySong);
            }
        }

        return spotifySongs;
    }

    private Boolean hasNext(Map result){
        try {
            Integer nextOffset = nextOffset(result);
            if (nextOffset != null){
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private Integer nextOffset(Map result) throws Exception {
        List<NameValuePair> params = URLEncodedUtils.parse(new URI(result.get("next").toString()), Charset.forName("UTF-8"));

        for(NameValuePair param: params){
            if (param.getName().equals("offset")) return Integer.parseInt(param.getValue());
        }
        throw new RuntimeException("Offset not found in next url");
    }

    private Map executeSpotifyCall(String playlistId, Integer offset){
        Integer limit = 100;
        String url = URL_BASE + playlistId + "/tracks?offset=" + offset + "&limit=" + limit;

        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, getHeaders()), Map.class).getBody();
    }

    public List<SpotifySong> fetch(String bearerToken, String playlistId){
        this.bearerToken = bearerToken;

        List<Map> spotifyCalls = new ArrayList<>();
        Map spotifyCall = executeSpotifyCall(playlistId, 0);
        spotifyCalls.add(spotifyCall);

        while(hasNext(spotifyCall)){
            Integer nextOffset;
            try{ nextOffset = nextOffset(spotifyCall); } catch (Exception ex){ nextOffset = null;}
            if (nextOffset != null){
                spotifyCall = executeSpotifyCall(playlistId, nextOffset);
                spotifyCalls.add(spotifyCall);
            } else {
                break;
            }
        }

        return responseToSpotifyList(spotifyCalls);
    }
}
