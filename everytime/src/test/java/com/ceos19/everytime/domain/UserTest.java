package com.ceos19.everytime.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class UserTest{

    @Test
    public void postTest() throws Exception{
        //given
        User user = new User();
        Post post1 = new Post("1","2");
        Post post2 = new Post("1","2");

        //when
        user.addPost(post1);
        user.addPost(post2);

        //then
        Assert.assertEquals(user.getPosts().size(),2);
    }

    @Test
    public void messageTest() throws Exception{
        //given
        Message message1 = new Message("title1", "content1");
        Message message2 = new Message("title2", "content2");

        User user1 = new User("user1", 123, "asdf", "123");
        User user2 = new User("user2", 123, "asdf", "123");

        //when
        user1.addSendMessage(message1);
        user2.addReceiveMessage(message1);

        //then
        List<SendMessage> sendMessages = user1.getSendMessages();
        for (SendMessage sendMessage : sendMessages) {
            System.out.println("sendMessage = " + sendMessage);
        }
        List<ReceiveMessage> receiveMessages = user1.getReceiveMessages();
        for (ReceiveMessage receiveMessage : receiveMessages) {
            System.out.println("receiveMessage = " + receiveMessage);
        }

    }
}