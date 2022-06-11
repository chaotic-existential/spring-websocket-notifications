package ru.solnyshko.notifications.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.solnyshko.notifications.domain.entity.NotificationType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class NotificationUpdateRequest {

    @NotNull
    private NotificationType type;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    private String content;
}
