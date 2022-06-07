package ru.solnyshko.notifications.core.exceptions;

public class NoSuchRecordException extends RuntimeException {

    public NoSuchRecordException(Class<?> _class, Long id) {
        super("No record of " + _class.getSimpleName() + " found with id " + id);
    }
}
