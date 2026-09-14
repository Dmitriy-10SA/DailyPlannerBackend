package com.andef.daily.planner.frontend.core.data

import kotlinx.serialization.Serializable

/**
 * Ответ с ошибкой API
 */
@Serializable
data class ErrorResponseDto(
    val status: Int,
    val message: String
)