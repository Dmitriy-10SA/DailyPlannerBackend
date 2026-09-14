package com.andef.daily.planner.frontend.core.navigation

import androidx.compose.runtime.Composable
import com.andef.daily.planner.frontend.app.AppIntent
import com.andef.daily.planner.frontend.app.AppState
import com.andef.daily.planner.frontend.app.AppViewModel
import com.andef.daily.planner.frontend.feature.auth.presentation.AuthScreen
import com.andef.daily.planner.frontend.feature.planner.presentation.PlannerScreen

/**
 * Граф навигации приложения
 */
@Composable
fun AppNavGraph(state: AppState, viewModel: AppViewModel) {
    when (state.destination) {
        AppDestination.Auth -> AuthScreen(
            onAuthenticated = {
                viewModel.send(intent = AppIntent.Authenticated)
            }
        )

        AppDestination.Planner -> PlannerScreen(
            onLogout = {
                viewModel.send(intent = AppIntent.Logout)
            }
        )
    }
}