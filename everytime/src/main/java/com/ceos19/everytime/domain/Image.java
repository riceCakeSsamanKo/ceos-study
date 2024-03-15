package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
@ToString(exclude = "post")
public class Image {  // 게시물에 달 사진
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private long imageIdx;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String storedFileName;

    private long fileSize;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    @Setter(value = PROTECTED)
    private Post post;
}
