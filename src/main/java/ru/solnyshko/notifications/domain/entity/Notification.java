package ru.solnyshko.notifications.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(nullable = false,
            length = 50)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime lastSentAt;
}
