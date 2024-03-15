package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
@ToString(exclude = {"sendMessage", "receiveMessage"})
public class Message {  // 쪽지, message는 user의 비즈니스 연관관계 편의 메서드로 저장함. 별도의 리포지토리 없다.
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "message_id")
    private Long id;

    private String title;
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "send_message_id")
    @Setter(value = PROTECTED)
    private SendMessage sendMessage;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "receive_message_id")
    @Setter(value = PROTECTED)
    private ReceiveMessage receiveMessage;

    public Message(String title, String content) {
        this.title = title;
        this.content = content;
        this.sentAt = LocalDateTime.now();
    }
}
