package com.example.DAJava.controller;

import com.example.DAJava.model.Albums;
import com.example.DAJava.model.Comments;
import com.example.DAJava.model.Ratings;
import com.example.DAJava.model.Songs;
import com.example.DAJava.service.AlbumsService;
import com.example.DAJava.service.CommentsService;
import com.example.DAJava.service.RatingsService;
import com.example.DAJava.service.SongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private SongsService songsService;

    @Autowired
    private AlbumsService albumsService;

    @Autowired
    private CommentsService commentService;

    @Autowired
    private RatingsService ratingService;

    @GetMapping("/albumList")
    public String albumList(Model model) {
        List<Albums> albums = albumsService.getAllAlbums();
        model.addAttribute("albums", albums);
        return "/home/albumList"; // Đảm bảo trả về "home/index"
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Songs> searchSongs(@RequestParam("query") String query) {
        return songsService.searchSongsByName(query);
    }

    @GetMapping("/albums/{id}")
    public String getAlbumById(@PathVariable("id") Long id, Model model) {
        Optional<Albums> albumOpt = albumsService.getAlbumById(id);
        if (albumOpt.isPresent()) {
            Albums album = albumOpt.get();
            List<Songs> songs = songsService.findAllSongsByAlbumId(id);
            model.addAttribute("album", album);
            model.addAttribute("songs", songs);
            return "home/albumDetail";
        } else {
            return "redirect:/home"; // Nếu không tìm thấy album, quay về trang chủ
        }
    }

    @GetMapping("/songs/{id}")
    public String getSongById(@PathVariable("id") Long id, Model model) {
        Optional<Songs> songOpt = songsService.getSongsById(id);
        if (songOpt.isPresent()) {
            Songs song = songOpt.get();
            List<Comments> comments = commentService.getCommentsBySongId(id);
            double averageRating = ratingService.getAverageRatingForSong(id);
            List<Ratings> ratings = ratingService.getRatingsBySongId(id); // Lấy danh sách các ratings
            model.addAttribute("song", song);
            model.addAttribute("comments", comments);
            model.addAttribute("averageRating", averageRating);
            model.addAttribute("ratings", ratings); // Đưa danh sách ratings vào model
            return "home/songDetail";
        } else {
            return "redirect:/home"; // Nếu không tìm thấy bài hát, quay về trang chủ
        }
    }

    @PostMapping("/rate")
    public String rateSong(@RequestParam Long songId, @RequestParam int ratingValue, Principal principal) {
        ratingService.rateSong(songId, ratingValue, principal.getName());
        return "redirect:/home/songs/" + songId; // Đảm bảo URL chuyển hướng đúng
    }

    @PostMapping("/comment")
    public String addComment(@RequestParam Long songId, @RequestParam String text, Principal principal) {
        commentService.addComment(songId, text, principal.getName());
        return "redirect:/home/songs/" + songId; // Đảm bảo URL chuyển hướng đúng
    }

    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam Long commentId, Principal principal) {
        commentService.deleteComment(commentId, principal.getName());
        return "redirect:/home/songs/" + commentId; // Đảm bảo URL chuyển hướng đúng
    }
}
