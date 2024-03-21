package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.*;
import com.ceos19.everytime.service.UserService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class CommentRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SchoolRepository schoolRepository;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TimeTableRepository timeTableRepository;
    @Autowired
    TimeTableCourseRepository timeTableCourseRepository;
    @Autowired
    ChattingRoomRepository chattingRoomRepository;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    EntityManager em;

    User user1;
    Post post;

    @BeforeEach
    public void each() {
        // 학교 저장
        School school = new School("홍익대학교");
        schoolRepository.save(school);

        // 게시판 저장
        Board board = new Board("컴공게시판", school);
        boardRepository.save(board);

        user1 = new User("myUsername", "myPassword", "김상덕", "A000011", "um@naver.com", school);
        userRepository.save(user1);

        post = new Post("새로운 포스팅", "ㅈㄱㄴ", false, false, user1, board);
        post.addPostLike(user1);
        post.addPostLike(user1);
        post.addPostLike(user1);

        Attachment attachment = Attachment.builder()
                .originFileName("original")
                .storePath("/usr")
                .attachmentType(AttachmentType.IMAGE)
                .build();

        post.addAttachment(attachment);
        post.addAttachment(attachment);

        Comment comment1 = new Comment("ㅎㅇ요1", user1, post);
        Comment comment2 = new Comment("ㅎㅇ요2", user1, post);
        Comment comment3 = new Comment("ㅎㅇ요3", user1, post);

        comment2.addReply(comment3);
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        postRepository.save(post);
        em.flush();
        em.clear();
    }

    @Test
    public void comment제거() throws Exception {
        //given
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        for (Comment comment : comments) {
            System.out.println("comment = " + comment);
            if (!comment.getReplies().isEmpty()) {
                for (Comment reply : comment.getReplies()) {
                    System.out.println("reply = " + reply);
                }
            }
        }

        for (Comment comment : comments) {
            // 자식 댓글의 parentComment 참조를 null로 설정
            for (Comment reply : comment.getReplies()) {
                reply.setParentComment(null);
            }
            // 자식 댓글 목록을 비움
            comment.getReplies().clear();

            // Comment가 부모 댓글을 가지고 있다면, 해당 부모 댓글의 자식 목록에서 현재 Comment를 제거
            if (comment.getParentComment() != null) {
                comment.getParentComment().getReplies().remove(comment);
            }

            // Comment 엔티티 제거
            commentRepository.delete(comment);
        }


        //when

        //then

    }
}