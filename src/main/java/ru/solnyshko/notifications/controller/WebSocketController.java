package ru.solnyshko.notifications.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.solnyshko.notifications.services.NotificationService;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final NotificationService notificationService;

    @MessageMapping("/{id}/send")
    @SendTo("/topic/notifications")
    public void send(@DestinationVariable Long id) {
        notificationService.send(id);
    }

    @MessageMapping("/{id}/schedule")
    @SendTo("/topic/notifications")
    public void schedule(@DestinationVariable Long id, @Payload String payload) {
        notificationService.schedule(id, LocalDateTime.parse(payload));
    }
}
