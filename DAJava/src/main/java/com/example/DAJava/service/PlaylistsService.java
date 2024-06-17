package com.example.DAJava.service;

import com.example.DAJava.model.Playlists;
import com.example.DAJava.repository.PlaylistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistsService {

    @Autowired
    private PlaylistsRepository playlistsRepository;

    public List<Playlists> getAllPlaylists() {
        return playlistsRepository.findAll();
    }

    public Optional<Playlists> getPlaylistById(Long id) {
        return playlistsRepository.findById(id);
    }

    public Playlists savePlaylist(Playlists playlist) {
        return playlistsRepository.save(playlist);
    }

    public void deletePlaylist(Long id) {
        playlistsRepository.deleteById(id);
    }
}
