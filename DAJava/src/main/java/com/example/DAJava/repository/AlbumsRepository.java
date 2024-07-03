package com.example.DAJava.repository;

import com.example.DAJava.model.Albums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumsRepository extends JpaRepository<Albums, Long> {
    @Query(value = "SELECT * FROM albums ORDER BY RAND() LIMIT ?1", nativeQuery = true)
    List<Albums> findRandomAlbums(int limit);
}
