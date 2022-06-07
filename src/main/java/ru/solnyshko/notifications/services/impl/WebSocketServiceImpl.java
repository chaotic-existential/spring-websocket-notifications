package ru.solnyshko.notifications.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.solnyshko.notifications.payload.dto.NotificationDTO;
import ru.solnyshko.notifications.services.WebSocketService;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketServiceImpl implements WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(NotificationDTO notificationDto) {
        log.info("Server sends notification: {}", notificationDto.toString());
        messagingTemplate.convertAndSend("/topic/notifications", notificationDto);
    }
}
