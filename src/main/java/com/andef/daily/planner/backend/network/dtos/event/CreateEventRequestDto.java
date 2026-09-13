package com.andef.daily.planner.backend.network.dtos.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Данные создания мероприятия
 *
 * @param title    Название
 * @param location Место проведения
 * @param startsAt Дата и время начала
 * @param endsAt   Дата и время окончания
 */
public record CreateEventRequestDto(
        @NotBlank @Size(max = 100) String title,
        @NotBlank @Size(max = 300) String location,
        @NotNull LocalDateTime startsAt,
        @NotNull LocalDateTime endsAt
) {
}
