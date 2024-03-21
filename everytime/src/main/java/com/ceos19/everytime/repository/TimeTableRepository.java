package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.Semester;
import com.ceos19.everytime.domain.TimeTable;
import com.ceos19.everytime.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
    @EntityGraph(attributePaths = {"user"})
    Optional<TimeTable> findById(Long timeTableId);

    @EntityGraph(attributePaths = {"user"})
    List<TimeTable> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"user"})
    List<TimeTable> findByUserIdAndYearAndSemester(Long userId, int year, Semester semester);

    @EntityGraph(attributePaths = {"user"})
    Optional<TimeTable> findByUserIdAndYearAndSemesterAndName(Long userId, int year, Semester semester, String name);
}
