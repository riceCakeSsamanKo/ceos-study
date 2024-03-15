package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@ToString(exclude = "sender")
public class SendMessage {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "send_message_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @Setter(value = PROTECTED)
    private User sender;

    @OneToOne(fetch = LAZY, cascade = PERSIST)
    private Message message;

    // SendMessage의 생성은 이 메서드가 담당
    protected static SendMessage createSendMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        message.setSendMessage(sendMessage);
        sendMessage.message = message;

        return sendMessage;
    }
}
