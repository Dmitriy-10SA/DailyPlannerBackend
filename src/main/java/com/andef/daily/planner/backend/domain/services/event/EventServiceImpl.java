package com.andef.daily.planner.backend.domain.services.event;

import com.andef.daily.planner.backend.data.entities.Event;
import com.andef.daily.planner.backend.data.entities.User;
import com.andef.daily.planner.backend.data.repositories.EventRepository;
import com.andef.daily.planner.backend.domain.exceptions.EventNotFoundException;
import com.andef.daily.planner.backend.domain.mappers.EventMapper;
import com.andef.daily.planner.backend.network.dtos.event.CreateEventRequestDto;
import com.andef.daily.planner.backend.network.dtos.event.EventFilterDto;
import com.andef.daily.planner.backend.network.dtos.event.EventResponseDto;
import com.andef.daily.planner.backend.network.dtos.event.UpdateEventRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Реализация сервиса {@link EventService}
 */
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Transactional(readOnly = true)
    @Override
    public List<EventResponseDto> findAllByDay(User user, EventFilterDto filter) {
        List<Event> events = eventRepository
                .findAllByUserAndDayAndSearchText(user, filter.day(), normalizeSearchText(filter.searchText()));

        return eventMapper.toResponseList(events);
    }

    @Transactional
    @Override
    public EventResponseDto create(User user, CreateEventRequestDto request) {
        validateTimeRange(request.startsAt(), request.endsAt());

        Event event = eventRepository.save(eventMapper.toEntity(user, request));

        return eventMapper.toResponse(event);
    }

    @Transactional
    @Override
    public EventResponseDto update(User user, Long eventId, UpdateEventRequestDto request) {
        validateTimeRange(request.startsAt(), request.endsAt());

        Event event = getUserEvent(user, eventId);

        eventMapper.updateEntity(request, event);

        return eventMapper.toResponse(eventRepository.save(event));
    }

    @Transactional
    @Override
    public void delete(User user, Long eventId) {
        eventRepository.delete(getUserEvent(user, eventId));
    }

    /**
     * Находит мероприятие пользователя
     *
     * @param user    Пользователь
     * @param eventId Идентификатор мероприятия
     * @return Найденное мероприятие
     */
    private Event getUserEvent(User user, Long eventId) {
        return eventRepository.findByIdAndUser(eventId, user)
                .orElseThrow(() -> new EventNotFoundException(eventId));
    }

    /**
     * Нормализует поисковую строку
     *
     * @param searchText Поисковая строка
     * @return Нормализованная поисковая строка
     */
    private String normalizeSearchText(String searchText) {
        if (searchText == null || searchText.isBlank()) {
            return null;
        }

        return searchText.strip();
    }

    /**
     * Проверяет временной диапазон мероприятия
     *
     * @param startsAt Дата и время начала
     * @param endsAt   Дата и время окончания
     */
    private void validateTimeRange(LocalDateTime startsAt, LocalDateTime endsAt) {
        if (!endsAt.isAfter(startsAt)) {
            throw new IllegalArgumentException("Дата окончания должна быть позже даты начала");
        }
    }
}
