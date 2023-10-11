package com.example.FlashCards.controller;

import com.example.FlashCards.model.Course;
import com.example.FlashCards.model.DailyWord;
import com.example.FlashCards.model.Idiom;
import com.example.FlashCards.model.Word;
import com.example.FlashCards.model.questions.Question;
import com.example.FlashCards.service.DailyWordService;
import com.example.FlashCards.service.QuestionService;
import com.example.FlashCards.service.UserService;
import com.example.FlashCards.service.WordService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private DailyWordService dailyWordService;
    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private WordService wordService;

    Logger logger = LoggerFactory.getLogger(QuestionsController.class);







    @GetMapping("/home")
    public String home(Model model, HttpSession httpSession){
        Long userId = (Long) httpSession.getAttribute("userId");


        if (dailyWordService.findByTodayDateAndUserId(LocalDate.now(), userId) == null) {
            DailyWord dailyWordFromChat = dailyWordService.getDailyWord(userService.findUserByUserId(userId).getPreferredLanguage(), userId);
            dailyWordService.saveDailyWord(dailyWordFromChat);
            DailyWord dailyWord = dailyWordService.findByTodayDateAndUserId(LocalDate.now(), userId);
            model.addAttribute("dailyWord", dailyWord);
        } else {
            DailyWord dailyWord = dailyWordService.findByTodayDateAndUserId(LocalDate.now(), userId);
            model.addAttribute("dailyWord", dailyWord);
        }

        model.addAttribute("username", userService.findUserByUserId(userId).getUsername());
        model.addAttribute("preferredLanguage", userService.findUserByUserId(userId).getPreferredLanguage().toString().toLowerCase());
        return "homePage";
    }



}
