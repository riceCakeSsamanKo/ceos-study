package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface SchoolRepository extends JpaRepository<School, Long> {
}
