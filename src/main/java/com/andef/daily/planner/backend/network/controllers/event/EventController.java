package com.andef.daily.planner.backend.network.controllers.event;

import com.andef.daily.planner.backend.domain.services.event.EventService;
import com.andef.daily.planner.backend.network.dtos.event.CreateEventDto;
import com.andef.daily.planner.backend.network.dtos.event.EventDto;
import com.andef.daily.planner.backend.network.dtos.event.EventFilterDto;
import com.andef.daily.planner.backend.network.dtos.event.UpdateEventDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер мероприятий
 */
@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    /**
     * Возвращает мероприятия пользователя
     *
     * @param authentication Данные аутентификации
     * @param filter         Параметры выборки
     * @return Мероприятия пользователя
     */
    @GetMapping
    public ResponseEntity<List<EventDto>> findAllByDay(
            Authentication authentication,
            @Valid @ModelAttribute EventFilterDto filter
    ) {
        return ResponseEntity.ok(eventService.findAllByDay(authentication, filter));
    }

    /**
     * Создаёт мероприятие
     *
     * @param authentication Данные аутентификации
     * @param request        Данные создания
     * @return Созданное мероприятие
     */
    @PostMapping
    public ResponseEntity<EventDto> create(
            Authentication authentication,
            @Valid @RequestBody CreateEventDto request
    ) {
        return ResponseEntity.ok(eventService.create(authentication, request));
    }

    /**
     * Изменяет мероприятие
     *
     * @param authentication Данные аутентификации
     * @param eventId        Идентификатор мероприятия
     * @param request        Данные изменения
     * @return Изменённое мероприятие
     */
    @PutMapping("/{eventId}")
    public ResponseEntity<EventDto> update(
            Authentication authentication,
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventDto request
    ) {
        EventDto dto = eventService.update(authentication, eventId, request);

        return ResponseEntity.ok(dto);
    }

    /**
     * Удаляет мероприятие
     *
     * @param authentication Данные аутентификации
     * @param eventId        Идентификатор мероприятия
     * @return Пустой ответ
     */
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> delete(Authentication authentication, @PathVariable Long eventId) {
        eventService.delete(authentication, eventId);

        return ResponseEntity.noContent().build();
    }
}
