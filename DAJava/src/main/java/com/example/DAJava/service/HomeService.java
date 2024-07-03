package com.example.DAJava.service;

import com.example.DAJava.model.Albums;
import com.example.DAJava.model.Artists;
import com.example.DAJava.model.Songs;
import com.example.DAJava.repository.AlbumsRepository;
import com.example.DAJava.repository.ArtistsRepository;
import com.example.DAJava.repository.SongsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    @Autowired
    private SongsRepository songsRepository;

    @Autowired
    private AlbumsRepository albumsRepository;

    @Autowired
    private ArtistsRepository artistsRepository;

    public List<Songs> getRandomSongs(int limit) {
        // Lấy danh sách bài hát ngẫu nhiên
        return songsRepository.findRandomSongs(limit);
    }

    public List<Albums> getRandomAlbums(int limit) {
        // Lấy danh sách album ngẫu nhiên
        return albumsRepository.findRandomAlbums(limit);
    }

    public List<Artists> getRandomArtists(int limit) {
        // Lấy danh sách nghệ sĩ ngẫu nhiên
        return artistsRepository.findRandomArtists(limit);
    }
}
