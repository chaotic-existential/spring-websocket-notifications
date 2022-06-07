package ru.solnyshko.notifications.services;

import ru.solnyshko.notifications.payload.dto.NotificationDTO;

public interface WebSocketService {
    void sendNotification(NotificationDTO notificationDTO);
}
