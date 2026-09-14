package com.andef.daily.planner.frontend.feature.planner.domain.usecases

import com.andef.daily.planner.frontend.feature.planner.domain.repositories.EventRepository

/**
 * Возвращает мероприятия
 */
class GetEventsUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(day: String, searchText: String?) = repository.getEvents(day, searchText)
}