package com.example.FlashCards.controller;

import com.example.FlashCards.ExcelProcessor;
import com.example.FlashCards.LoginSession;
import com.example.FlashCards.model.Course;
import com.example.FlashCards.model.Word;
import com.example.FlashCards.model.WordDTO;
import com.example.FlashCards.service.CourseService;
import com.example.FlashCards.service.WordService;
import jakarta.servlet.http.HttpSession;
import org.atmosphere.config.service.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private ExcelProcessor excelProcessor;

    @Autowired
    private WordService wordService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private LoginSession loginSession;

    private List<WordDTO> wordDTOList;

    @GetMapping("/courses/new")
    public String getCreateCoursePage(Model model) {
        model.addAttribute("wordsDTOList", wordDTOList);
        return "createCourse";
    }

    @PostMapping("/courses/new")
    public String addCourse(Course course, HttpSession session){
        Long userId = (Long) session.getAttribute("userId");
        course.setUserId(userId);

        wordService.saveWordsToDatabase(wordDTOList, course.getCourseId());
        courseService.saveCourse(course);

        return "createCourse";
    }

    @PostMapping("/courses/words")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String originalFileName = file.getOriginalFilename();
            String tempDir = System.getProperty("java.io.tmpdir");
            String filePath = tempDir + File.separator + originalFileName;

            File destFile = new File(filePath);
            file.transferTo(destFile);

            wordDTOList = excelProcessor.processExcelFile(filePath);


            return "redirect:/courses/new";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }


}
