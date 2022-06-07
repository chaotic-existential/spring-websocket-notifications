package ru.solnyshko.notifications.services;

import ru.solnyshko.notifications.payload.dto.NotificationDTO;
import ru.solnyshko.notifications.payload.request.NotificationCreateRequest;
import ru.solnyshko.notifications.payload.request.NotificationUpdateRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {

    List<NotificationDTO> getAll();
    NotificationDTO getById(Long id);

    NotificationDTO create(NotificationCreateRequest notificationCreateRequest);
    NotificationDTO update(Long id, NotificationUpdateRequest notificationUpdateRequest);

    void schedule(Long id, LocalDateTime scheduledTime);

    void send(Long id);
    void delete(Long id);
}
