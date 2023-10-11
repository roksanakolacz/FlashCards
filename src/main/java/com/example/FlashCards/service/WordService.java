package com.example.FlashCards.service;

import com.example.FlashCards.model.Idiom;
import com.example.FlashCards.model.Level;
import com.example.FlashCards.model.Word;
import com.example.FlashCards.model.dto.WordDTO;
import com.example.FlashCards.model.dto.WordMapper;
import com.example.FlashCards.repository.WordRepository;
import com.example.FlashCards.webclient.chatGPT.ChatGPTHelper;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private ChatGPTHelper chatGPTHelper;

    public List<Word> saveWordsToDatabase(List<WordDTO> words, Long courseId) {

        List<Word> savedWords = new ArrayList<>();

        for (WordDTO wordDTO : words) {
            Word wordEntity = wordMapper.mapToEntity(wordDTO);
            wordEntity.setCourseId(courseId);
            wordRepository.save(wordEntity);
            savedWords.add(wordEntity);
        }

        return savedWords;

    }

    public Word getWord(Long id) {
        return wordRepository.findById(id).orElseThrow();
    }

    public List<Word> getAllWords() {
        return wordRepository.findAll();
    }

    @Transactional
    public void editWord(Long id, Word editedWord) {

        Word existingWord = wordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        existingWord.setWord(editedWord.getWord());
        existingWord.setTranslatedWord(editedWord.getTranslatedWord());

        wordRepository.save(existingWord);
    }

    public Boolean isTranslationCorrect(Word word, String translation) {
        return (translation == word.getWord());
    }


    public void deleteWord(Long id) {
        wordRepository.deleteById(id);
    }

    public List<Word> mapStringsToWordList(String language, String level, int listSize) {
        List<String> randomWordsGPT = chatGPTHelper.getRandomWordList(language, level, listSize);
        List<Word> randomWords = new ArrayList<>();

        for(String randomWord : randomWordsGPT){
            String translation = chatGPTHelper.getTranslation(randomWord);
            WordDTO wordDTO = WordDTO.builder().word(randomWord).translatedWord(translation).build();
            Word word = wordMapper.mapToEntity(wordDTO);
            randomWords.add(word);
        }

        return randomWords;

    }



}


