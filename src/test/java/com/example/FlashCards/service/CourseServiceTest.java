package com.example.FlashCards.service;

import com.example.FlashCards.model.*;
import com.example.FlashCards.model.questions.Question;
import com.example.FlashCards.model.questions.QuestionForeignWordABCD;
import com.example.FlashCards.repository.CourseRepository;
import com.example.FlashCards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CourseService courseService;


    private Course course1;
    private Course course2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        course1 = new Course(Idiom.SPANISH, "New course", Level.A2, Subject.CULTURE, null);
        course2 =new Course(Idiom.SPANISH, "New course 2", Level.A1, Subject.CULTURE, null);

    }


    @Test
    public void saveCourse_validCourse_courseRegistered(){
        //given
        Course courseToSave = new Course();
        when(courseRepository.save(courseToSave)).thenReturn(courseToSave);

        //when

        Course savedCourse = courseService.saveCourse(courseToSave);

        //then
        assertEquals(courseToSave, savedCourse);
        verify(courseRepository, times(1)).save(courseToSave);
    }

    @Test
    public void saveCourse_courseIsNull_throwsException(){
        //given
        Course courseToSave = null;
        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            courseService.saveCourse(courseToSave);
        });

    }


    @Test
    public void getCoursesForUser_existingUser_returnListOfCourses(){
        //given
        List<Course> expectedCourses = new ArrayList<>();
        User user = new User();
        course1.setUserId(user.getUserId());
        course2.setUserId(user.getUserId());
        expectedCourses.add(course1);
        expectedCourses.add(course2);

        when(courseRepository.getCoursesForUser(user.getUserId())).thenReturn(expectedCourses);

        //when
        List<Course> actualCourses = courseService.getCoursesForUser(user.getUserId());

        //then
        assertEquals(expectedCourses.size(), actualCourses.size());
        assertEquals(expectedCourses.get(0).getTitle(), actualCourses.get(0).getTitle());
        assertEquals(expectedCourses.get(1).getTitle(), actualCourses.get(1).getTitle());

    }


    @Test
    public void getCoursesForUser_existingUserWithoutCourses_returnEmptyList(){
        //given
        List<Course> expectedCourses = new ArrayList<>();
        User user = new User();

        when(courseRepository.getCoursesForUser(user.getUserId())).thenReturn(Collections.emptyList());

        //when
        List<Course> actualCourses = courseService.getCoursesForUser(user.getUserId());

        //then
        assertTrue(actualCourses.isEmpty());
    }


    @Test
    public void getCoursesForUser_userIdIsNull_throwsException(){
        //given
        Long userId = null;
        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            courseService.getCoursesForUser(userId);
        });
    }




    @Test
    public void getCourseById_existingCourseId_returnOptionalWithCourse(){

        //given

        Long existingCourseId = 1L;
        Course expectedCourse = new Course();
        when(courseRepository.findById(existingCourseId)).thenReturn(Optional.of(expectedCourse));

        //when
        Optional<Course> actualCourseOptional = courseService.getCourseById(existingCourseId);

        //then
        assertTrue(actualCourseOptional.isPresent());
        assertEquals(expectedCourse, actualCourseOptional.get());

    }



    @Test
    public void getCourseById_nonExistingCourseId_returnEmptyOptional(){

        //given

        Long nonExistingCourseId = 123L;
        when(courseRepository.findById(nonExistingCourseId)).thenReturn(Optional.empty());

        //when
        Optional<Course> actualCourseOptional = courseService.getCourseById(nonExistingCourseId);

        //then
        assertTrue(actualCourseOptional.isEmpty());

    }

    @Test
    public void getCourseById_courseIdIsNull_throwsException(){
        //given
        Long courseId = null;
        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            courseService.getCourseById(courseId);
        });
    }


    @Test
    public void getWords_existingCourseId_returnListOfWords(){
        //given
        Long courseId = 1L;
        Course course = new Course();
        Word word1 = new Word();
        word1.setWord("word1");
        Word word2 = new Word();
        word2.setWord("word2");

        List<Word> expectedWords = Arrays.asList(word1, word2);

        course.setWords(expectedWords);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        //when

        List<Word> actualWords = courseService.getWords(courseId);

        //then
        assertEquals(expectedWords.size(), actualWords.size());
        assertEquals(expectedWords.get(0), actualWords.get(0));
        assertEquals(expectedWords.get(1), actualWords.get(1));

    }

    @Test
    public void getWords_nonExistingCourseId_throwsException(){
        //given
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        //when and then
        assertThrows(NoSuchElementException.class, () -> {
            courseService.getWords(courseId);
        });

    }


    @Test
    public void getWords_courseIdIsNull_throwsException(){
        //given
        Long courseId = null;
        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            courseService.getWords(courseId);
        });
    }


    @Test
    public void getQuestions_existingCourseId_returnListOfQuestions(){
        //given
        Long courseId = 1L;
        Course course = new Course();
        Question question1 = new QuestionForeignWordABCD();
        question1.setContent("Guess the translation");
        Question question2 = new QuestionForeignWordABCD();
        question2.setContent("Guess the translation2");

        List<Question> expectedQuestions = Arrays.asList(question1, question2);

        course.setQuestions(expectedQuestions);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        //when

        List<Question> actualQuestions = courseService.getQuestions(courseId);

        //then
        assertEquals(expectedQuestions.size(), actualQuestions.size());
        assertEquals(expectedQuestions.get(0), actualQuestions.get(0));
        assertEquals(expectedQuestions.get(1), actualQuestions.get(1));

    }

    @Test
    public void getQuestions_nonExistingCourseId_throwsException(){
        //given
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        //when and then
        assertThrows(NoSuchElementException.class, () -> {
            courseService.getQuestions(courseId);
        });

    }


    @Test
    public void getQuestions_courseIdIsNull_throwsException(){
        //given
        Long courseId = null;
        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            courseService.getQuestions(courseId);
        });
    }


    @Test
    public void deleteCourse_correctID_courseDeleted(){
        //given
        //when
        //then
    }

}
