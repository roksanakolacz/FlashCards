package com.example.FlashCards.controller;

import com.example.FlashCards.model.DailyWord;
import com.example.FlashCards.model.Idiom;
import com.example.FlashCards.service.DailyWordService;
import com.example.FlashCards.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class HomeController {

    @Autowired
    private DailyWordService dailyWordService;
    @Autowired
    private UserService userService;


    @GetMapping("/courses")
    public String getCoursesList() {
        return "browseCourses";
    }

    @GetMapping("/random-course")
    public String getRandomWordCourse() {
        return "createRandomWordCourse";
    }


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
