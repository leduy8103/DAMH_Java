package com.example.DAJava.repository;

import com.example.DAJava.model.Artists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistsRepository extends JpaRepository<Artists, Long> {
    @Query(value = "SELECT * FROM artists ORDER BY RAND() LIMIT ?1", nativeQuery = true)
    List<Artists> findRandomArtists(int limit);
}
