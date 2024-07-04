package com.example.DAJava.repository;

import com.example.DAJava.model.Songs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongsRepository extends JpaRepository<Songs, Long> {
    List<Songs> findByTitleContainingIgnoreCase(String title);
    List<Songs> findByAlbumAlbumId(Long albumId); // Tìm theo AlbumId

    default List<Songs> findByArtistArtistId(Long artistId) // Thêm phương thức tìm theo ArtistId
    {
        return null;
    }

    @Query(value = "SELECT * FROM songs ORDER BY RAND() LIMIT ?1", nativeQuery = true)
    List<Songs> findRandomSongs(int limit);
}
