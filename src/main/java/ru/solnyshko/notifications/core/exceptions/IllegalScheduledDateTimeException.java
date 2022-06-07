package ru.solnyshko.notifications.core.exceptions;

import java.time.LocalDateTime;

public class IllegalScheduledDateTimeException extends RuntimeException {

    public IllegalScheduledDateTimeException(LocalDateTime ldt) {
        super("Illegal datetime (" + ldt + ") for notification scheduling");
    }
}
