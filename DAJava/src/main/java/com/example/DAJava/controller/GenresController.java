package com.example.DAJava.controller;

import com.example.DAJava.model.Genres;
import com.example.DAJava.service.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/genres")
public class GenresController {

    @Autowired
    private GenresService genresService;

    @GetMapping
    public String getAllGenres(Model model) {
        List<Genres> genresList = genresService.getAllGenres();
        model.addAttribute("genresList", genresList);
        return "genres/list";
    }

    @GetMapping("/add")
    public String showAddGenreForm(Model model) {
        model.addAttribute("genre", new Genres());
        return "genres/add";
    }

    @PostMapping("/add")
    public String addGenre(@ModelAttribute Genres genre) {
        genresService.saveGenre(genre);
        return "redirect:/genres";
    }

    @GetMapping("/edit/{id}")
    public String showEditGenreForm(@PathVariable Long id, Model model) {
        Genres genre = genresService.getGenreById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre Id:" + id));
        model.addAttribute("genre", genre);
        return "genres/edit";
    }

    @PostMapping("/edit/{id}")
    public String editGenre(@PathVariable Long id, @ModelAttribute Genres genre) {
        genre.setGenreId(id);
        genresService.updateGenre(genre);
        return "redirect:/genres";
    }

    @GetMapping("/delete/{id}")
    public String deleteGenre(@PathVariable Long id) {
        genresService.deleteGenre(id);
        return "redirect:/genres";
    }
}
