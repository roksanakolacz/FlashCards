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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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



    @GetMapping("/courses/new")
    public String getCreateCoursePage(Model model, HttpSession session) {
        List<WordDTO> wordDTOList = (List<WordDTO>) session.getAttribute("wordList");

        model.addAttribute("wordsDTOList", wordDTOList);
        return "createCourse";
    }

    @PostMapping("/courses/new")
    public String saveCourse(Course course, HttpSession session, Model model, RedirectAttributes redirectAttributes){

        List<WordDTO> wordDTOList = (List<WordDTO>) session.getAttribute("wordList");

        if (wordDTOList == null || wordDTOList.isEmpty()) {
            redirectAttributes.addFlashAttribute("excelFileNotUploaded", true);
            return "redirect:/courses/new";
        }

        Long userId = (Long) session.getAttribute("userId");
        course.setUserId(userId);
        Course savedCourse = courseService.saveCourse(course);

        wordService.saveWordsToDatabase(wordDTOList, savedCourse.getCourseId());

        model.addAttribute("courseAdded", true);

        session.invalidate();


        return "createCourse";
    }

    @PostMapping("/courses/words")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file, Model model, HttpSession session) {

        try {
            String originalFileName = file.getOriginalFilename();
            String tempDir = System.getProperty("java.io.tmpdir");
            String filePath = tempDir + File.separator + originalFileName;

            File destFile = new File(filePath);
            file.transferTo(destFile);

            List<WordDTO> wordDTOList = excelProcessor.processExcelFile(filePath);
            session.setAttribute("wordList", wordDTOList);


            return "redirect:/courses/new";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }


}
