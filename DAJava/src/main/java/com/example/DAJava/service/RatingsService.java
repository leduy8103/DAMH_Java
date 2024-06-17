package com.example.DAJava.service;

import com.example.DAJava.model.Ratings;
import com.example.DAJava.repository.RatingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingsService {

    @Autowired
    private RatingsRepository ratingsRepository;

    public List<Ratings> getAllRatings() {
        return ratingsRepository.findAll();
    }

    public Optional<Ratings> getRatingById(Long id) {
        return ratingsRepository.findById(id);
    }

    public Ratings saveRating(Ratings rating) {
        return ratingsRepository.save(rating);
    }

    public void deleteRating(Long id) {
        ratingsRepository.deleteById(id);
    }
}
