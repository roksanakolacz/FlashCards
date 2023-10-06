package com.example.FlashCards.model.questions;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;

import java.util.List;

@Entity
@DiscriminatorValue("ForeignWordABCD")
public class QuestionForeignWordABCD extends Question{

    public QuestionForeignWordABCD() {
    }

    public QuestionForeignWordABCD(String content, List<String> options, String correctOption, Long courseId, Long wordId) {
        this.setContent(content);
        this.setOptions(options);
        this.setCorrectOption(correctOption);
        this.setCourseId(courseId);
        this.setWordId(wordId);
    }
}
