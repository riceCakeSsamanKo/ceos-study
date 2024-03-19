package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter
@Entity
@ToString
public class Post extends BaseTimeEntity{
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
    @JoinColumn(name = "umjoonsik")
    private Board board;
}  
