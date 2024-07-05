package com.example.DAJava.service;

import com.example.DAJava.model.*;
import com.example.DAJava.repository.PlaylistDetailRepository;
import com.example.DAJava.repository.PlaylistsRepository;
import com.example.DAJava.repository.SongsRepository;
import com.example.DAJava.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistsService {

    @Autowired
    private PlaylistsRepository playlistsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SongsRepository songsRepository;
    @Autowired
    private PlaylistDetailRepository playlistDetailRepository;
    public void addPlaylist(String playlistName, String username) {
        Users user = userRepository.findByUsername(username);
        Playlists playlists = new Playlists();
        playlists.setName(playlistName);
        playlists.setDescription("none");
        playlists.setUser(user);
        playlistsRepository.save(playlists);
    }
    public List<Playlists> getAllPlaylists() {
        return playlistsRepository.findAll();
    }

    public Optional<Playlists> getPlaylistById(Long id) {
        return playlistsRepository.findById(id);
    }

    public List<Playlists> getAllPlaylistByUser(String username){
        return playlistsRepository.findByUser_username(username);
    }

    public Playlists savePlaylist(Playlists playlist) {
        return playlistsRepository.save(playlist);
    }
    @Transactional
    public void deletePlaylistDetailsByPlaylistId(Long playlistId) {
        // Xoá các playlistDetail có liên quan đến playlistId
        List<PlaylistDetails> playlistDetails = playlistDetailRepository.findByPlaylists_PlaylistId(playlistId);
        for (PlaylistDetails playlistDetail : playlistDetails) {
            playlistDetailRepository.delete(playlistDetail);
        }
    }
    @Transactional
    public void deletePlaylist(Long id) {
        playlistsRepository.deleteById(id);
    }

    public boolean isSongInPlaylist(Long playlistId, Long songId) {
        return playlistDetailRepository.existsByPlaylists_PlaylistIdAndSong_SongId(playlistId, songId);
    }

    public boolean addSongToPlaylist(Long playlistId, Long songId) {
        if (isSongInPlaylist(playlistId, songId)) {
            return false; // Song already exists in the playlist
        }
        PlaylistDetails playlistDetail = new PlaylistDetails();
        playlistDetail.setPlaylists(playlistsRepository.findById(playlistId).orElseThrow(() -> new IllegalArgumentException("Invalid playlist ID")));
        playlistDetail.setSong(songsRepository.findById(songId).orElseThrow(() -> new IllegalArgumentException("Invalid song ID")));
        playlistDetailRepository.save(playlistDetail);
        return true; // Song added successfully
    }

    public List<Songs> getSongsInPlaylist(Long playlistId) {
        List<PlaylistDetails> playlistDetails = playlistDetailRepository.findByPlaylists_PlaylistId(playlistId);
        return playlistDetails.stream().map(PlaylistDetails::getSong).collect(Collectors.toList());
    }
    public void removeSongFromPlaylist(Long playlistId, Long songId) {
        Optional<PlaylistDetails> playlistDetails = playlistDetailRepository.findByPlaylists_PlaylistIdAndSong_SongId(playlistId, songId);
        if (playlistDetails.isPresent()) {
            playlistDetailRepository.deleteById(playlistDetails.get().getPlaylistDetailId());
        } else {
            throw new IllegalArgumentException("Song not found in playlist");
        }
    }

}
