package com.andef.daily.planner.frontend.feature.planner.data.dtos

import kotlinx.serialization.Serializable

/**
 * Данные мероприятия из API
 */
@Serializable
data class EventDto(
    val id: Long,
    val title: String,
    val location: String,
    val startsAt: String,
    val endsAt: String
)