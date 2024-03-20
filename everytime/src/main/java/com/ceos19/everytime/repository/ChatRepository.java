package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {
    List<Chat> findByChattingRoomId(Long chattingRoomId);
}
