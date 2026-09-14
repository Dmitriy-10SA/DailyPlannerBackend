package com.andef.daily.planner.frontend.feature.planner.domain.model

/**
 * Мероприятие
 */
data class Event(
    val id: Long,
    val title: String,
    val location: String,
    val startsAt: String,
    val endsAt: String
)
