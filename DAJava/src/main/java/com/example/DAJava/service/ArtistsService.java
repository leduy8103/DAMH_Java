package com.example.DAJava.service;

import com.example.DAJava.model.Artists;
import com.example.DAJava.repository.ArtistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Service
public class ArtistsService {

    @Autowired
    private ArtistsRepository artistsRepository;

    public List<Artists> getAllArtists() {
        return artistsRepository.findAll();
    }

    public Artists getArtistById(Long id) {
        return artistsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid artist Id:" + id));
    }

    public Artists addArtist(Artists artist) {
        return artistsRepository.save(artist);
    }

    public Artists updateArtist(@NotNull Artists artist) {
        Artists existingArtist = artistsRepository.findById(artist.getArtistId())
                .orElseThrow(() -> new IllegalStateException("Artist with ID " +
                        artist.getArtistId() + " does not exist."));
        existingArtist.setArtistName(artist.getArtistName());
        existingArtist.setAvatar(artist.getAvatar());
        existingArtist.setBio(artist.getBio());
        existingArtist.setCountry(artist.getCountry());
        return artistsRepository.save(existingArtist);
    }

    public void deleteArtist(Long id) {
        artistsRepository.deleteById(id);
    }
}
