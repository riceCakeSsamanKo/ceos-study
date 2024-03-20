package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.TimeTable;
import com.ceos19.everytime.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
    List<TimeTable> findByUserId(Long userId);

    List<TimeTable> findByUserIdAndSemester(Long userId, String semester);

    Optional<TimeTable> findByUserIdAndSemesterAndName(Long userId, String semester, String name);
}
