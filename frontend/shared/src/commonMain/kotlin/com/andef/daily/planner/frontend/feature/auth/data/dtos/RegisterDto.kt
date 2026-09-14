package com.andef.daily.planner.frontend.feature.auth.data.dtos

import kotlinx.serialization.Serializable

/**
 * Данные регистрации
 */
@Serializable
data class RegisterDto(
    val login: String,
    val password: String
)