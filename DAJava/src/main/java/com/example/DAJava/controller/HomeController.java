package com.example.DAJava.controller;

import com.example.DAJava.service.SongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private SongsService songService;
    @GetMapping("/trang1")
    public String Index(Model model) {
        model.addAttribute("songs", songService.getAllSongs());
        return "/home/index";
    }
    @GetMapping("/trang2")
    public String Home(Model model) {
        model.addAttribute("songs", songService.getAllSongs());
        return "/home/home1";
    }
}
