package com.ceos19.everytime.service;

import com.ceos19.everytime.domain.Course;
import com.ceos19.everytime.domain.TimeTable;
import com.ceos19.everytime.domain.TimeTableCourse;
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
public class TimeTableService {
    private final TimeTableRepository timeTableRepository;
    private final TimeTableCourseRepository timeTableCourseRepository;

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

    public TimeTable findByTimeTableId(Long timeTableId) {
        Optional<TimeTable> optionalTimeTable = timeTableRepository.findById(timeTableId);
        if (optionalTimeTable.isEmpty()) {
            throw new NullPointerException("해당 TimeTable이 존재하지 않습니다");
        }
        return optionalTimeTable.get();
    }

    public List<TimeTable> findByUserId(Long userId) {
        return timeTableRepository.findByUserId(userId);
    }
}
