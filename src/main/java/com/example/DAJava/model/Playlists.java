package com.example.DAJava.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Playlists")
public class Playlists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistId;

    @Length(max = 100)
    private String name;

    @Length(max = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;
}
