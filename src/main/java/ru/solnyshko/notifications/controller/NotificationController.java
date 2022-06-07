package ru.solnyshko.notifications.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solnyshko.notifications.payload.dto.NotificationDTO;
import ru.solnyshko.notifications.payload.request.NotificationCreateRequest;
import ru.solnyshko.notifications.payload.request.NotificationUpdateRequest;
import ru.solnyshko.notifications.services.NotificationService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notifications")
public class NotificationController {

    private final NotificationService service;

    @GetMapping
    public List<NotificationDTO> getAll() {
        return service.getAll();
    }

    @PostMapping
    public NotificationDTO create(
            @RequestBody @Valid NotificationCreateRequest notificationCreateRequest) {
        return service.create(notificationCreateRequest);
    }

    @PutMapping("/{id}")
    public NotificationDTO update(
            @PathVariable Long id,
            @RequestBody @Valid NotificationUpdateRequest notificationUpdateRequest) {
        return service.update(id, notificationUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Notification has been deleted");
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<String> send(@PathVariable Long id) {
        service.send(id);
        return ResponseEntity.ok("Notification has been sent");
    }

    @PostMapping("/{id}/schedule")
    public ResponseEntity<String> schedule(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime scheduledTime) {
        service.schedule(id, scheduledTime);
        return ResponseEntity.ok("Notification has been scheduled for " + scheduledTime);
    }
}
