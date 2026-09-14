package com.andef.daily.planner.frontend.feature.auth.data.dtos

import kotlinx.serialization.Serializable

/**
 * Данные входа
 */
@Serializable
data class LoginDto(
    val login: String,
    val password: String
)