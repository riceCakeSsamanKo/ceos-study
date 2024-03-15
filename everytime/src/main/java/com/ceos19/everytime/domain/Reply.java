package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@ToString(exclude = {"user", "comment"})
public class Reply extends BaseTimeEntity {  // 대댓글
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @Setter(value = PROTECTED)
    private User user;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "comment_id")
    @Setter(value = PROTECTED)
    private Comment comment;

    private Reply(String content) {
        this.content = content;
    }

    protected static Reply createReply(Comment comment, String content) {
        Reply reply = new Reply(content);
        comment.addReply(reply);

        return reply;
    }
}
