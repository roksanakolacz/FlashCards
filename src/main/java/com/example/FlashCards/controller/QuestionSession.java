package com.example.FlashCards.controller;

import com.example.FlashCards.model.questions.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionSession {
    private List<Question> questions = new ArrayList<>();
    private int currentIndex = 0;

    private Boolean currentIndexChecked = false;
    private Boolean isCorrect = false;
    private Boolean isIncorrect = false;


    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public Boolean getIncorrect() {
        return isIncorrect;
    }

    public void setIncorrect(Boolean incorrect) {
        isIncorrect = incorrect;
    }

    public Boolean getCurrentIndexChecked() {
        return currentIndexChecked;
    }

    public void setCurrentIndexChecked(Boolean currentIndexChecked) {
        this.currentIndexChecked = currentIndexChecked;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
}
