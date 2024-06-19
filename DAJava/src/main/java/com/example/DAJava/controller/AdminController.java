package com.example.DAJava.controller;

import com.example.DAJava.model.Artists;
import com.example.DAJava.model.Genres;
import com.example.DAJava.model.Songs;
import com.example.DAJava.model.Users;
import com.example.DAJava.service.*;
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
    @Autowired
    private AlbumsService albumsService;
//    @Autowired
//    private GenresService genresService;
//    @Autowired
//    private Albums albumsService;
    @GetMapping("/songlist")
    public String showSongList(Model model, String name) {
        model.addAttribute("songs", songService.getAllSongs());
        return "/admin/songs/song-list";
    }
    @GetMapping("/songlist/add")
    public String showAddForm(Model model) {
        Songs song = new Songs();
        song.setReleaseDate(new Date()); // Đặt ngày hiện tại
        model.addAttribute("genres", genresService.getAllGenres());
        model.addAttribute("albums", albumsService.getAllAlbums());
        return "/admin/songs/add-song";
    }
    // Process the form for adding a new product
    @PostMapping("/songlist/add")
    public String addSong(@Valid Songs songs, BindingResult result,Model model) {
        if (result.hasErrors()) {
            Songs song = new Songs();
            song.setReleaseDate(new Date()); // Đặt ngày hiện tại
            model.addAttribute("songs", song);
            model.addAttribute("genres", genresService.getAllGenres());
            model.addAttribute("albums", albumsService.getAllAlbums());
            return "/admin/songs/add-song";
        }
        if (songs.getReleaseDate() == null) {
//             Set current date as releaseDate
            songs.setReleaseDate(Calendar.getInstance().getTime());
        }
        songService.addSong(songs);
        return "redirect:/songlist";
    }
    @GetMapping("/songlist/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Songs song = songService.getSongsById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("songs", song);
        model.addAttribute("genres", genresService.getAllGenres());
        model.addAttribute("albums", albumsService.getAllAlbums());
        return "/admin/songs/update-song";
    }
    // Process the form for updating a product
    @PostMapping("/songlist/edit/{id}")
    public String updateProduct(@PathVariable Long id, @Valid Songs song,
                                BindingResult result,
                                @RequestParam("avatarFiles") List<MultipartFile> avatarFiles, Model model) {
//        if (result.hasErrors()) {
//            song.setSongId(id);
//            model.addAttribute("genres", genresService.getAllGenres());
//            model.addAttribute("albums", albumsService.getAllAlbums());
//            // keep the id in the form in case of errors
//            return "/admin/songs/update-song";
//        }
//
//        String uploadDir = "src/main/resources/static/images/";
//        try {
//            Path uploadPath = Paths.get(uploadDir);
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//            for (MultipartFile avatarFile : avatarFiles) {
//                if (!avatarFile.isEmpty()) {
//                    String fileName = StringUtils.cleanPath(avatarFile.getOriginalFilename());
//                    product.addAvatar(fileName);  // Assuming you have a method to add filenames to the product
//
//                    try (InputStream inputStream = avatarFile.getInputStream()) {
//                        Path filePath = uploadPath.resolve(fileName);
//                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//                    } catch (IOException e) {
//                        throw new RuntimeException("Could not save avatar image: " + e.getMessage());
//                    }
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Could not create upload directory: " + e.getMessage());
//        }
//
//        productService.updateProduct(product);
        return "redirect:/products";
    }
    // Handle request to delete a product
    @GetMapping("/songlist/delete/{id}")
    public String deleteSong(@PathVariable Long id) {
        songService.deleteSongById(id);
        return "redirect:/songlist";
    }
    @Autowired
    private UserService userService;
    //Quản lý user
    @GetMapping("users")
    public String listUsers(Model model) {
        model.addAttribute("usersList", userService.getAllUsers());
        return "admin/users/list";
    }

    @GetMapping("users/lock/{username}")
    public String lockUser(@PathVariable String username) {
        userService.lockUser(username);
        return "redirect:/admin/users";
    }

    @GetMapping("users/unlock/{username}")
    public String unlockUser(@PathVariable String username) {
        userService.unlockUser(username);
        return "redirect:/admin/users";
    }

    @GetMapping("users/reset-password-form/{username}")
    public String showResetPasswordForm(@PathVariable String username, Model model) {
        model.addAttribute("user", userService.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found")));
        return "admin/users/reset-password-form";
    }

    @PostMapping("users/reset-password/{username}")
    public String resetPassword(@PathVariable String username, @RequestParam("newPassword") String newPassword) {
        userService.resetPassword(username, newPassword);
        return "redirect:/admin/users";
    }

    @GetMapping("users/edit/{id}")
    public String showEditUserForm(@PathVariable String username, Model model) {
        Users user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        model.addAttribute("user", user);
        return "admin/users/edit-user-form";
    }

    @PostMapping("users/edit/{id}")
    public String updateUser(@PathVariable String username, @ModelAttribute Users updatedUser) {
        userService.updateUser(username, updatedUser);
        return "redirect:/admin/users";
    }

}
