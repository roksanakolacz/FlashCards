package com.example.FlashCards.service;

import com.example.FlashCards.model.Course;
import com.example.FlashCards.model.Word;
import com.example.FlashCards.model.dto.WordDTO;
import com.example.FlashCards.model.dto.WordMapper;
import com.example.FlashCards.model.questions.Question;
import com.example.FlashCards.model.questions.QuestionForeignWordABCD;
import com.example.FlashCards.repository.CourseRepository;
import com.example.FlashCards.repository.QuestionRepository;
import com.example.FlashCards.repository.WordRepository;
import com.example.FlashCards.webclient.chatGPT.ChatGPTHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    @Autowired
    private final QuestionRepository questionRepository;

    @Autowired
    private final ChatGPTHelper chatGPTHelper;

    @Autowired
    private final CourseRepository courseRepository;

    public List<String> getQuestionOptions(Long questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        return question != null ? question.getOptions() : Collections.emptyList();
    }


    public void saveQuestions(List<Word> wordDTOList, Long courseId) {
        List<Question> questions = new ArrayList<>();
        List<QuestionForeignWordABCD> questionForeignWordABCDS = generateQuestions(wordDTOList);

        questions.addAll(questionForeignWordABCDS);


        Course savedCourse = courseRepository.findById(courseId).orElseThrow();

        for (Question question : questions) {

            Question savedQuestion = questionRepository.save(question);
            savedQuestion.setCourseId(courseId);
            savedCourse.addQuestion(question);
        }

    }


    public List<QuestionForeignWordABCD> generateQuestions(List<Word> wordDTOList){
        return generateQuestionForeignWordABCD(wordDTOList);

    }
    public List<QuestionForeignWordABCD> generateQuestionForeignWordABCD(List<Word> words){
        List<QuestionForeignWordABCD> questionForeignWordABCDS = new ArrayList<>();

        for(Word word : words){

            String content = String.format("Guess the translation of word: %s", word.getWord());

            List<String> options = new ArrayList<>();
            options.add(word.getTranslatedWord());
            options.add(chatGPTHelper.findSimiliarWord(word.getTranslatedWord()));

            ArrayList<Word> newList = new ArrayList<>(words);
            newList.remove(word);
            Collections.shuffle(newList);

            options.add(newList.get(0).getTranslatedWord());
            options.add(newList.get(1).getTranslatedWord());
            Collections.shuffle(options);

            String correctOption = word.getTranslatedWord();

            QuestionForeignWordABCD question = new QuestionForeignWordABCD(content, options, correctOption, word.getCourseId(), word.getId());
            questionForeignWordABCDS.add(question);
        }

        return questionForeignWordABCDS;

    }


    public List<QuestionForeignWordABCD> generateRandomQuestion(List<Word> words){
        List<QuestionForeignWordABCD> questionForeignWordABCDS = new ArrayList<>();

        for(Word word : words){

            String content = String.format("Guess the translation of word: %s", word.getWord());

            List<String> options = new ArrayList<>();
            options.add(word.getTranslatedWord());
            options.add(chatGPTHelper.findSimiliarWord(word.getTranslatedWord()));
            options.add(chatGPTHelper.findSimiliarWord(word.getTranslatedWord()));
            options.add(chatGPTHelper.findSimiliarWord(word.getTranslatedWord()));

            Collections.shuffle(options);

            String correctOption = word.getTranslatedWord();

            QuestionForeignWordABCD question = new QuestionForeignWordABCD(content, options, correctOption, word.getCourseId(), word.getId());
            questionForeignWordABCDS.add(question);
        }

        return questionForeignWordABCDS;

    }
}
