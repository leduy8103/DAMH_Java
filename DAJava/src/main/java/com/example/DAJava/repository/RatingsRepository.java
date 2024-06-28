package com.example.DAJava.repository;

import com.example.DAJava.model.Ratings;
import com.example.DAJava.model.Songs;
import com.example.DAJava.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Long> {
    Ratings findByUserAndSong(Users user, Songs song);
}
