package com.example.FlashCards.service;

import com.example.FlashCards.model.Course;
import com.example.FlashCards.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    private WordService wordService;

    public Course saveCourse(Course course){

        return courseRepository.save(course);

    }

    public List<Course> getCoursesForUser(Long userId){
        return courseRepository.getCoursesForUser(userId);
    }




}
