package com.example.FlashCards.repository;

import com.example.FlashCards.model.Course;
import com.example.FlashCards.model.DailyWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(value = "select * from course d where d.user_Id = :userId ", nativeQuery = true)
    List<Course> getCoursesForUser(@Param("userId") Long userId);


}
