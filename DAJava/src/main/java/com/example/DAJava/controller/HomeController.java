package com.example.DAJava.controller;

import com.example.DAJava.service.SongsService;
import com.example.DAJava.model.Songs;
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

    @GetMapping
    public String Index() {
        return "/home/index";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Songs> searchSongs(@RequestParam("query") String query) {
        return songsService.searchSongsByName(query);
    }
}
