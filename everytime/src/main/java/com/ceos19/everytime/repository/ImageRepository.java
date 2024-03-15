package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
