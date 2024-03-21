package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@ToString(exclude = {"commenter", "post", "parentComment", "replies"})
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User commenter;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comment_id")
    @Setter
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = ALL, orphanRemoval = true)
    private List<Comment> replies = new ArrayList<>();

    public void addReply(Comment comment) {
        comment.parentComment = this;
        replies.add(comment);
    }

    public Comment(String content, User commenter, Post post) {
        this.content = content;
        this.commenter = commenter;
        this.post = post;
    }
}
