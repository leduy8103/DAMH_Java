package com.example.DAJava.repository;

import com.example.DAJava.model.Artists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistsRepository extends JpaRepository<Artists, Long> {
}
