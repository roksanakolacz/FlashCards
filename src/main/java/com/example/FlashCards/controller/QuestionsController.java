package com.example.FlashCards.controller;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.FlashCards.model.Course;
import com.example.FlashCards.model.Word;
import com.example.FlashCards.model.questions.Question;
import com.example.FlashCards.model.questions.QuestionForeignWordABCD;
import com.example.FlashCards.service.CourseService;
import com.example.FlashCards.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.*;

@Controller
@SessionAttributes({"questionSession", "words", "course"}) // Dodaj tylko "questionSession" jako atrybut sesji
public class QuestionsController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private QuestionService questionService;

    @ModelAttribute("questionSession")
    public QuestionSession getQuestionSession() {
        return new QuestionSession();
    }

    private List<Word> words;
    Logger logger = LoggerFactory.getLogger(QuestionsController.class);

    @GetMapping("/train/course/{id}")
    public String getQuestion(@PathVariable Long id, Model model, @ModelAttribute("questionSession") QuestionSession questionSession) {
        Course courseById = courseService.getCourseById(id).orElseThrow();
        questionSession.setCurrentIndexChecked(false);
        logger.info("Size of the list is {}", questionSession.getQuestions().size());

        if (questionSession.getQuestions().isEmpty()) {
            questionSession.setQuestions(courseService.getQuestions(id));
            Collections.shuffle(questionSession.getQuestions());
            logger.info("Initialized questions with {} items", questionSession.getQuestions().size());
        }

        if (questionSession.getCurrentIndex() >= questionSession.getQuestions().size()) {
            return "redirect:/courses";
        }

        if (!questionSession.getQuestions().isEmpty()) {


            Question currentQuestion = questionSession.getQuestions().get(questionSession.getCurrentIndex());
            if (!Hibernate.isInitialized(currentQuestion.getOptions())) {
                currentQuestion.setOptions(questionService.getQuestionOptions(currentQuestion.getQuestionId()));
            }

            logger.info("Current question: {}", currentQuestion.getContent());
            model.addAttribute("course", courseById);
            model.addAttribute("question", currentQuestion);
            model.addAttribute("options", currentQuestion.getOptions());
            logger.info("Current index: {}", questionSession.getCurrentIndex());
            questionSession.setCurrentIndex(questionSession.getCurrentIndex() + 1);
        }



        return "questions";
    }

    @PostMapping("/train/course/{id}/check")
    public String checkTranslationInCourse(
            @PathVariable Long id,
            @ModelAttribute("questionSession") QuestionSession questionSession,
            @RequestParam String meaning,
            Model model,
            SessionStatus sessionStatus,
            HttpSession httpSession) {

        logger.info("I am in method checkTranslation");
        logger.info("Length of question list is {}", questionSession.getQuestions().size());

        if (questionSession.getCurrentIndex() >= questionSession.getQuestions().size()) {
            // Wszystkie pytania zostały już przetworzone, można przekierować użytkownika gdziekolwiek chcesz
            sessionStatus.setComplete(); // Wyczyść sesję questionSession
            return "redirect:/courses"; // Na przykład na stronę z kursami
        }

        Question currentQuestion = questionSession.getQuestions().get(questionSession.getCurrentIndex() - 1);
        if (!Hibernate.isInitialized(currentQuestion.getOptions())) {
            currentQuestion.setOptions(questionService.getQuestionOptions(currentQuestion.getQuestionId()));
        }

        logger.info("Current index: {}", questionSession.getCurrentIndex());

        if (meaning.equalsIgnoreCase(currentQuestion.getCorrectOption())) {
            questionSession.setCorrect(true);
            questionSession.setIncorrect(false);
            logger.info("Correct answer");
            model.addAttribute("isCorrect", true);
        } else {
            questionSession.setCorrect(false);
            questionSession.setIncorrect(true);
            logger.info("Wrong answer");
            model.addAttribute("isIncorrect", true);
        }

        questionSession.setCurrentIndexChecked(true);

        Course courseById = courseService.getCourseById(id).orElseThrow();

        model.addAttribute("course", courseById);
        model.addAttribute("question", currentQuestion);
        model.addAttribute("options", currentQuestion.getOptions());

        return "questions";
    }


    @GetMapping("/train/flashcards/{id}")
    public String getFlashcards(@PathVariable Long id, Model model) {
        Optional<Course> course = courseService.getCourseById(id);

        if (words == null) {
            words = courseService.getWords(id);
            Collections.shuffle(words);
        }

        if (!words.isEmpty()) {
            model.addAttribute("course", course.get());
            model.addAttribute("word", words.get(0));
        }

        return "flashcards";
    }


    @PostMapping("/train/flashcards")
    public String checkTranslationInFlashCards(
            SessionStatus sessionStatus,
            @RequestParam String meaning,
            @ModelAttribute("course") Course course,
            @ModelAttribute("word") Word word,
            Model model) {

        Long id = course.getCourseId();


        if (meaning.equalsIgnoreCase(word.getTranslatedWord())) {
            words.remove(word);
        } else {
            //first remove, and then it goes to the end of the list
            words.remove(word);
            words.add(word);

        }

        if (words.isEmpty()) {
            sessionStatus.setComplete();
            words = null;
            model.addAttribute("flashCardsProgress", "FlashCards completed!");
            return "redirect:/courses";
        }

        return "redirect:/train/flashcards/" + id;
    }


}