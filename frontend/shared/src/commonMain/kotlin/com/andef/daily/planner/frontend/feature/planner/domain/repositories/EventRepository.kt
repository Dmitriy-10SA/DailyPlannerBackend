package com.andef.daily.planner.frontend.feature.planner.domain.repositories

import com.andef.daily.planner.frontend.feature.planner.domain.model.Event

/**
 * Репозиторий мероприятий
 */
interface EventRepository {

    /**
     * Возвращает мероприятия выбранного дня
     */
    suspend fun getEvents(day: String, searchText: String?): List<Event>

    /**
     * Создаёт мероприятие
     */
    suspend fun create(title: String, location: String, startsAt: String, endsAt: String): Event

    /**
     * Изменяет мероприятие
     */
    suspend fun update(
        id: Long,
        title: String,
        location: String,
        startsAt: String,
        endsAt: String
    ): Event

    /**
     * Удаляет мероприятие
     */
    suspend fun delete(id: Long)
}