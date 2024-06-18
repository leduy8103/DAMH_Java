package com.example.DAJava.controller;

import com.example.DAJava.model.Artists;
import com.example.DAJava.model.Genres;
import com.example.DAJava.model.Songs;
import com.example.DAJava.service.ArtistsService;
import com.example.DAJava.service.GenresService;
import com.example.DAJava.service.SongsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    // Quản lý chủ đề
    @Autowired
    private GenresService genresService;

    @GetMapping("/genres")
    public String getAllGenres(Model model) {
        List<Genres> genresList = genresService.getAllGenres();
        model.addAttribute("genresList", genresList);
        return "admin/genres/list";
    }

    @GetMapping("/genres/add")
    public String showAddGenreForm(Model model) {
        model.addAttribute("genre", new Genres());
        return "admin/genres/add";
    }

    @PostMapping("/genres/add")
    public String addGenre(@ModelAttribute Genres genre) {
        genresService.saveGenre(genre);
        return "redirect:/admin/genres";
    }

    @GetMapping("/genres/edit/{id}")
    public String showEditGenreForm(@PathVariable Long id, Model model) {
        Genres genre = genresService.getGenreById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre Id:" + id));
        model.addAttribute("genre", genre);
        return "admin/genres/edit";
    }

    @PostMapping("/genres/edit/{id}")
    public String editGenre(@PathVariable Long id, @ModelAttribute Genres genre) {
        genre.setGenreId(id);
        genresService.updateGenre(genre);
        return "redirect:/admin/genres";
    }

    @GetMapping("/genres/delete/{id}")
    public String deleteGenre(@PathVariable Long id) {
        genresService.deleteGenre(id);
        return "redirect:/admin/genres";
    }

    // Quản lý ca sĩ
    @Autowired
    private ArtistsService artistsService;

    @GetMapping("/artists")
    public String listArtists(Model model) {
        model.addAttribute("artistsList", artistsService.getAllArtists());
        return "admin/artists/list";
    }

    @GetMapping("/artists/add")
    public String showAddArtistForm(Model model) {
        model.addAttribute("artist", new Artists());
        return "admin/artists/add";
    }

    @PostMapping("/artists/add")
    public String addArtist(@ModelAttribute Artists artist) {
        artistsService.addArtist(artist);
        return "redirect:/admin/artists";
    }

    @GetMapping("/artists/edit/{id}")
    public String showEditArtistForm(@PathVariable Long id, Model model) {
        Artists artist = artistsService.getArtistById(id);
        model.addAttribute("artist", artist);
        return "admin/artists/edit";
    }

    @PostMapping("/artists/edit/{id}")
    public String editArtist(@PathVariable Long id, @ModelAttribute Artists artist) {
        artist.setArtistId(id);
        artistsService.updateArtist(artist);
        return "redirect:/admin/artists";
    }

    @GetMapping("/artists/delete/{id}")
    public String deleteArtist(@PathVariable Long id) {
        artistsService.deleteArtist(id);
        return "redirect:/admin/artists";
    }

    //Quản lý bài hát
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
