package ru.solnyshko.notifications.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import ru.solnyshko.notifications.domain.entity.Notification;
import ru.solnyshko.notifications.core.exceptions.IllegalScheduledDateTimeException;
import ru.solnyshko.notifications.core.exceptions.NoSuchRecordException;
import ru.solnyshko.notifications.domain.mapper.NotificationMapper;
import ru.solnyshko.notifications.payload.dto.NotificationDTO;
import ru.solnyshko.notifications.payload.request.NotificationCreateRequest;
import ru.solnyshko.notifications.payload.request.NotificationUpdateRequest;
import ru.solnyshko.notifications.domain.repository.NotificationRepository;
import ru.solnyshko.notifications.services.NotificationService;
import ru.solnyshko.notifications.services.WebSocketService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final WebSocketService webSocketService;
    private final TaskScheduler taskScheduler;

    private final NotificationRepository repository;
    private final NotificationMapper mapper;

    @RequiredArgsConstructor
    private class ScheduledNotificationSending implements Runnable {
        private final Long notificationId;

        @Override
        public void run() {
            send(notificationId);
        }
    }

    public List<NotificationDTO> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public NotificationDTO getById(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new NoSuchRecordException(Notification.class, id);
        }

        return mapper.toDto(
                repository.findById(id).get());
    }

    public NotificationDTO create(NotificationCreateRequest notificationCreateRequest) {
        Notification notification = mapper.toEntity(notificationCreateRequest);

        return mapper.toDto(
                repository.save(notification));
    }

    public NotificationDTO update(Long id, NotificationUpdateRequest notificationUpdateRequest) {
        if (repository.findById(id).isEmpty()) {
            throw new NoSuchRecordException(Notification.class, id);
        }

        Notification notification = repository.findById(id).get();
        mapper.update(notificationUpdateRequest, notification);

        return mapper.toDto(
                repository.save(notification));
    }

    public void delete(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new NoSuchRecordException(Notification.class, id);
        }

        repository.deleteById(id);
    }

    public void send(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new NoSuchRecordException(Notification.class, id);
        }

        Notification notification = repository.findById(id).get();
        NotificationDTO notificationDto = mapper.toDto(notification);

        webSocketService.sendNotification(notificationDto);
        notification.setLastSentAt(LocalDateTime.now());

        repository.save(notification);
        log.info("Notification with id {} has been sent", id);
    }

    public void schedule(Long id, LocalDateTime scheduledTime) {
        if (repository.findById(id).isEmpty()) {
            throw new NoSuchRecordException(Notification.class, id);
        }
        if (scheduledTime.isBefore(LocalDateTime.now())) {
            throw new IllegalScheduledDateTimeException(scheduledTime);
        }

        Date scheduledDate = Date.from(
                scheduledTime.atZone(ZoneId.systemDefault()).toInstant());

        taskScheduler.schedule(new ScheduledNotificationSending(id), scheduledDate);
        log.info("Notification with id {} has been scheduled for {}", id, scheduledTime);
    }
}

