package com.example.DAJava.controller;

import com.example.DAJava.model.Artists;
import com.example.DAJava.service.ArtistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/artists")
public class ArtistsController {

    @Autowired
    private ArtistsService artistsService;

    @GetMapping
    public String listArtists(Model model) {
        model.addAttribute("artistsList", artistsService.getAllArtists());
        return "artists/list";
    }

    @GetMapping("/add")
    public String showAddArtistForm(Model model) {
        model.addAttribute("artist", new Artists());
        return "artists/add";
    }

    @PostMapping("/add")
    public String addArtist(@ModelAttribute Artists artist) {
        artistsService.addArtist(artist);
        return "redirect:/artists";
    }

    @GetMapping("/edit/{id}")
    public String showEditArtistForm(@PathVariable Long id, Model model) {
        Artists artist = artistsService.getArtistById(id);
        model.addAttribute("artist", artist);
        return "artists/edit";
    }

    @PostMapping("/edit/{id}")
    public String editArtist(@PathVariable Long id, @ModelAttribute Artists artist) {
        artist.setArtistId(id);
        artistsService.updateArtist(artist);
        return "redirect:/artists";
    }

    @GetMapping("/delete/{id}")
    public String deleteArtist(@PathVariable Long id) {
        artistsService.deleteArtist(id);
        return "redirect:/artists";
    }
}
