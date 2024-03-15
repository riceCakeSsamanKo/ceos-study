package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@ToString(exclude = {"user", "post", "replies"})
public class Comment extends BaseTimeEntity {  // 댓글
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private Comment(String content) {
        this.content = content;
    }

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @Setter(value = PROTECTED)
    private User user;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "post_id")
    @Setter(value = PROTECTED)
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    /**
     * 연관관계 편의 메서드
     */
    protected static Comment createComment(Post post, String content) {
        Comment comment = new Comment(content);
        post.addComment(comment);

        return comment;
    }

    protected void addReply(Reply reply) {
        reply.setComment(this);
        replies.add(reply);
    }
}
