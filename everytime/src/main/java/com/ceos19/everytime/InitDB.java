package com.ceos19.everytime;


import com.ceos19.everytime.domain.Post;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct  // 초기화 콜백
    public void init() {
        initService.postInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void postInit() {
            Post post = new Post("title...", "content...");
            em.persist(post);
        }
    }
}
