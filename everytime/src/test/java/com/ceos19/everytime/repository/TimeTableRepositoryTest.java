package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class TimeTableRepositoryTest {
    @Autowired
    SchoolRepository schoolRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TimeTableRepository timeTableRepository;
    @Autowired
    EntityManager em;

    School school;
    User userA;
    Course course;

    @Before
    public void each(){
        school = new School("schoolA", "computer");
        schoolRepository.save(school);

        userA = new User("userA@asdf.com", "password", "userA", "aaabbbc", "userA@asdf.com", school);
        userRepository.save(userA);

        course = new Course("1234-567", "컴퓨터개론", 1, "김교수", 3, "t123");

        course.addClassTime(Weekend.MON,2);
        course.addClassTime(Weekend.TUE,4);
        course.addClassTime(Weekend.TUE,5);
        courseRepository.save(course);
//        course.getClassTimes().remove(0);

        TimeTable timeTable1 = new TimeTable("timeTable1", "2024-1", userA);
        timeTableRepository.save(timeTable1);

    }

    @Test
    public void 타임테이블조회() throws Exception{
        //given
        System.out.println(userA.getId());
        //when
        List<TimeTable> byUser = timeTableRepository.findByUserId(userA.getId());

        //then
        for (TimeTable timeTable : byUser) {
            System.out.println("timeTable = " + timeTable);
        }
    }
}
