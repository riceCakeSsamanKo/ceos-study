package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom,Long> {
    List<ChattingRoom> findByUserId(Long userId);
}
