package com.example.DAJava.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Ratings")
public class Ratings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    private int ratingValue;

    @ManyToOne
    @JoinColumn(name = "songId")
    private Songs song;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;
}
