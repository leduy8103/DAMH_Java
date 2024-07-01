package com.example.DAJava.repository;

import com.example.DAJava.model.PlaylistDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistDetailRepository extends JpaRepository<PlaylistDetails, Long> {
    List<PlaylistDetails> findByPlaylists_PlaylistId(Long playlistId);

    Optional<PlaylistDetails> findByPlaylists_PlaylistIdAndSong_SongId(Long playlistId, Long songId);
}
