package com.ceos19.everytime.domain;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@ToString(exclude = "receiver")
public class ReceiveMessage {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "receive_message_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @Setter(value = PROTECTED)
    private User receiver;

    @OneToOne(fetch = LAZY, cascade = PERSIST)
    private Message message;

    // ReceiveMessage의 생성은 이 메서드가 담당
    protected static ReceiveMessage createReceiveMessage(Message message) {
        ReceiveMessage receiveMessage = new ReceiveMessage();
        message.setReceiveMessage(receiveMessage);
        receiveMessage.message = message;

        return receiveMessage;
    }
}
