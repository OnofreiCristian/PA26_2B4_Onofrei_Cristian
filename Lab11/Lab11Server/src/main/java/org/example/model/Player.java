package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Entity
@Table(name = "PLAYERS")
@Getter
@Setter
@EntityListeners(AuditListener.class)
public class Player {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_seq")
    @SequenceGenerator(name = "player_seq", sequenceName = "PLAYER_SEQ", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Result> results;

    // Note: Use @Transient for fields you DON'T want to save in the DB
    @Transient
    private int currentScore = 0;

    public Player() {}

    public Player(String name) {
        this.name = name;
    }



}