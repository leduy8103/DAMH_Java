package com.example.DAJava.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PlaylistDetails")
public class PlaylistDetails {
    @EmbeddedId
    private PlaylistDetailId id;

    @ManyToOne
    @MapsId("playlistId")
    @JoinColumn(name = "playlistId")
    private Playlists playlists;

    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name = "songId")
    private Songs song;
}

@Embeddable
class PlaylistDetailId implements Serializable {
    private Long playlistId;
    private Long songId;
}
