package com.example.DAJava.service;

import com.example.DAJava.model.Genres;
import com.example.DAJava.repository.GenresRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenresService {

    @Autowired
    private GenresRepository genresRepository;

    public List<Genres> getAllGenres() {
        return genresRepository.findAll();
    }

    public Optional<Genres> getGenreById(Long id) {
        return genresRepository.findById(id);
    }

    public Genres saveGenre(Genres genre) {
        return genresRepository.save(genre);
    }

    public void deleteGenre(Long id) {
        genresRepository.deleteById(id);
    }
    public Genres updateGenre(@NotNull Genres genre) {
        Genres existingGenre = genresRepository.findById(genre.getGenreId())
                .orElseThrow(() -> new IllegalStateException("Genre with ID " +
                        genre.getGenreId() + " does not exist."));
        existingGenre.setName(genre.getName());
        existingGenre.setDescription(genre.getDescription());
        return genresRepository.save(existingGenre);
    }
}