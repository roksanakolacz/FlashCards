package com.example.FlashCards.controller;

import com.example.FlashCards.model.Course;
import com.example.FlashCards.model.Word;
import com.example.FlashCards.model.dto.WordDTO;
import com.example.FlashCards.model.questions.Question;
import com.example.FlashCards.model.questions.QuestionForeignWordABCD;
import com.example.FlashCards.service.CourseService;
import com.example.FlashCards.service.QuestionService;
import com.example.FlashCards.service.WordService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"questionSession", "words", "course"})
public class RandomCourseController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CourseService courseService;

    @Autowired
    private WordService wordService;


    @ModelAttribute("questionSession")
    public QuestionSession getQuestionSession() {
        return new QuestionSession();
    }


    Logger logger = LoggerFactory.getLogger(QuestionsController.class);



    @GetMapping("/courses/form")
    public String getRandomWordCourseForm(){

        return "randomWordsForm";

    }


    @GetMapping("/courses/form/course")
    public String getRandomWordCourse(@RequestParam String language, @RequestParam String level, @RequestParam String noOfWords,
                                      Model model, @ModelAttribute("questionSession") QuestionSession questionSession) {

        int numberOfWords = Integer.parseInt(noOfWords);

        questionSession.setCurrentIndexChecked(false);
        logger.info("Size of the list is {}", questionSession.getQuestions().size());

        if (questionSession.getQuestions().isEmpty()) {
            List<Word> randomWords = wordService.mapStringsToWordList(language, level, numberOfWords);
            logger.info("First random words is {}", randomWords.get(0).getWord());


            List<Question> questions = new ArrayList<>();
            List<QuestionForeignWordABCD> questionForeignWordABCDS = questionService.generateRandomQuestion(randomWords);

            questions.addAll(questionForeignWordABCDS);

            questionSession.setQuestions(questions);

            logger.info("Initialized questions with {} items", questionSession.getQuestions().size());
        }

        if (questionSession.getCurrentIndex() >= questionSession.getQuestions().size()) {
            return "redirect:/home";
        }

        if (!questionSession.getQuestions().isEmpty()) {

            Question currentQuestion = questionSession.getQuestions().get(questionSession.getCurrentIndex());
            if (!Hibernate.isInitialized(currentQuestion.getOptions())) {
                currentQuestion.setOptions(questionService.getQuestionOptions(currentQuestion.getQuestionId()));
            }

            logger.info("Current question: {}", currentQuestion.getContent());
            model.addAttribute("question", currentQuestion);
            model.addAttribute("options", currentQuestion.getOptions());
            logger.info("Current index: {}", questionSession.getCurrentIndex());
            questionSession.setCurrentIndex(questionSession.getCurrentIndex() + 1);
        }

        return "randomWordsQuestion";
    }







    @GetMapping("/courses/form/questions")
    public String getRandomWordCourseQuestion(Model model, @ModelAttribute("questionSession") QuestionSession questionSession) {


        questionSession.setCurrentIndexChecked(false);
        logger.info("Size of the list is {}", questionSession.getQuestions().size());


        if (questionSession.getCurrentIndex() >= questionSession.getQuestions().size()) {
            return "redirect:/home";
        }

        if (!questionSession.getQuestions().isEmpty()) {

            Question currentQuestion = questionSession.getQuestions().get(questionSession.getCurrentIndex());
            if (!Hibernate.isInitialized(currentQuestion.getOptions())) {
                currentQuestion.setOptions(questionService.getQuestionOptions(currentQuestion.getQuestionId()));
            }

            logger.info("Current question: {}", currentQuestion.getContent());
            model.addAttribute("question", currentQuestion);
            model.addAttribute("options", currentQuestion.getOptions());
            logger.info("Current index: {}", questionSession.getCurrentIndex());
            questionSession.setCurrentIndex(questionSession.getCurrentIndex() + 1);
        }


        return "randomWordsQuestion";
    }




    @GetMapping("/courses/form/questions/check")
    public String checkTranslationInCourse(
            @ModelAttribute("questionSession") QuestionSession questionSession,
            @RequestParam String meaning,
            Model model,
            SessionStatus sessionStatus,
            HttpSession httpSession) {

        logger.info("I am in method checkTranslation");
        logger.info("Length of question list is {}", questionSession.getQuestions().size());

        if (questionSession.getCurrentIndex() >= questionSession.getQuestions().size()) {
            sessionStatus.setComplete();
            return "redirect:/courses";
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

        model.addAttribute("question", currentQuestion);
        model.addAttribute("options", currentQuestion.getOptions());

        return "randomWordsQuestion";
    }



}
