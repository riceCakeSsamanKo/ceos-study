package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
    List<ChattingRoom> findByParticipant1Id(Long userId);
    List<ChattingRoom> findByParticipant2Id(Long userId);

    @Query("SELECT c FROM ChattingRoom c " +
            "WHERE c.participant1.id = :userId " +
            "OR c.participant2.id = :userId")
    List<ChattingRoom> findByParticipant1IdOrParticipant2Id(@Param("userId") Long userId);
}
