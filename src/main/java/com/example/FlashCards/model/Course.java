package com.example.FlashCards.model;

import com.example.FlashCards.model.questions.Question;
import com.example.FlashCards.model.questions.QuestionForeignWordABCD;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private LocalDateTime date = LocalDateTime.now();

    @OneToMany
    @JoinColumn(name="courseId")
    private List<Word> words;

    @OneToMany
    @JoinColumn(name="courseId")
    private List<Question> questions;

    public Course(Idiom language, String title, Level level, Subject subject, List<Word> words) {
        this.language = language;
        this.title = title;
        this.level = level;
        this.subject = subject;
        this.words = words;
    }


    public int getWordCount() {
        if (words == null) {
            return 0;
        }
        return words.size();
    }


    public int getTrainedWordCount(){
        if (words == null) {
            return 0;
        }
        List<Word> trainedWords = words.stream()
                .filter(Word::getIsTrained).toList();

        return trainedWords.size();
    }

    public int getPercentageResult(){
        if (getWordCount() == 0){
            return 0;
        }
        return (getTrainedWordCount()/getWordCount());
    }

    public void addQuestion(Question question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        questions.add(question);
        question.setCourseId(this.getCourseId());
    }

}
