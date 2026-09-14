package com.andef.daily.planner.frontend.feature.planner.data.dtos

import kotlinx.serialization.Serializable

/**
 * Данные создания мероприятия
 */
@Serializable
data class CreateEventDto(
    val title: String,
    val location: String,
    val startsAt: String,
    val endsAt: String
)