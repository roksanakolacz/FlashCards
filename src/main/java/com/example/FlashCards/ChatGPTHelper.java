package com.example.FlashCards;

import com.example.FlashCards.model.Idiom;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ChatGPTHelper {

    private OpenAiService service;

    public ChatGPTHelper() {

        service = new OpenAiService("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", Duration.ofSeconds(30));

    }

    public String getRandomWord(Idiom language){
        String question = "I want to receive from you only one word as an answer. Do not add any introduction to your answer. Only one word every time. Give me one random word in " + language.name().toLowerCase();
        return removeSpecialCharacters(askChatGPT(question));
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
        return input.replaceAll(regex, "");
    }

}
