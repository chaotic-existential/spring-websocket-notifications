package ru.solnyshko.notifications.factory;

import ru.solnyshko.notifications.domain.entity.Notification;
import ru.solnyshko.notifications.domain.entity.NotificationType;
import ru.solnyshko.notifications.payload.dto.NotificationDTO;
import ru.solnyshko.notifications.payload.request.NotificationCreateRequest;
import ru.solnyshko.notifications.payload.request.NotificationUpdateRequest;

public class TestNotificationFactory {

    private final NotificationType type = NotificationType.SUCCESS;

    private final String title = "Test title";
    private final String content = "Test content";

    public Notification getNotification(Long id) {
        return new Notification(id, type, title, content, null);
    }

    public NotificationDTO getNotificationDTO(Long id) {
        return new NotificationDTO(id, type, title, content, null);
    }

    public NotificationCreateRequest getNotificationCreateRequest() {
        return new NotificationCreateRequest(type, title, content);
    }

    public NotificationUpdateRequest getNotificationUpdateRequest() {
        return new NotificationUpdateRequest(type, title, content);
    }
}
