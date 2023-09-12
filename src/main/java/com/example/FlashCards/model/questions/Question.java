package com.example.FlashCards.model.questions;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public abstract class Question {
    @Id
    @GeneratedValue
    private Long questionId;
    private String content;
    private List<String> options;
    private int correctOptionIndex;

    private Long courseId;

    private Long wordId;

    public boolean isCorrect(int selectedOptionIndex) {
        return selectedOptionIndex == correctOptionIndex;
    }


   // public abstract String generateQuestionForWord(Word word);
}
