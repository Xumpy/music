package com.xumpy.music.spotify;

import com.xumpy.music.model.Song;
import com.xumpy.music.youtube.SongsProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations= "classpath:application.properties")
public class FetchTracksPlaylistTest {
    private String playlistId = "0vuV6TZpYtpleNx7WZDK42";

    @Autowired OAuth2BearerToken oAuth2BearerToken;
    @Autowired FetchTracksPlaylist fetchTracksPlaylist;
    @Autowired SongsProcessor songsProcessor;

    @Test
    public void testFetchPlaylist(){
        String token = oAuth2BearerToken.fetchToken();
        List<? extends Song> songs = fetchTracksPlaylist.fetch(token, playlistId);
        songsProcessor.process(songs);
    }

}