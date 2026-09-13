package com.andef.daily.planner.backend.domain.exceptions;

import lombok.NonNull;

/**
 * Ошибка отсутствующего мероприятия
 */
public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(@NonNull Long id) {
        super("Мероприятие с идентификатором '%d' не найдено".formatted(id));
    }
}
