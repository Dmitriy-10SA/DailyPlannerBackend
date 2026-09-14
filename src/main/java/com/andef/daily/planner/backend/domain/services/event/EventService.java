package com.andef.daily.planner.backend.domain.services.event;

import com.andef.daily.planner.backend.data.entities.Event;
import com.andef.daily.planner.backend.network.dtos.event.CreateEventDto;
import com.andef.daily.planner.backend.network.dtos.event.EventDto;
import com.andef.daily.planner.backend.network.dtos.event.EventFilterDto;
import com.andef.daily.planner.backend.network.dtos.event.UpdateEventDto;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Сервис для {@link Event}
 */
public interface EventService {

    /**
     * Возвращает мероприятия пользователя
     *
     * @param authentication Данные аутентификации
     * @param filter         Параметры выборки
     * @return Мероприятия пользователя
     */
    List<EventDto> findAllByDay(Authentication authentication, EventFilterDto filter);

    /**
     * Создаёт мероприятие
     *
     * @param authentication Данные аутентификации
     * @param request        Данные создания
     * @return Созданное мероприятие
     */
    EventDto create(Authentication authentication, CreateEventDto request);

    /**
     * Изменяет мероприятие
     *
     * @param authentication Данные аутентификации
     * @param eventId        Идентификатор мероприятия
     * @param request        Данные изменения
     * @return Изменённое мероприятие
     */
    EventDto update(Authentication authentication, Long eventId, UpdateEventDto request);

    /**
     * Удаляет мероприятие
     *
     * @param authentication Данные аутентификации
     * @param eventId        Идентификатор мероприятия
     */
    void delete(Authentication authentication, Long eventId);
}
