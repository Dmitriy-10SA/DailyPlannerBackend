package com.andef.daily.planner.frontend.feature.auth.data.dtos

import kotlinx.serialization.Serializable

/**
 * Данные смены пароля
 */
@Serializable
data class ChangePasswordDto(
    val login: String,
    val newPassword: String
)