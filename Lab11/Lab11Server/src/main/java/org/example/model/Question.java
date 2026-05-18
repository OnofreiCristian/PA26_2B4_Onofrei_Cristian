package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "QUESTIONS")
@EntityListeners(AuditListener.class)
@Setter
@Getter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    @SequenceGenerator(name = "question_seq", sequenceName = "QUESTION_SEQ", allocationSize = 1)

    private Long id;

    @Column(name = "QUESTION_TEXT")
    private String text;

    @Column(name = "CORRECT_ANSWER")
    private String correctAnswer;

    public Question(String text, String correctAnswer) {
        this.text = text;
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrect(String answer) {return correctAnswer.equals(answer.trim());}


}