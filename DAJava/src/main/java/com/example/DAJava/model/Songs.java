package com.example.DAJava.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "songs")
public class Songs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;
    private String title;
    private String lyric;
    private int duration;
    private Date releaseDate;
    private String filePath;
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "albumId")
    private Albums album;

    @ManyToOne
    @JoinColumn(name = "genreId")
    private Genres genre;
}
