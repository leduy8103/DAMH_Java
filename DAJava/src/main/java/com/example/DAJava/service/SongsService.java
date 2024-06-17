package com.example.DAJava.service;
import com.example.DAJava.model.Songs;
import com.example.DAJava.repository.SongsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SongsService {
    public List<Songs> getAllSongs() {
        return songsRepository.findAll();
    }
        return songsRepository.findById(id);
    }
        return songsRepository.save(song);
    }
        songsRepository.deleteById(id);
    }
}
