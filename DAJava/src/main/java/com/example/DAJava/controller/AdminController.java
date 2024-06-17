package com.example.DAJava.controller;

import com.example.DAJava.model.Songs;
import com.example.DAJava.service.SongsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private SongsService songService;
//    @Autowired
//    private GenresService genresService;
//    @Autowired
//    private Albums albumsService;
    @GetMapping("/songlist")
    public String showSongList(Model model, String name) {
        model.addAttribute("songs", songService.getAllSongs());
//        model.addAttribute("genres" genresService.getAllGenres);
//        model.addAttribute("albums" albumsService.getAllAlbums);
        return "/admin/songs/song-list";
    }
    @GetMapping("/songlist/add")
    public String showAddForm(Model model) {
        Songs song = new Songs();
        song.setReleaseDate(new Date()); // Đặt ngày hiện tại
        model.addAttribute("songs", song);
//    model.addAttribute("genres", genresService.getAllGenres());
//    model.addAttribute("albums", albumsService.getAllAlbums());
        return "/admin/songs/add-song";
    }
    // Process the form for adding a new product
    @PostMapping("/songlist/add")
    public String addSong(@Valid Songs songs, BindingResult result) {
        if (result.hasErrors()) {
            return "/admin/songs/add-song";
        }
        if (songs.getReleaseDate() == null) {
            // Set current date as releaseDate
            songs.setReleaseDate(Calendar.getInstance().getTime());
        }
        songService.addSong(songs);
        return "redirect:/songlist";
    }
}
