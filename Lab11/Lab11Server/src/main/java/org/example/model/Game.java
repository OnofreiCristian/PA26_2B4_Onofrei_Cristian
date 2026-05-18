package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table(name = "GAMES")
@Getter
@EntityListeners(AuditListener.class)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_seq")
    @SequenceGenerator(name = "game_seq", sequenceName = "GAME_SEQ", allocationSize = 1)
    private Long id;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Result> results;
    @Column(name = "MATCH_DATE")
    private LocalDateTime matchDate;

    @ManyToMany
    @JoinTable(
            name = "GAME_QUESTIONS",
            joinColumns = @JoinColumn(name = "GAME_ID"),
            inverseJoinColumns = @JoinColumn(name = "QUESTION_ID")
    )
    private List<Question> questions;

    public Game() {
        this.matchDate = LocalDateTime.now();
    }

}