package com.example.FlashCards.controller;

import com.example.FlashCards.model.Course;
import com.example.FlashCards.model.Word;
import com.example.FlashCards.service.CourseService;
import com.example.FlashCards.service.WordService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WordListController {

    private final WordService wordService;
    private final CourseService courseService;


    @GetMapping("/courses/{id}/words")
    public String displayWordsForCourse(@PathVariable Long id, Model model){

        List<Word> words = courseService.getWords(id);
        model.addAttribute("courseId", id);
        model.addAttribute("words", words);

        return "wordList";

    }


    @PostMapping("/words/{id}")
    public String editWord(@PathVariable("id") Long id, @RequestBody Word editedWord, @NotNull Model model){

        wordService.editWord(id, editedWord);

        Long courseId = (Long) model.getAttribute("courseId");

        return String.format("redirect:/courses/%d/words", courseId);


    }


    @GetMapping("/courses/{courseId}/words/{id}")
    public String deleteWord(@PathVariable("courseId") Long courseId, @PathVariable("id") Long id, Model model){
        wordService.deleteWord(id);
        return String.format("redirect:/courses/%d/words", courseId);
    }



}
