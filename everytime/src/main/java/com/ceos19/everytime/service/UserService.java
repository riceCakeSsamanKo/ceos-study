package com.ceos19.everytime.service;

import com.ceos19.everytime.domain.ChattingRoom;
import com.ceos19.everytime.domain.TimeTable;
import com.ceos19.everytime.domain.User;
import com.ceos19.everytime.repository.ChattingRoomRepository;
import com.ceos19.everytime.repository.TimeTableCourseRepository;
import com.ceos19.everytime.repository.TimeTableRepository;
import com.ceos19.everytime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TimeTableRepository timeTableRepository;
    private final ChattingRoomRepository chattingRoomRepository;

    public void deleteUser(User user) {
        // 연관된 Chat 제거

        // 연관된 ChattingRoom 제거
        List<ChattingRoom> chattingRooms = chattingRoomRepository.findByUserId(user.getId());
        chattingRoomRepository.deleteAll(chattingRooms);

        // 연관된 TimeTableCourse 제거

        // 연관된 TimeTable 제거
        List<TimeTable> timeTables = timeTableRepository.findByUserId(user.getId());
        timeTableRepository.deleteAll(timeTables); // 연관된 TimeTableCourse도 cascade로 제거

        
    }
}
