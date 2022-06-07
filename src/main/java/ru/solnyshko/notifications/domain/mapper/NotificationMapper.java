package ru.solnyshko.notifications.domain.mapper;

import org.mapstruct.*;
import ru.solnyshko.notifications.payload.dto.NotificationDTO;
import ru.solnyshko.notifications.domain.entity.Notification;
import ru.solnyshko.notifications.payload.request.NotificationCreateRequest;
import ru.solnyshko.notifications.payload.request.NotificationUpdateRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {
    NotificationDTO toDto(Notification entity);
    Notification toEntity(NotificationCreateRequest payload);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(NotificationUpdateRequest payload, @MappingTarget Notification entity);
}
