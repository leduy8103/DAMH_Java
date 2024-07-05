package com.example.DAJava.controller;

import com.example.DAJava.model.*;
import com.example.DAJava.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private SongsService songsService;
    @Autowired
    private AlbumsService albumsService;
    @Autowired
    private PlaylistsService playlistsService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentsService commentService;
    @Autowired
    private RatingsService ratingService;

    @Autowired
    private HomeService homeService;
    @Autowired
    private ArtistsService artistsService; // Thêm ArtistsService

    @GetMapping
    public String index(Model model) {
        // Lấy ngẫu nhiên 4 bài hát, albums và artists
        List<Songs> randomSongs = homeService.getRandomSongs(4);
        List<Albums> randomAlbums = homeService.getRandomAlbums(4);
        List<Artists> randomArtists = homeService.getRandomArtists(4);

        // Đưa dữ liệu vào model để truyền cho template Thymeleaf
        model.addAttribute("randomSongs", randomSongs);
        model.addAttribute("randomAlbums", randomAlbums);
        model.addAttribute("randomArtists", randomArtists);

        return "/home/index"; // Thay thế "index" bằng tên template Thymeleaf của bạn
    }
    @GetMapping("/artistList")
    public String artistList(Model model) {
        List<Artists> artists = artistsService.getAllArtists();
        model.addAttribute("artists", artists);
        return "/home/artistList";
    }

//    @GetMapping("/artists/{id}")
//    public String getArtistById(@PathVariable("id") Long id, Model model) {
//        Artists artist = artistsService.getArtistById(id);
//        List<Songs> songs = songsService.findAllSongsByArtistId(id); // Đây là phương thức bạn cần thêm trong SongsService
//        model.addAttribute("artist", artist);
//        model.addAttribute("songs", songs);
//        return "home/artistDetail";
//    }

    @GetMapping("/albumList")
    public String albumList(Model model) {
        List<Albums> albums = albumsService.getAllAlbums();
        model.addAttribute("albums", albums);
        return "/home/albumList"; // Đảm bảo trả về "home/index"
    }

    @GetMapping("/search")
    public String searchSongs(@RequestParam String keyword, Model model,Principal principal) {
        List<Songs> results = songsService.searchSongsByName(keyword);
        model.addAttribute("searchResults", results);
        List<Playlists> playlists = playlistsService.getAllPlaylistByUser(principal.getName());
        model.addAttribute("playlists", playlists);
        return "/home/searchResult";
    }

    @GetMapping("/searchAutocomplete")
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
            return "redirect:/home";
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
    // Playlist
    @GetMapping("/playlistList")
    public String getAllPlaylist(Model model, Principal principal) {
        List<Playlists> myPlaylists = playlistsService.getAllPlaylistByUser(principal.getName());
        model.addAttribute("myPlaylists", myPlaylists);
        List<Playlists> otherPlaylists = playlistsService.getAllPlaylists().stream().filter(playlist -> !playlist.getUser().getUsername().equals(principal.getName()))
                .collect(Collectors.toList());
        model.addAttribute("otherPlaylists",otherPlaylists);
        return "home/playlistList";
    }
    @GetMapping("/playlist/{playlistId}")
    public String getPlaylistById(@PathVariable("playlistId") Long playlistId, Model model, Principal principal) {
        Optional<Playlists> playlistOpt = playlistsService.getPlaylistById(playlistId);
        if (playlistOpt.isPresent()) {
            Playlists playlist = playlistOpt.get();
            List<Songs> songs = playlistsService.getSongsInPlaylist(playlistId);
            model.addAttribute("playlist", playlist);
            List<Playlists> playlists = playlistsService.getAllPlaylistByUser(principal.getName());
            model.addAttribute("playlists", playlists);
            model.addAttribute("songs", songs);
            return "home/playlistDetail";
        } else {
            return "redirect:/home"; // Redirect to home if playlist not found
        }
    }

    @GetMapping("/playlist/addPlaylist")
    public String addPlaylist(@RequestParam String playlistName, Principal principal) {
        playlistsService.addPlaylist(playlistName, principal.getName());
        return "redirect:/home";
    }

    @GetMapping("/playlist/addToPlaylist")
    @ResponseBody
    public ResponseEntity<String> addToPlaylist(@RequestParam Long playlistId, @RequestParam Long songId) {
        boolean added = playlistsService.addSongToPlaylist(playlistId, songId);
        if (!added) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Song already exists in the playlist");
        }
        return ResponseEntity.ok("success");
    }

    @GetMapping("/playlist/deletePlaylist/{playlistId}")
    public String deletePlaylist(@PathVariable("playlistId") Long playlistId) {
        playlistsService.deletePlaylistDetailsByPlaylistId(playlistId); // Xoá các playlistDetail liên quan
        playlistsService.deletePlaylist(playlistId); // Xoá playlist
        return "redirect:/home";
    }

    @GetMapping("/playlist/removeFromPlaylist")
    public String removeFromPlaylist(@RequestParam Long playlistId, @RequestParam Long songId) {
        playlistsService.removeSongFromPlaylist(playlistId, songId);
        return "redirect:/home/playlist/" + playlistId;
    }
}
