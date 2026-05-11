package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "GAMES")
@Getter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_seq")
    @SequenceGenerator(name = "game_seq", sequenceName = "GAME_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "MATCH_DATE")
    private LocalDateTime matchDate;

    public Game() {
        this.matchDate = LocalDateTime.now();
    }

}