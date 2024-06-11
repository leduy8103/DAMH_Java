package com.example.DAJava.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Albums")
public class Albums {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long albumId;

    private String title;
    private Date releaseDate;
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "artistId")
    private Artists artist;

    @ManyToOne
    @JoinColumn(name = "genreId")
    private Genres genre;
}
