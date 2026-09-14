package com.andef.daily.planner.frontend.feature.auth.presentation

/**
 * Намерение экрана аутентификации
 */
sealed interface AuthIntent {
    data class ModeChange(val mode: AuthMode) : AuthIntent
    data class LoginChange(val value: String) : AuthIntent
    data class PasswordChange(val value: String) : AuthIntent
    data class RepeatedPasswordChange(val value: String) : AuthIntent
    data object PasswordVisibilityChange : AuthIntent
    data object ErrorDismiss : AuthIntent
    data class Submit(val onSuccess: () -> Unit) : AuthIntent
}
