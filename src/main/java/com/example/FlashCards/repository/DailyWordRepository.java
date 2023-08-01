package com.example.FlashCards.repository;

import com.example.FlashCards.model.DailyWord;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyWordRepository extends JpaRepository<DailyWord, Long> {


    @Query(value = "select top 1 * from daily_words d where d.local_date =:localDate and d.user_id = :userId", nativeQuery = true)
    DailyWord findByTodayDateAndUserId(@Param("localDate") LocalDate localDate, @Param("userId") Long userId);


    @Query(value = "select top 1 * from daily_words d where d.word = word", nativeQuery = true)
    DailyWord findWord(@Param("word") String word);




}
