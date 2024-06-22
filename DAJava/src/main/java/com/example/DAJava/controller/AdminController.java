package com.example.DAJava.controller;

import com.example.DAJava.model.*;
import com.example.DAJava.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @GetMapping("/songlist")
    public String showSongList(Model model, String name) {
        model.addAttribute("songs", songService.getAllSongs());
        return "/admin/songs/song-list";
    }
    @GetMapping("/songlist/add")
    public String showAddForm(Model model) {
        Songs song = new Songs();
        song.setReleaseDate(new Date()); // Đặt ngày hiện tại
        model.addAttribute("song", song);
        model.addAttribute("genres", genresService.getAllGenres());
        model.addAttribute("albums", albumsService.getAllAlbums());
        return "/admin/songs/add-song";
    }

    // Process the form for adding a new product
    @PostMapping("/songlist/add")
    public String addSong(@Valid Songs songs,
                          BindingResult result,
                          Model model,
                          @RequestParam("filePath") MultipartFile file,
                          @RequestParam("imagePath") MultipartFile image,
                          RedirectAttributes redirectAttributes) {
        // Lưu file âm thanh
        if (!file.isEmpty()) {
            try {
                // Lưu tệp âm thanh vào thư mục src/main/resources/static/images/songAudio
                String audioDirectory = "src/main/resources/static/images/songAudio";
                String filePath = saveFile(file, audioDirectory);
                songs.setFilePath("/images/songAudio/" + file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                // Xử lý lỗi lưu file
            }
        }

        // Lưu file hình ảnh
        if (!image.isEmpty()) {
            try {
                // Lưu tệp hình ảnh vào thư mục src/main/resources/static/images/songImg
                String imageDirectory = "src/main/resources/static/images/songImg";
                String imagePath = saveFile(image, imageDirectory);
                songs.setImagePath("/images/songImg/" + image.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                // Xử lý lỗi lưu file
            }
        }

        if (songs.getReleaseDate() == null) {
            // Set current date as releaseDate
            songs.setReleaseDate(Calendar.getInstance().getTime());
        }
        songs.setDuration(0);
        songService.addSong(songs);
        return "redirect:/admin/songlist";
    }

    // Method to save file and return the path
    private String saveFile(MultipartFile file, String directory) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get(directory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName, e);
        }
    }

    @GetMapping("/songlist/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Songs song = songService.getSongsById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid song Id:" + id));
        model.addAttribute("song", song);
        model.addAttribute("genres", genresService.getAllGenres());
        model.addAttribute("albums", albumsService.getAllAlbums());
        return "/admin/songs/edit-song";
    }

    // Phương thức xử lý cập nhật bài hát
    @PostMapping("/songlist/edit/{id}")
    public String updateSong(@PathVariable Long id, @Valid Songs song,
                             BindingResult result, Model model,
                             @RequestParam(value = "audioFile", required = false) MultipartFile audioFile,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        if (result.hasErrors()) {
            model.addAttribute("genres", genresService.getAllGenres());
            model.addAttribute("albums", albumsService.getAllAlbums());
            return "/admin/songs/edit-song";
        }

        // Lưu file âm thanh mới hoặc giữ nguyên file cũ
        if (audioFile != null && !audioFile.isEmpty()) {
            String audioFileName = StringUtils.cleanPath(Objects.requireNonNull(audioFile.getOriginalFilename()));
            String audioUploadDir = "src/main/resources/static/images/songAudio/";
            try {
                Files.createDirectories(Paths.get(audioUploadDir));
                Path audioFilePath = Paths.get(audioUploadDir + audioFileName);
                Files.copy(audioFile.getInputStream(), audioFilePath, StandardCopyOption.REPLACE_EXISTING);
                song.setFilePath("/images/songAudio/" + audioFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Giữ nguyên file cũ nếu không có file mới được chọn
            Songs existingSong = songService.getSongsById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid song Id:" + id));
            song.setFilePath(existingSong.getFilePath());
        }

        // Lưu file ảnh mới hoặc giữ nguyên file cũ
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageFileName = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
            String imageUploadDir = "src/main/resources/static/images/songImg/";
            try {
                Files.createDirectories(Paths.get(imageUploadDir));
                Path imageFilePath = Paths.get(imageUploadDir + imageFileName);
                Files.copy(imageFile.getInputStream(), imageFilePath, StandardCopyOption.REPLACE_EXISTING);
                song.setImagePath("/images/songImg/" + imageFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Giữ nguyên file cũ nếu không có file mới được chọn
            Songs existingSong = songService.getSongsById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid song Id:" + id));
            song.setImagePath(existingSong.getImagePath());
        }

        song.setSongId(id);
        songService.updateSong(song);
        return "redirect:/admin/songlist";
    }
    // Handle request to delete a product
    @GetMapping("/songlist/delete/{id}")
    public String deleteSong(@PathVariable Long id) {
        songService.deleteSongById(id);
        return "redirect:/admin/songlist";
    }

//    Album
    @GetMapping("/albumlist")
    public String showAlbumList(Model model) {
        model.addAttribute("albums", albumsService.getAllAlbums());
        return "/admin/albums/list";
    }

    // Hiển thị form thêm mới album
    @GetMapping("/albumlist/add")
    public String showAddAlbumForm(Model model) {
        Albums album = new Albums();
        model.addAttribute("album", album);
        model.addAttribute("genres", genresService.getAllGenres());
        model.addAttribute("artists", artistsService.getAllArtists());
        return "/admin/albums/add";
    }

    // Xử lý thêm mới album
    @PostMapping("/albumlist/add")
    public String addAlbum(@Valid Albums album,
                           BindingResult result,
                           @RequestParam("imageFile") MultipartFile imageFile,
                           RedirectAttributes redirectAttributes) {
        // Xử lý lưu file hình ảnh
        if (!imageFile.isEmpty()) {
            try {
                String imageFileName = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
                String imageUploadDir = "src/main/resources/static/images/albumImg/";
                Files.createDirectories(Paths.get(imageUploadDir));
                Path imageFilePath = Paths.get(imageUploadDir + imageFileName);
                Files.copy(imageFile.getInputStream(), imageFilePath, StandardCopyOption.REPLACE_EXISTING);
                album.setImagePath("/images/albumImg/" + imageFileName);
            } catch (IOException e) {
                e.printStackTrace();
                // Xử lý lỗi lưu file
            }
        }
        if (album.getReleaseDate() == null) {
            // Set current date as releaseDate
            album.setReleaseDate(Calendar.getInstance().getTime());
        }
        albumsService.addAlbum(album);
        return "redirect:/admin/albumlist";
    }

    // Hiển thị form chỉnh sửa album
    @GetMapping("/albumlist/edit/{id}")
    public String showEditAlbumForm(@PathVariable Long id, Model model) {
        Albums album = albumsService.getAlbumById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + id));
        model.addAttribute("album", album);
        model.addAttribute("genres", genresService.getAllGenres());
        model.addAttribute("artists", artistsService.getAllArtists());
        return "/admin/albums/edit";
    }

    // Xử lý cập nhật album
    @PostMapping("/albumlist/edit/{id}")
    public String updateAlbum(@PathVariable Long id, @Valid Albums album,
                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        // Xử lý lưu file hình ảnh mới hoặc giữ nguyên file cũ
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imageFileName = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
                String imageUploadDir = "src/main/resources/static/images/albumImg/";
                Files.createDirectories(Paths.get(imageUploadDir));
                Path imageFilePath = Paths.get(imageUploadDir + imageFileName);
                Files.copy(imageFile.getInputStream(), imageFilePath, StandardCopyOption.REPLACE_EXISTING);
                album.setImagePath("/images/albumImg/" + imageFileName);
            } catch (IOException e) {
                e.printStackTrace();
                // Xử lý lỗi lưu file
            }
        } else {
            // Giữ nguyên file cũ nếu không có file mới được chọn
            Albums existingAlbum = albumsService.getAlbumById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + id));
            album.setImagePath(existingAlbum.getImagePath());
        }

        album.setAlbumId(id);
        albumsService.updateAlbum(album);
        return "redirect:/admin/albumlist";
    }

    // Xử lý yêu cầu xóa album
    @GetMapping("/albumlist/delete/{id}")
    public String deleteAlbum(@PathVariable Long id) {
        albumsService.deleteAlbumById(id);
        return "redirect:/admin/albumlist";
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
