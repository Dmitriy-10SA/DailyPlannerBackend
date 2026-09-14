package com.andef.daily.planner.frontend.feature.planner.domain.usecases

import com.andef.daily.planner.frontend.feature.planner.domain.model.EventDraft
import com.andef.daily.planner.frontend.feature.planner.domain.repositories.EventRepository

/**
 * Создаёт мероприятие
 */
class CreateEventUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(event: EventDraft) = repository.create(
        event.title,
        event.location,
        event.startsAt,
        event.endsAt
    )
}