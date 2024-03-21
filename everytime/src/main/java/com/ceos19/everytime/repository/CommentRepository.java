package com.ceos19.everytime.repository;

import com.ceos19.everytime.domain.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @EntityGraph(attributePaths = {"post", "replies"})
    List<Comment> findByCommenterId(Long commenterId);

    @EntityGraph(attributePaths = {"commenter", "replies"})
    List<Comment> findByPostId(Long postId);

    @EntityGraph(attributePaths = {"commenter"})
    List<Comment> findByParentCommentId(Long parentCommentId);
}
