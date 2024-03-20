package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.TimeTableCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Jpa21Utils;

import java.util.List;

// 사용 x
public interface TimeTableCourseRepository extends JpaRepository<TimeTableCourse,Long> {
    List<TimeTableCourse> findByCourseId(Long courseId);
    List<TimeTableCourse> findByTimeTableId(Long timeTableId);
}
