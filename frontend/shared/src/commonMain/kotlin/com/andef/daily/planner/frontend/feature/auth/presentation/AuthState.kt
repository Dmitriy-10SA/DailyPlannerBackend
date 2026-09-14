package com.andef.daily.planner.frontend.feature.auth.presentation

/**
 * Режим экрана аутентификации
 */
enum class AuthMode {
    Login,
    Register,
    ChangePassword
}

/**
 * Состояние экрана аутентификации
 */
data class AuthState(
    val mode: AuthMode = AuthMode.Login,
    val login: String = "",
    val password: String = "",
    val repeatedPassword: String = "",
    val passwordVisible: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
) {
    val passwordConfirmationRequired: Boolean
        get() = mode != AuthMode.Login

    val passwordsMatch: Boolean
        get() = !passwordConfirmationRequired || password == repeatedPassword

    val submitEnabled: Boolean
        get() = login.isNotBlank() && login.length <= 100 && password.isNotBlank() &&
                (!passwordConfirmationRequired || repeatedPassword.isNotBlank()) &&
                passwordsMatch && !loading
}
