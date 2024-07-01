package com.example.DAJava.repository;

import com.example.DAJava.model.Playlists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistsRepository extends JpaRepository<Playlists, Long> {
    List<Playlists> findByUser_username(String username);
}
