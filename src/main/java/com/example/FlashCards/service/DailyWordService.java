package com.example.FlashCards.service;

import com.example.FlashCards.ChatGPTHelper;
import com.example.FlashCards.LoginSession;
import com.example.FlashCards.model.DailyWord;
import com.example.FlashCards.model.Idiom;
import com.example.FlashCards.repository.DailyWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DailyWordService {
    @Autowired
    private DailyWordRepository dailyWordRepository;

    @Autowired
    LoginSession loginSession;
    @Autowired
    private ChatGPTHelper chatGPTHelper;

    public DailyWord getDailyWord(Idiom language, Long userId){
        String randomWord = chatGPTHelper.getRandomWord(language);
        String translation = chatGPTHelper.getTranslation(randomWord);
        String sentences = chatGPTHelper.getUsesExample(randomWord, language);

        DailyWord dailyWord = new DailyWord();
        dailyWord.setWord(randomWord);
        dailyWord.setTranslation(translation);
        dailyWord.setSentences(sentences);
        dailyWord.setLanguage(language);
        dailyWord.setUserId(userId);
        dailyWord.setLocalDate(LocalDate.now());

        return dailyWord;
    }


    public void saveDailyWord(DailyWord dailyWord){
        if(dailyWord == null){
            throw new NullPointerException("Daily word cannot be null");
        }
        dailyWordRepository.save(dailyWord);
    }
}
