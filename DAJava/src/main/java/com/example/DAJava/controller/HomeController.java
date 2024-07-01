package com.example.DAJava.controller;

import com.example.DAJava.model.Songs;
import com.example.DAJava.service.SongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private SongsService songsService;

    @GetMapping("/trang1")
    public String Index(Model model) {
        model.addAttribute("songs", songsService.getAllSongs());
        return "/home/index";
    }
    @GetMapping("/trang2")
    public String Home(Model model) {
        model.addAttribute("songs", songsService.getAllSongs());
        return "/home/home1";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Songs> searchSongs(@RequestParam("query") String query) {
        return songsService.searchSongsByName(query);
    }
}
