package com.ceos19.everytime.service;

import com.ceos19.everytime.domain.Course;
import com.ceos19.everytime.domain.TimeTableCourse;
import com.ceos19.everytime.repository.CourseRepository;
import com.ceos19.everytime.repository.TimeTableCourseRepository;
import com.ceos19.everytime.repository.TimeTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final TimeTableCourseRepository timeTableCourseRepository;

    @Transactional(readOnly = false)
    public Long save(Course course) {
        Course save = courseRepository.save(course);
        return save.getId();
    }

    @Transactional(readOnly = false)
    public void deleteCourseById(Long courseId) {
        // Course와 연관된 중간테이블 조회
        List<TimeTableCourse> timeTableCourses = timeTableCourseRepository.findByCourseId(courseId);

        // 연관관계 제거
        timeTableCourseRepository.deleteAll(timeTableCourses);

        // Course 제거
        courseRepository.deleteById(courseId);
    }
}
