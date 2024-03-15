package com.ceos19.everytime.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.ceos19.everytime.domain.*;
import jakarta.persistence.EntityManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class UserRepositoryTest{

    @Autowired
    UserRepository userRepository;
    @Autowired
    SchoolRepository schoolRepository;
    @Autowired
    PostRepository postRepository;

    @Autowired
    EntityManager em;

    School school;

    User testUser;
    Post testPost;

    @Before
    public void init() {
        school = new School("school", "department");
        schoolRepository.save(school);

        testUser = new User("엄준식", 1111, "umjoonsik@naver.com", "pass");
        userRepository.save(testUser);

        testPost = new Post("title", "content");
        postRepository.save(testPost);
    }

    @Test
    public void test1() throws Exception{
        //given
        User user = new User("user",123,"mmm@gmail.com","asdf",school);

        //when
        userRepository.save(user);
        List<User> all = userRepository.findAll();

        //then

        assertEquals(user.getSchool().getName(),"school");
    }

    @Test
    public void test2() throws Exception{
        //given
        User user = new User("user",123,"mmm@gmail.com","asdf",school);

        //when
        userRepository.save(user);

        //then
        assertEquals(userRepository.findByName("user").get().getName(), "user");
    }

    @Test
    public void test3() throws Exception{
        //given
        User user1 = new User("user1",123,"mmm@gmail.com","asdf",school);
        User user2 = new User("user2",1234,"mmm123@gmail.com","asdf",school);
        userRepository.save(user1);
        userRepository.save(user2);

        Message message = new Message("title", "content");

        //when
        Optional<User> byId1 = userRepository.findById(user1.getId());
        Optional<User> byId2 = userRepository.findById(user2.getId());

        User findUser1 = byId1.get();
        User findUser2 = byId2.get();

        findUser1.addSendMessage(message);
        findUser2.addReceiveMessage(message);

        //then
        System.out.println("id=" + message.getId());
    }
    @Test
    public void messageTest() throws Exception{
        //given
        User sender = new User("user1",123,"mmm@gmail.com","asdf",school);
        User receiver = new User("user2",1234,"mmm123@gmail.com","asdf",school);
        Message message = new Message("title", "content");

        sender.addSendMessage(message);
        receiver.addReceiveMessage(message);

        //when
        userRepository.save(sender);
        userRepository.save(receiver);

        //then
        List<SendMessage> sendMessages = sender.getSendMessages();
        for (SendMessage sendMessage : sendMessages) {
            System.out.println("sendMessage = " + sendMessage);
        }

        System.out.println("message.getSendMessage=" + message.getSendMessage());
        System.out.println("message.getReceiveMessage="+message.getReceiveMessage());

        Assert.assertEquals(sender.getSendMessages().get(0).getMessage().getReceiveMessage().getReceiver().getId(),receiver.getId());
    }

    @Test
    public void addComment() throws Exception{
        //given

        //when
        testUser.addCommentToPost(testPost,"1234");
        userRepository.save(testUser);

        //then
        Assert.assertEquals(testUser.getComments().get(0).getUser().getId(), testUser.getId());
        Assert.assertEquals(testUser.getComments().get(0).getPost().getId(), testPost.getId());

        Comment comment = testUser.getComments().get(0);
        System.out.println("user id:" + testUser.getId());
        System.out.println("post id:" + testPost.getId());
        System.out.println("comment id:"+comment.getId());
        System.out.println("comment.user id:" + comment.getUser().getId());
        System.out.println("comment.post id:" + comment.getPost().getId());

    }

    @Test
    public void commentTest() throws Exception{
        //given
        //when
        testUser.addCommentToPost(testPost, "hello");
        userRepository.save(testUser);

        //then
        Assert.assertEquals(testUser.getComments().get(0).getId(), testPost.getComments().get(0).getId());
    }

    @Test
    public void replyTest() throws Exception{
        //given
        testUser.addCommentToPost(testPost, "hello");
        userRepository.save(testUser);
        Comment comment = testUser.getComments().get(0);

        //when
        testUser.addReplyToComment(comment, "good!");
        userRepository.save(testUser);

        //then
        Assert.assertEquals(testUser.getReplies().get(0).getId(),comment.getReplies().get(0).getId());
        System.out.println(comment.getReplies().get(0).getId());
        System.out.println(comment.getReplies().get(0).getContent());
    }
}