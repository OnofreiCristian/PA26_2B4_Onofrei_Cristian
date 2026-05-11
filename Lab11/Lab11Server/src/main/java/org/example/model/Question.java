package org.example.model;

import jakarta.persistence.*;


@Entity
@Table(name = "QUESTIONS")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    @SequenceGenerator(name = "question_seq", sequenceName = "QUESTION_SEQ", allocationSize = 1)

    private Long id;

    @Column(name = "QUESTION_TEXT")
    private String text;

    @Column(name = "CORRECT_ANSWER")
    private String correctAnswer;

    public Question() {}

    public Question(String text, String correctAnswer) {
        this.text = text;
        this.correctAnswer = correctAnswer;
    }

    public Long getId() {return id;}
    public String getText() {return text;}
    public boolean isCorrect(String answer) {return correctAnswer.equals(answer.trim());}


}