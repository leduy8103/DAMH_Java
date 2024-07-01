package com.example.DAJava.repository;

import com.example.DAJava.model.Songs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongsRepository extends JpaRepository<Songs, Long> {
    List<Songs> findByTitleContainingIgnoreCase(String title);
    List<Songs> findByAlbumAlbumId(Long albumId); // Chỉnh sửa tại đây
}
