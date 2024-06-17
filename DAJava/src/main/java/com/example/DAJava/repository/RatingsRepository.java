package com.example.DAJava.repository;

import com.example.DAJava.model.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Long> {
}
