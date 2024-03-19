package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School,Long> {
}
