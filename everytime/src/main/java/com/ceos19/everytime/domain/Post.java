package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter
@Entity
@ToString
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(length = 1000)
    private String content;

    private boolean isQuestion;
    private boolean isAnonymous;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id") // join 한 user의 fk 명을 "user_id"로 지정. 연관관계 매핑에는 @JoinColumn은 영향을 주지 않음.
    private User author;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Post(String title, String content, boolean isQuestion, boolean isAnonymous, User author, Board board) {
        this.title = title;
        this.content = content;
        this.isQuestion = isQuestion;
        this.isAnonymous = isAnonymous;
        this.author = author;
        this.board = board;
    }

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<PostLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = ALL)
    private List<Attachment> attachments = new ArrayList<>();
}
