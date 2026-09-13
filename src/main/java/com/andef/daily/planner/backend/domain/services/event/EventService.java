package com.andef.daily.planner.backend.domain.services.event;

import com.andef.daily.planner.backend.data.entities.Event;
import com.andef.daily.planner.backend.data.entities.User;
import com.andef.daily.planner.backend.network.dtos.event.CreateEventRequestDto;
import com.andef.daily.planner.backend.network.dtos.event.EventFilterDto;
import com.andef.daily.planner.backend.network.dtos.event.EventResponseDto;
import com.andef.daily.planner.backend.network.dtos.event.UpdateEventRequestDto;

import java.util.List;

/**
 * Сервис для {@link Event}
 */
public interface EventService {

    /**
     * Возвращает мероприятия пользователя за выбранный день
     *
     * @param user   Пользователь
     * @param filter Параметры выборки
     * @return Данные мероприятий по времени начала
     */
    List<EventResponseDto> findAllByDay(User user, EventFilterDto filter);

    /**
     * Создаёт мероприятие пользователя
     *
     * @param user    Пользователь
     * @param request Данные создания
     * @return Данные созданного мероприятия
     */
    EventResponseDto create(User user, CreateEventRequestDto request);

    /**
     * Изменяет мероприятие пользователя
     *
     * @param user    Пользователь
     * @param eventId Идентификатор мероприятия
     * @param request Данные изменения
     * @return Данные изменённого мероприятия
     */
    EventResponseDto update(User user, Long eventId, UpdateEventRequestDto request);

    /**
     * Удаляет мероприятие пользователя
     *
     * @param user    Пользователь
     * @param eventId Идентификатор мероприятия
     */
    void delete(User user, Long eventId);
}
