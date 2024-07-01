package com.example.DAJava.service;

import com.example.DAJava.model.Ratings;
import com.example.DAJava.model.Songs;
import com.example.DAJava.model.Users;
import com.example.DAJava.repository.RatingsRepository;
import com.example.DAJava.repository.SongsRepository;
import com.example.DAJava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingsService {

    @Autowired
    private RatingsRepository ratingsRepository;

    @Autowired
    private SongsRepository songsRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Ratings> getAllRatings() {
        return ratingsRepository.findAll();
    }

    public Ratings saveRating(Ratings rating) {
        return ratingsRepository.save(rating);
    }

    public void deleteRating(Long id) {
        ratingsRepository.deleteById(id);
    }

    public void rateSong(Long songId, int ratingValue, String username) {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user");
        }

        Songs song = songsRepository.findById(songId).orElseThrow(() -> new IllegalArgumentException("Invalid song ID"));

        // Kiểm tra xem người dùng đã đánh giá bài hát này chưa
        Ratings existingRating = ratingsRepository.findByUserAndSong(user, song);
        if (existingRating != null) {
            // Nếu đã có đánh giá, cập nhật giá trị đánh giá
            existingRating.setRatingValue(ratingValue);
            ratingsRepository.save(existingRating);
        } else {
            // Nếu chưa có, tạo mới đánh giá
            Ratings rating = new Ratings();
            rating.setSong(song);
            rating.setUser(user);
            rating.setRatingValue(ratingValue);
            ratingsRepository.save(rating);
        }
    }

//    public double getAverageRatingForSong(Long songId) {
//        List<Ratings> ratings = ratingsRepository.findAllBySong_SongId(songId);
//        if (ratings.isEmpty()) {
//            return 0.0; // Trả về 0 nếu chưa có đánh giá nào
//        }
//
//        double sum = 0;
//        for (Ratings rating : ratings) {
//            sum += rating.getRatingValue();
//        }
//
//        return sum / ratings.size();
//    }
    public double getAverageRatingForSong(Long songId) {
        List<Ratings> ratings = ratingsRepository.findAllBySong_SongId(songId);
        if (ratings.isEmpty()) {
            return 0.0; // Trả về 0 nếu không có đánh giá
        }

        int totalRating = 0;
        for (Ratings rating : ratings) {
            totalRating += rating.getRatingValue();
        }

        return (double) totalRating / ratings.size();
    }
    public List<Ratings> getRatingsBySongId(Long songId) {
        return ratingsRepository.findAllBySong_SongId(songId);
    }
}
