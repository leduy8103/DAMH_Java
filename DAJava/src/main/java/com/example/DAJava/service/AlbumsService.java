package com.example.DAJava.service;

import com.example.DAJava.model.Albums;
import com.example.DAJava.repository.AlbumsRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlbumsService {

    private final AlbumsRepository albumsRepository;

    @Autowired
    public AlbumsService(AlbumsRepository albumsRepository) {
        this.albumsRepository = albumsRepository;
    }

    // Lấy danh sách tất cả các albums từ cơ sở dữ liệu
    public List<Albums> getAllAlbums() {
        return albumsRepository.findAll();
    }

    // Lấy một album dựa trên ID
    public Optional<Albums> getAlbumById(Long id) {
        return albumsRepository.findById(id);
    }

    // Thêm mới một album vào cơ sở dữ liệu
    public Albums addAlbum(@NotNull Albums album) {
        return albumsRepository.save(album);
    }

    // Cập nhật thông tin của một album
    public Albums updateAlbum(@NotNull Albums album) {
        Albums existingAlbum = albumsRepository.findById(album.getAlbumId())
                .orElseThrow(() -> new IllegalStateException("Album with ID " + album.getAlbumId() + " does not exist."));

        existingAlbum.setTitle(album.getTitle());
        existingAlbum.setReleaseDate(album.getReleaseDate());
        existingAlbum.setImagePath(album.getImagePath());
        // Các thuộc tính khác của album cần được cập nhật tương tự nếu có

        return albumsRepository.save(existingAlbum);
    }

    // Xóa một album từ cơ sở dữ liệu dựa trên ID
    public void deleteAlbumById(Long id) {
        albumsRepository.deleteById(id);
    }
}
