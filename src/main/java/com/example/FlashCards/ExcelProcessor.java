package com.example.FlashCards;

import com.example.FlashCards.model.dto.WordDTO;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@Component
public class ExcelProcessor {

    public List<WordDTO> processExcelFile(String filePath) {
        List<WordDTO> words = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {

                    Cell wordCell = row.getCell(0);
                    Cell translatedWordCell = row.getCell(1);

                    String word = getStringCellValue(wordCell);
                    String translatedWord = getStringCellValue(translatedWordCell);

                    WordDTO wordObj = new WordDTO(word, translatedWord);
                    words.add(wordObj);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return words;
    }

    private Long getNumericCellValue(Cell cell) {
        if (cell != null) {
            return (long) cell.getNumericCellValue();
        }
        return null;
    }

    private String getStringCellValue(Cell cell) {
        if (cell != null) {
            return cell.getStringCellValue();
        }
        return null;
    }
}
