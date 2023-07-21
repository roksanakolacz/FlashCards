package com.example.FlashCards.controller;

import com.example.FlashCards.ExcelProcessor;
import com.example.FlashCards.model.Word;
import com.example.FlashCards.model.WordDTO;
import com.example.FlashCards.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Controller
public class WordController {

    @Autowired
    private ExcelProcessor excelProcessor;

    @Autowired
    private WordService wordService;

    @GetMapping("/words")
    public String getWords(Model model) {
        List<Word> words = wordService.getAllWords();
        model.addAttribute("words", words);
        return "words";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String tempDir = System.getProperty("java.io.tmpdir"); // Katalog tymczasowy serwera
            String filePath = tempDir + File.separator + originalFileName;

            File destFile = new File(filePath);
            file.transferTo(destFile);

            List<WordDTO> wordDTOList = excelProcessor.processExcelFile(filePath);
            wordService.saveWordsToDatabase(wordDTOList);
            return "redirect:/words";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }
}
