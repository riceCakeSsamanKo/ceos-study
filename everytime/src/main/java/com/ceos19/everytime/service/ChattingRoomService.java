package com.ceos19.everytime.service;

import com.ceos19.everytime.domain.Chat;
import com.ceos19.everytime.domain.ChattingRoom;
import com.ceos19.everytime.repository.ChatRepository;
import com.ceos19.everytime.repository.ChattingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChattingRoomService {
    private final ChattingRoomRepository chattingRoomRepository;
    private final ChatRepository chatRepository;

    @Transactional(readOnly = false)
    public Long save(ChattingRoom chattingRoom) {
        chattingRoomRepository.save(chattingRoom);
        return chattingRoom.getId();
    }

    @Transactional(readOnly = false)
    public void deleteChattingRoom(ChattingRoom chattingRoom) {
        List<Chat> chats = chatRepository.findByChattingRoomId(chattingRoom.getId());
        // 연관된 Chat들 제거
        chatRepository.deleteAll(chats);
        // ChattingRoom 제거
        chattingRoomRepository.delete(chattingRoom);
    }

    @Transactional(readOnly = false)
    public void deleteChattingRoomById(Long chattingRoomId) {
        List<Chat> chats = chatRepository.findByChattingRoomId(chattingRoomId);
        // 연관된 Chat들 제거
        chatRepository.deleteAll(chats);
        // ChattingRoom 제거
        chattingRoomRepository.deleteById(chattingRoomId);
    }
}
