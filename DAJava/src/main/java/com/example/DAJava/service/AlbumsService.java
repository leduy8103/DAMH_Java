package com.example.DAJava.service;

import com.example.DAJava.model.Albums;
import com.example.DAJava.repository.AlbumsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumsService {

    @Autowired
    private AlbumsRepository albumsRepository;

    public List<Albums> getAllAlbums() {
        return albumsRepository.findAll();
    }

    public Optional<Albums> getAlbumById(Long id) {
        return albumsRepository.findById(id);
    }

    public Albums saveAlbum(Albums album) {
        return albumsRepository.save(album);
    }

    public void deleteAlbum(Long id) {
        albumsRepository.deleteById(id);
    }
}
