package com.example.FlashCards.service;

import com.example.FlashCards.model.Course;
import com.example.FlashCards.model.Word;
import com.example.FlashCards.model.questions.Question;
import com.example.FlashCards.model.questions.QuestionForeignWordABCD;
import com.example.FlashCards.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public Optional<Course> getCourseById(Long userId){
        return courseRepository.findById(userId);
    }

    public List<Word> getWords(Long courseId){
        return courseRepository.findById(courseId).get().getWords();
    }

    public List<Question> getQuestions(Long courseId){
        return courseRepository.findById(courseId).get().getQuestions();
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
