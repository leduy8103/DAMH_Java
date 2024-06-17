package com.example.DAJava.service;

import com.example.DAJava.model.Comments;
import com.example.DAJava.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

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
}
