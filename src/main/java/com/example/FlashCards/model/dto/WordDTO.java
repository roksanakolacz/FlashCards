package com.example.FlashCards.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.atmosphere.config.service.Get;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class WordDTO {

    private String word;
    private String translatedWord;

}
