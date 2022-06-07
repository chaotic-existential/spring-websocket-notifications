package ru.solnyshko.notifications.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solnyshko.notifications.domain.entity.Notification;

@Repository
public interface NotificationRepository
        extends JpaRepository<Notification, Long> {
}
