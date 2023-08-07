package com.example.FlashCards.controller;

import com.example.FlashCards.model.Course;
import com.example.FlashCards.model.Word;
import com.example.FlashCards.service.CourseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes({"word", "course"})
public class QuestionsController {

    @Autowired
    private CourseService courseService;

    private List<Word> words;

    @GetMapping("/train/course/{id}")
    public String getQuestion(@PathVariable Long id) {
        return "question";
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
    public String checkTranslation(
            SessionStatus sessionStatus,
            @RequestParam String meaning,
            @ModelAttribute("course") Course course,
            @ModelAttribute("word") Word word,
            Model model) {

        Long id = course.getCourseId();

        if (meaning.equalsIgnoreCase(word.getTranslatedWord())) {
            words.remove(word);
        } else {
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
