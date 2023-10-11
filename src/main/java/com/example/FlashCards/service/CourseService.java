package com.example.FlashCards.service;

import com.example.FlashCards.controller.QuestionsController;
import com.example.FlashCards.model.Course;
import com.example.FlashCards.model.User;
import com.example.FlashCards.model.Word;
import com.example.FlashCards.model.questions.Question;
import com.example.FlashCards.model.questions.QuestionForeignWordABCD;
import com.example.FlashCards.repository.CourseRepository;
import com.example.FlashCards.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public Course saveCourse(Course course) {
        if (course == null) {
          throw new IllegalArgumentException("Course cannot be null");
        }
        return courseRepository.save(course);
    }

    public List<Course> getCoursesForUser(Long userId){
        if(userId==null){
            throw new IllegalArgumentException("UserId cannot be null");
        }
        return courseRepository.getCoursesForUser(userId);
    }


    public Optional<Course> getCourseById(Long courseId){
        if(courseId == null){
            throw new IllegalArgumentException("CourseId cannot be null");
        }

        return courseRepository.findById(courseId);
    }

    public List<Word> getWords(Long courseId){
        if(courseId == null){
            throw new IllegalArgumentException("CourseId cannot be null");
        }

        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isPresent()) {
            return courseOptional.get().getWords();
        } else {
            throw new NoSuchElementException("Course with ID not found");
        }
    }

    public List<Question> getQuestions(Long courseId){
        if(courseId == null){
            throw new IllegalArgumentException("CourseId cannot be null");
        }
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isPresent()) {
            return courseOptional.get().getQuestions();
        } else {
            throw new NoSuchElementException("Course with ID not found");
        }
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
