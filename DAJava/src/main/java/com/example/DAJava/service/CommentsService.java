package com.example.DAJava.service;

import com.example.DAJava.model.Comments;
import com.example.DAJava.model.Songs;
import com.example.DAJava.model.Users;
import com.example.DAJava.repository.CommentsRepository;
import com.example.DAJava.repository.SongsRepository;
import com.example.DAJava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private SongsRepository songRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Comments> getAllComments() {
        return commentsRepository.findAll();
    }

    public Optional<Comments> getCommentById(Long id) {
        return commentsRepository.findById(id);
    }

    public Comments saveComment(Comments comment) {
        return commentsRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentsRepository.deleteById(id);
    }

    public List<Comments> getCommentsBySongId(Long songId) {
        return commentsRepository.findBySongSongId(songId);
    }

    public void addComment(Long songId, String text, String username) {
        Users user = userRepository.findByUsername(username);
        Songs song = songRepository.findById(songId).orElseThrow(() -> new IllegalArgumentException("Invalid song ID"));
        Comments comment = new Comments();
        comment.setSong(song);
        comment.setUser(user);
        comment.setText(text);
        comment.setDate(new Date());
        commentsRepository.save(comment);
    }

    public void deleteComment(Long commentId, String username) {
        Comments comment = commentsRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Invalid comment ID"));
        if (comment.getUser().getUsername().equals(username)) {
            commentsRepository.delete(comment);
        } else {
            throw new AccessDeniedException("You do not have permission to delete this comment");
        }
    }
    public List<Comments> getCommentsSince(Date startDate, Date endDate) {
        return commentsRepository.findByDateBetween(startDate, endDate);
    }
}
