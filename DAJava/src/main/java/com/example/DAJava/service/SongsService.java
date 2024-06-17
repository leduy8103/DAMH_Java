package com.example.DAJava.service;

import com.example.DAJava.model.Songs;
import com.example.DAJava.repository.SongsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongsService {

    @Autowired
    private SongsRepository songsRepository;

    public List<Songs> getAllSongs() {
        return songsRepository.findAll();
    }

    public Optional<Songs> getSongById(Long id) {
        return songsRepository.findById(id);
    }

    public Songs saveSong(Songs song) {
        return songsRepository.save(song);
    }

    public void deleteSong(Long id) {
        songsRepository.deleteById(id);
    }
}
