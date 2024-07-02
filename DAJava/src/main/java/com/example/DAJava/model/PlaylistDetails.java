package com.example.DAJava.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "playlist_details")
public class PlaylistDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistDetailId;

    @ManyToOne
    @JoinColumn(name = "playlistId", referencedColumnName = "playlistId")
    private Playlists playlists;

    @ManyToOne
    @JoinColumn(name = "songId", referencedColumnName = "songId")
    private Songs song;
}
