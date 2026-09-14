package com.andef.daily.planner.frontend.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.daily.planner.frontend.feature.auth.domain.usecases.ChangePasswordUseCase
import com.andef.daily.planner.frontend.feature.auth.domain.usecases.LoginUseCase
import com.andef.daily.planner.frontend.feature.auth.domain.usecases.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Модель состояния экрана аутентификации
 */
class AuthViewModel(
    private val register: RegisterUseCase,
    private val login: LoginUseCase,
    private val changePassword: ChangePasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    /**
     * Обрабатывает намерение экрана
     */
    fun send(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.ModeChange -> _state.value = AuthState(mode = intent.mode)
            is AuthIntent.LoginChange -> updateState {
                copy(login = intent.value.take(100), error = null)
            }

            is AuthIntent.PasswordChange -> updateState {
                copy(password = intent.value, error = null)
            }

            is AuthIntent.RepeatedPasswordChange -> updateState {
                copy(repeatedPassword = intent.value, error = null)
            }

            AuthIntent.PasswordVisibilityChange -> updateState {
                copy(passwordVisible = !passwordVisible)
            }

            AuthIntent.ErrorDismiss -> updateState { copy(error = null) }
            is AuthIntent.Submit -> submit(intent.onSuccess)
        }
    }

    /**
     * Отправляет данные аутентификации
     */
    private fun submit(onSuccess: () -> Unit) {
        val currentState = _state.value
        if (!currentState.submitEnabled) return

        updateState { copy(loading = true, error = null) }
        viewModelScope.launch {
            try {
                when (currentState.mode) {
                    AuthMode.Login -> login(currentState.login.trim(), currentState.password)
                    AuthMode.Register -> register(currentState.login.trim(), currentState.password)
                    AuthMode.ChangePassword -> changePassword(
                        currentState.login.trim(),
                        currentState.password
                    )
                }
                _state.value = AuthState()
                onSuccess()
            } catch (exception: Exception) {
                updateState {
                    copy(
                        loading = false,
                        error = exception.message ?: "Не удалось выполнить запрос"
                    )
                }
            }
        }
    }

    /**
     * Изменяет состояние экрана
     */
    private inline fun updateState(transform: AuthState.() -> AuthState) {
        _state.value = _state.value.transform()
    }
}
