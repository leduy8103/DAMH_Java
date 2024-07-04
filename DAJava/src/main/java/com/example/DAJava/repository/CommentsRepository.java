package com.example.DAJava.repository;

import com.example.DAJava.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findBySongSongId(Long songId);
    List<Comments> findByDateBetween(Date starDate, Date endDate);
}