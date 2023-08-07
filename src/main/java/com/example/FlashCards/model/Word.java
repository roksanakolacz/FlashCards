package com.example.FlashCards.model;

import com.example.FlashCards.model.questions.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;
    private String translatedWord;
    private Long courseId;

    private Boolean isTrained = false;
    private LocalDateTime date = LocalDateTime.now();;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Question> questions;

    public Word(String word, String translatedWord, Long courseId) {
        this.word = word;
        this.translatedWord = translatedWord;
        this.courseId = courseId;
        this.date = LocalDateTime.now();
    }
}
