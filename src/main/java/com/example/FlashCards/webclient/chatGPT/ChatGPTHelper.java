package com.example.FlashCards.webclient.chatGPT;

import com.example.FlashCards.model.Idiom;
import com.example.FlashCards.model.Level;
import com.example.FlashCards.model.Word;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ChatGPTHelper {

    private OpenAiService service;

    public ChatGPTHelper() {

        service = new OpenAiService("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", Duration.ofSeconds(30));

    }


    public String findSimiliarWord(String word){
        String question = "I want to receive from you only one word as an answer. Do not add any introduction " +
                "to your answer. Only one word every time. Find a smiliar word in the same language for the given word. " +
                "It is important that it is in the same language. It can not be a synonim this is very important, only the word from similiar category, " +
                "or similiar written. The word is " + word.toLowerCase();
        return removeSpecialCharacters(askChatGPT(question));
    }
    
    
    public String getRandomWord(Idiom language){
        String question = "I want to receive from you only one word as an answer. Do not add any introduction to your answer. Only one word every time. Give me one random word in " + language.name().toLowerCase();
        return removeSpecialCharacters(askChatGPT(question));
    }


    public List<String> getRandomWordList(String language, String level, int listSize){
        Set<String> randomWordsSet = new HashSet<>();

        String questionTemplate = "I want to receive from you only one word as an answer. " +
                "Do not add any introduction to your answer. Only one word every time. Each word from different category. Not similiar to the previous one. " +
                "Give me one random word about difficulty %s in %s";

        String formattedQuestion = String.format(questionTemplate, level.toLowerCase(), language.toLowerCase());


        while (randomWordsSet.size() < listSize) {
            String randomWord = removeSpecialCharacters(askChatGPT(formattedQuestion));
            if (randomWordsSet.contains(randomWord)){
                continue;
            }
            randomWordsSet.add(randomWord);
        }

        return new ArrayList<>(randomWordsSet);
    }

    public String getTranslation(String word){
        String question = " want to receive from you only one word as an answer. Give me translation for English of word: " + word;
        return askChatGPT(question);
    }

    public String getUsesExample(String word, Idiom language){
        String question = " want to receive from you only needed sentences. Give me three sentences in " + language.name().toLowerCase() + "for word: " + word;
        return askChatGPT(question);
    }



    private String askChatGPT(String question){
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(List.of(new ChatMessage("user", question)))
                .model("gpt-3.5-turbo")
                .build();

        List<ChatCompletionChoice> choices = service.createChatCompletion(completionRequest).getChoices();

        StringBuilder stringBuilder = new StringBuilder();
        choices.stream()
                .map(ChatCompletionChoice::getMessage)
                .map(ChatMessage::getContent)
                .forEach(stringBuilder::append);

        String result =  stringBuilder.toString();
        byte[] resultutf8Bytes = result.getBytes(StandardCharsets.UTF_8);
        String resultutf8String = new String(resultutf8Bytes, StandardCharsets.UTF_8);

        return resultutf8String;

    }


    public static String removeSpecialCharacters(String input) {
        String regex = "[^a-zA-Z]";
        return input.replaceAll(regex, "").toLowerCase();
    }

}
