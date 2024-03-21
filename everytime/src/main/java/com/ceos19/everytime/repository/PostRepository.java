package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = {"author", "postLikes", "attachments"})
    Optional<Post> findById(Long postId);

    @EntityGraph(attributePaths = {"postLikes", "attachments"})
    List<Post> findByAuthorId(Long userId);

    @EntityGraph(attributePaths = {"author", "postLikes", "attachments"})
    List<Post> findByBoardId(Long boardId);
}
