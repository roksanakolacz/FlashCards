package com.example.FlashCards.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.atmosphere.config.service.Get;

@Getter
@Setter
@AllArgsConstructor
public class WordDTO {
    private String word;
    private String translatedWord;

}
