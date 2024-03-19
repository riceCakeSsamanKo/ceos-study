package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
