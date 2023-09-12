package com.example.FlashCards.model.dto;

import com.example.FlashCards.model.Word;
import com.example.FlashCards.model.dto.WordDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class WordMapper {

    private final ModelMapper modelMapper;

    public WordMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(WordDTO.class, Word.class)
                .addMappings(mapper -> mapper.skip(Word::setId));
    }

    public WordDTO mapToDto(Word word) {
        return modelMapper.map(word, WordDTO.class);
    }

    public Word mapToEntity(WordDTO wordDTO) {
        return modelMapper.map(wordDTO, Word.class);
    }
}