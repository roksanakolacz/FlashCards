package com.example.FlashCards.model;

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
@Table(name="course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Enumerated(EnumType.STRING)
    private Idiom language;

    private String title;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    private Long userId;

    private LocalDateTime date;

    @OneToMany
    @JoinColumn(name="courseId")
    private List<Word> wordList;

    public Course(Idiom language, String title, Level level, Subject subject, List<Word> wordList) {
        this.language = language;
        this.title = title;
        this.level = level;
        this.subject = subject;
        this.date = LocalDateTime.now();
        this.wordList = wordList;
    }
}
