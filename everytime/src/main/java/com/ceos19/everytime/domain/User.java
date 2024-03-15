package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@ToString(exclude = {"school", "posts", "hearts", "sendMessages", "receiveMessages", "comments", "replies"})
public class User {  // 유저
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private int studentNo;

    @Email
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "school_id")
    @Setter(value = PROTECTED)
    private School school;

    public User(String name, int studentNo, String username, String password) {
        this.name = name;
        this.studentNo = studentNo;
        this.username = username;
        this.password = password;
    }

    public User(String name, int studentNo, String username, String password, School school) {
        this.name = name;
        this.studentNo = studentNo;
        this.username = username;
        this.password = password;
        this.school = school;
    }

    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = PERSIST, orphanRemoval = true) //유저가 제거되도 게시물의 좋아요는 유지됨.
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = ALL, orphanRemoval = true)
    private List<SendMessage> sendMessages = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = ALL, orphanRemoval = true)
    private List<ReceiveMessage> receiveMessages = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    /**
     * 연관관계 편의 메서드
     */
    public void addPost(Post post) {
        post.setUser(this);
        posts.add(post);
    }

    // User + 중간 테이블 + Message간의 연관관계를 매핑해주는 메서드

    public void addSendMessage(Message message) {
        SendMessage sendMessage = SendMessage.createSendMessage(message);

        sendMessage.setSender(this);
        sendMessages.add(sendMessage);
    }
    // User + 중간 테이블 + Message간의 연관관계를 매핑해주는 메서드
    public void addReceiveMessage(Message message) {
        ReceiveMessage receiveMessage = ReceiveMessage.createReceiveMessage(message);

        receiveMessage.setReceiver(this);
        receiveMessages.add(receiveMessage);
    }

    // 게시물의 댓글은 User에서 다룸
    public void addCommentToPost(Post post, String commentContent) {
        Comment comment = Comment.createComment(post, commentContent);
        comment.setUser(this);
        comments.add(comment);
    }
    // 댓글의 대댓글은 User에서 다룸
    public void addReplyToComment(Comment comment, String replyContent) {
        Reply reply = Reply.createReply(comment, replyContent);
        reply.setUser(this);
        replies.add(reply);
    }

    protected void addHearts(Heart heart) {
        heart.setUser(this);
        hearts.add(heart);
    }
}
