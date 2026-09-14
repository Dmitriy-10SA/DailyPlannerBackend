package com.andef.daily.planner.backend.domain.exceptions;

/**
 * Ошибка отсутствующего мероприятия
 */
public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(Long id) {
        super("Мероприятие с идентификатором '%d' не найдено".formatted(id));
    }
}
