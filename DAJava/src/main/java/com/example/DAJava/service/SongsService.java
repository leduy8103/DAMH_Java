package com.example.DAJava.service;

import com.example.DAJava.model.Songs;
import com.example.DAJava.repository.SongsRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class SongsService {

    private final SongsRepository songsRepository;

    public List<Songs> getAllSongs() {
        return songsRepository.findAll();
    }

    public Optional<Songs> getSongsById(Long id) {
        return songsRepository.findById(id);
    }

    public Songs addSong(Songs song) {
        return songsRepository.save(song);
    }

    public Songs updateSong(@NotNull Songs song) {
        Songs existingSong = songsRepository.findById(song.getSongId())
                .orElseThrow(() -> new IllegalStateException("Song with ID " + song.getSongId() + " does not exist."));
        existingSong.setTitle(song.getTitle());
        existingSong.setLyric(song.getLyric());
        existingSong.setFilePath(song.getFilePath());
        existingSong.setImagePath(song.getImagePath());
        existingSong.setGenre(song.getGenre());
        return songsRepository.save(existingSong);
    }

    public List<Songs> searchSongsByName(String name) {
        return songsRepository.findByTitleContainingIgnoreCase(name);
    }

    public void deleteSongById(Long id) {
        if (!songsRepository.existsById(id)) {
            throw new IllegalStateException("Song with ID " + id + " does not exist.");
        }
        songsRepository.deleteById(id);
    }

    public List<Songs> findAllSongsByAlbumId(Long albumId) {
        return songsRepository.findByAlbumAlbumId(albumId);
    }

//    public List<Songs> findAllSongsByArtistId(Long artistId) {
//        return songsRepository.findByArtistArtistId(artistId);
//    }
}
