package com.xumpy.music.dao.implementations;

import com.xumpy.music.dao.model.SongDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongDaoImpl extends CrudRepository<SongDao, Integer>{
    @Query("from SongDao where artist = :artist and title = :title")
    public List<SongDao> selectFromArtistAndTitle(@Param("artist") String artist, @Param("title") String title);

    @Query("from SongDao where youtubeUrl is not null and processed = false")
    public List<SongDao> selectNotProcessed();
}
