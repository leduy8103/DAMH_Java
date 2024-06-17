package com.example.DAJava.repository;

import com.example.DAJava.model.Songs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongsRepository extends JpaRepository<Songs, Long> {
}
