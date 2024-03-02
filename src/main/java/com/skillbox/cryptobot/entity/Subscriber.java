package com.skillbox.cryptobot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscribers")
public class Subscriber {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "subscriber_amount")
    private Double subscriberAmount;

    @Column(name = "last_notification")
    private LocalDateTime lastNotification;
}
