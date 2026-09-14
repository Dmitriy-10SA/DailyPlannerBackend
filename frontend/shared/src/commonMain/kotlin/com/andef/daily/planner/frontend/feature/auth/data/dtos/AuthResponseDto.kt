package com.andef.daily.planner.frontend.feature.auth.data.dtos

import kotlinx.serialization.Serializable

/**
 * Ответ аутентификации
 */
@Serializable
data class AuthResponseDto(
    val jwt: String
)