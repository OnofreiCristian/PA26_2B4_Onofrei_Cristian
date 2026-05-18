package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "RESULTS")
@Getter
@EntityListeners(AuditListener.class)
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_seq")
    @SequenceGenerator(name = "result_seq", sequenceName = "RESULT_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PLAYER_ID", nullable = false)
    private Player player;

    @ManyToOne
    @JoinColumn(name = "GAME_ID", nullable = false)
    private Game game;

    @Column(name = "FINAL_SCORE")
    private int score;

    @Column(name = "RESPONSE_TIME_MS")
    private long responseTimeMs;

    public Result() {}

    public Result(Player player, Game game, int score, long responseTimeMs) {
        this.player = player;
        this.game = game;
        this.score = score;
        this.responseTimeMs = responseTimeMs;
    }

}