package com.example.DAJava.repository;

import com.example.DAJava.model.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Long> {

    Ratings findByUserAndSong(com.example.DAJava.model.Users user, com.example.DAJava.model.Songs song);
    List<Ratings> findAllBySong_SongId(Long songId);
}
