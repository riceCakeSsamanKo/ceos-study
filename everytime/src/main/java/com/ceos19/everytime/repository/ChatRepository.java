package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.Chat;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @EntityGraph(attributePaths = {"author"})
    List<Chat> findByChattingRoomId(Long chattingRoomId);

    @EntityGraph(attributePaths = {"author"})
    List<Chat> findByAuthorId(Long authorID);
}
