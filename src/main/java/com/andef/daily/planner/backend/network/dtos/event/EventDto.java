package com.andef.daily.planner.backend.network.dtos.event;

import java.time.LocalDateTime;

/**
 * Данные мероприятия
 *
 * @param id       Идентификатор
 * @param title    Название
 * @param location Место проведения
 * @param startsAt Дата и время начала
 * @param endsAt   Дата и время окончания
 */
public record EventDto(
        Long id,
        String title,
        String location,
        LocalDateTime startsAt,
        LocalDateTime endsAt
) {
}
