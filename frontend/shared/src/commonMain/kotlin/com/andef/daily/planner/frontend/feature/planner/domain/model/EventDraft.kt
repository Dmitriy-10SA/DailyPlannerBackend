package com.andef.daily.planner.frontend.feature.planner.domain.model

/**
 * Данные редактирования мероприятия
 */
data class EventDraft(
    val title: String,
    val location: String,
    val startsAt: String,
    val endsAt: String
)
