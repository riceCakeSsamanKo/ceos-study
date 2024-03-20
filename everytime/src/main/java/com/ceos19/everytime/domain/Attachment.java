package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "post")
public class Attachment {  // 게시물에 달 사진
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    @Column(nullable = false)
    private String originalFileName;
    @Column(nullable = false)
    private String storedFileName;

    @Enumerated(STRING)
    private AttachmentType attachmentType;

    public Attachment(String originalFileName, String storedFileName, AttachmentType attachmentType, Post post) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.attachmentType = attachmentType;
        this.post = post;
    }

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Attachment(Long id, String originFileName, String storePath, AttachmentType attachmentType) {
        this.id = id;
        this.originalFileName = originFileName;
        this.storedFileName = storePath;
        this.attachmentType = attachmentType;
    }
}
