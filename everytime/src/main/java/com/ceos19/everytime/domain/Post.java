package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = {"user", "hearts", "comments", "images"})
public class Post extends BaseTimeEntity {  //게시물
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")  //select * from post p join user u on p.user_id = u.id;
    @Setter(value = PROTECTED)
    private User user;

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    /**
     * 연관관계 편의 메서드
     */

    // User, Heart, Post의 연관관계는 Post에서 관리함
    public void addHeartFromUser(User user) {
        Heart heart = Heart.createHeart(user);
        heart.setPost(this);
        hearts.add(heart);
    }

    public void addImage(Image image) {
        image.setPost(this);
        images.add(image);
    }

    protected void addComment(Comment comment) {
        comment.setPost(this);
        comments.add(comment);
    }
}
