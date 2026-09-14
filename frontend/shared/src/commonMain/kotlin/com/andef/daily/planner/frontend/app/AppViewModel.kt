package com.andef.daily.planner.frontend.app

import androidx.lifecycle.ViewModel
import com.andef.daily.planner.frontend.core.domain.HasSessionUseCase
import com.andef.daily.planner.frontend.core.navigation.AppDestination
import com.andef.daily.planner.frontend.feature.auth.domain.usecases.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel(
    private val hasSession: HasSessionUseCase,
    private val logout: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AppState(destination = configureDestination()))
    val state: StateFlow<AppState> = _state.asStateFlow()

    fun send(intent: AppIntent) {
        when (intent) {
            AppIntent.Authenticated -> onAuthenticated()
            AppIntent.Logout -> onLogout()
        }
    }

    private fun configureDestination(): AppDestination {
        return if (hasSession()) {
            AppDestination.Planner
        } else {
            AppDestination.Auth
        }
    }

    private fun onAuthenticated() {
        _state.value = AppState(AppDestination.Planner)
    }

    private fun onLogout() {
        logout()
        _state.value = AppState(AppDestination.Auth)
    }
}
