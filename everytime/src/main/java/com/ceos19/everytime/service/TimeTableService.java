package com.ceos19.everytime.service;

import com.ceos19.everytime.domain.TimeTable;
import com.ceos19.everytime.domain.TimeTableCourse;
import com.ceos19.everytime.repository.TimeTableCourseRepository;
import com.ceos19.everytime.repository.TimeTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * deprecated
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TimeTableService {
    private final TimeTableRepository timeTableRepository;
    private final TimeTableCourseRepository timeTableCourseRepository;

    @Transactional(readOnly = false)
    public Long save(TimeTable timeTable) {
        timeTableRepository.save(timeTable);
        return timeTable.getId();
    }

    @Transactional(readOnly = false)
    public void deleteTimeTable(TimeTable timeTable) {
        List<TimeTableCourse> timeTableCourses = timeTableCourseRepository.findByTimeTableId(timeTable.getId());

        // 중간테이블 제거
        timeTableCourseRepository.deleteAll(timeTableCourses);

        // timeTable 제거
        timeTableRepository.delete(timeTable);
    }

    @Transactional(readOnly = false)
    public void deleteTimeTableById(Long timeTableId) {
        // TimeTable과 연관된 중간 테이블 제거
        List<TimeTableCourse> timeTableCourses = timeTableCourseRepository.findByTimeTableId(timeTableId);

        // 중간테이블 제거
        timeTableCourseRepository.deleteAll(timeTableCourses);

        // timeTable 제거
        timeTableRepository.deleteById(timeTableId);
    }
}
