package com.ceos19.everytime.service;

import com.ceos19.everytime.domain.*;
import com.ceos19.everytime.repository.*;
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
    private final TimeTableCourseRepository timeTableCourseRepository;
    private final PostRepository postRepository;
    private final ChatRepository chatRepository;
    private final CommentRepository commentRepository;

    public Long join(User user) {
        userRepository.save(user);
        return user.getId();
    }

    public void deleteUser(User user) {
        // 연관된 Chat 제거
        List<ChattingRoom> chattingRooms =
                chattingRoomRepository.findByParticipant1IdOrParticipant2Id(user.getId());
        for (ChattingRoom chattingRoom : chattingRooms) {
            chatRepository.deleteAllByChattingRoomId(chattingRoom.getId());
        }
        // 연관된 ChattingRoom 제거
        chattingRoomRepository.deleteAll(chattingRooms);

        // 연관된 TimeTableCourse 제거
        List<TimeTable> timeTables = timeTableRepository.findByUserId(user.getId());
        for (TimeTable timeTable : timeTables) {
            timeTableCourseRepository.deleteAllByTimeTableId(timeTable.getId());
        }
        // 연관된 TimeTable 제거
        timeTableRepository.deleteAll(timeTables); // 연관된 TimeTableCourse도 cascade로 제거

        // 유저와 연관된 Comment 제거
        List<Comment> comments = commentRepository.findByCommenterId(user.getId());
        commentRepository.deleteAll(comments);

        // Post와 연관된 Comment 제거
        List<Post> posts = postRepository.findByAuthorId(user.getId());
        for (Post post : posts) {
            List<Comment> commentsInPost = commentRepository.findByPostId(post.getId());
            commentRepository.deleteAll(commentsInPost);
        }
        //연관된 Post 제거
        postRepository.deleteAll(posts);

        // User 제거
        userRepository.delete(user);
    }
}
