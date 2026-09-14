package com.andef.daily.planner.backend.domain.mappers;

import com.andef.daily.planner.backend.data.entities.Event;
import com.andef.daily.planner.backend.data.entities.User;
import com.andef.daily.planner.backend.network.dtos.event.CreateEventDto;
import com.andef.daily.planner.backend.network.dtos.event.EventDto;
import com.andef.daily.planner.backend.network.dtos.event.UpdateEventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Маппер мероприятий
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {

    /**
     * Преобразует данные создания в мероприятие
     *
     * @param user    Пользователь
     * @param request Данные создания
     * @return Мероприятие
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "title", source = "request.title")
    @Mapping(target = "location", source = "request.location")
    @Mapping(target = "startsAt", source = "request.startsAt")
    @Mapping(target = "endsAt", source = "request.endsAt")
    Event toEntity(User user, CreateEventDto request);

    /**
     * Переносит данные изменения в мероприятие
     *
     * @param event   Мероприятие
     * @param request Данные изменения
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(UpdateEventDto request, @MappingTarget Event event);

    /**
     * Преобразует мероприятие в данные ответа
     *
     * @param event Мероприятие
     * @return Данные мероприятия
     */
    EventDto toResponse(Event event);

    /**
     * Преобразует мероприятия в данные ответа
     *
     * @param events Мероприятия
     * @return Данные мероприятий
     */
    List<EventDto> toResponseList(List<Event> events);
}
