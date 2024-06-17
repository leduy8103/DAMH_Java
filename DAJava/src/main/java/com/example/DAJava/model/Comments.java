package com.example.DAJava.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String text;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "songId")
    private Songs song;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;
}
