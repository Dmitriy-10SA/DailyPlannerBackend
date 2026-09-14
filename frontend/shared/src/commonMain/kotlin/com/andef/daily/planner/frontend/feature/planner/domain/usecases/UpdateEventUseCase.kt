package com.andef.daily.planner.frontend.feature.planner.domain.usecases

import com.andef.daily.planner.frontend.feature.planner.domain.model.EventDraft
import com.andef.daily.planner.frontend.feature.planner.domain.repositories.EventRepository

/**
 * Изменяет мероприятие
 */
class UpdateEventUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(id: Long, event: EventDraft) = repository.update(
        id,
        event.title,
        event.location,
        event.startsAt,
        event.endsAt
    )
}