package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PostRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    User user;
    Post post;

    @Before
    public void each() {
        user = new User("user", 1231451254, "asdf@asdf.com", "asdf");
        post = new Post("title", "content");

        userRepository.save(user);
        postRepository.save(post);
    }

    @Test
    public void heartTest() throws Exception {
        //given
        System.out.println(post);

        //when
        post.addHeartFromUser(user);
        postRepository.save(post);

        //then
        Assert.assertEquals(post.getHearts().get(0).getId(), user.getHearts().get(0).getId());
    }
}
