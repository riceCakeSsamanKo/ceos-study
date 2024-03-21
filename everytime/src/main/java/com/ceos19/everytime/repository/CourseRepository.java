package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    @EntityGraph(attributePaths = {"classTimes"})
    Optional<Course> findById(long courseId);

    @EntityGraph(attributePaths = {"classTimes"})
    List<Course> findByCourseNumber(String courseNumber);

    @EntityGraph(attributePaths = {"classTimes"})
    List<Course> findByName(String name);

    @EntityGraph(attributePaths = {"classTimes"})
    List<Course> findByOpeningGrade(int openingGrade);

    @EntityGraph(attributePaths = {"classTimes"})
    List<Course> findByProfessorName(String professorName);
}
