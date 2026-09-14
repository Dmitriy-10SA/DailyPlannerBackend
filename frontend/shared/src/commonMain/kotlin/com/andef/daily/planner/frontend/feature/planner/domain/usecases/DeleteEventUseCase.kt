package com.andef.daily.planner.frontend.feature.planner.domain.usecases

import com.andef.daily.planner.frontend.feature.planner.domain.repositories.EventRepository

/**
 * Удаляет мероприятие
 */
class DeleteEventUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(id: Long) = repository.delete(id)
}