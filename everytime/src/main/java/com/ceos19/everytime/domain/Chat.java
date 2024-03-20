package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
@ToString
public class Chat{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime sentAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "conversation_id")
    @Setter(value = PROTECTED)
    private ChattingRoom chattingRoom;

    @Builder
    public Chat(String content, LocalDateTime sentAt, User author) {
        this.content = content;
        this.sentAt = sentAt;
        this.author = author;
    }
}
