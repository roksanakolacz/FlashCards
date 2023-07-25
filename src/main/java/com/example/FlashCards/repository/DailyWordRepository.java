package com.example.FlashCards.repository;

import com.example.FlashCards.model.DailyWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyWordRepository extends JpaRepository<DailyWord, Long> {
}
