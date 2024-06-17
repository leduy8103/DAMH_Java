package com.example.DAJava.repository;

import com.example.DAJava.model.Playlists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistsRepository extends JpaRepository<Playlists, Long> {
}
