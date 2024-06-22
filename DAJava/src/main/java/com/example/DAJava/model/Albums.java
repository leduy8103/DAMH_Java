package com.example.DAJava.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "artistId")
    private Artists artist;

    @ManyToOne
    @JoinColumn(name = "genreId")
    private Genres genre;
}
