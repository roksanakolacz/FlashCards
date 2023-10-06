package com.example.FlashCards.model.questions;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "question_type")
@Getter
@Setter
public abstract class Question {
    @Id
    @GeneratedValue
    private Long questionId;
    private String content;

    @ElementCollection
    private List<String> options;
    private String correctOption;

    private Long courseId;

    private Long wordId;

}
