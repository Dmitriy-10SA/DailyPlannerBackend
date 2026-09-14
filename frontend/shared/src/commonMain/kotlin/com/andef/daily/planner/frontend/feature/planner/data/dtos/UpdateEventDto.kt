package com.andef.daily.planner.frontend.feature.planner.data.dtos

import kotlinx.serialization.Serializable

/**
 * Данные изменения мероприятия
 */
@Serializable
data class UpdateEventDto(
    val title: String,
    val location: String,
    val startsAt: String,
    val endsAt: String
)