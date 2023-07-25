package com.example.FlashCards.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Idiom language;

    private String title;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    private Long userId;

    @OneToMany
    @JoinColumn(name="courseId")
    private List<Word> wordList;

    public Course(Idiom language, String title, Level level, Subject subject) {
        this.language = language;
        this.title = title;
        this.level = level;
        this.subject = subject;
    }
}
