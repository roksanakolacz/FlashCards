package com.example.FlashCards.service;

import com.example.FlashCards.model.Word;
import com.example.FlashCards.model.WordDTO;
import com.example.FlashCards.model.WordMapper;
import com.example.FlashCards.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private WordMapper wordMapper;

    public void saveWordsToDatabase(List<WordDTO> words) {

        words.stream()
                .forEach(wordDTO -> wordRepository.save(wordMapper.mapToEntity(wordDTO)));
    }

    public List<Word> getAllWords() {
        return wordRepository.findAll();
    }
}