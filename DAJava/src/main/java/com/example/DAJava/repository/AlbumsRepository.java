package com.example.DAJava.repository;

import com.example.DAJava.model.Albums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumsRepository extends JpaRepository<Albums, Long> {
}
