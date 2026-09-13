package com.andef.daily.planner.backend.network.dtos.event;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Параметры выборки мероприятий
 *
 * @param day        Выбранный день
 * @param searchText Поисковая строка
 */
public record EventFilterDto(
        @NotNull LocalDate day,
        String searchText
) {
}
