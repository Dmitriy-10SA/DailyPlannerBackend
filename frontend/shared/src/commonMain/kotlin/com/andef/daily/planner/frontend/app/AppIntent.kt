package com.andef.daily.planner.frontend.app

sealed interface AppIntent {
    data object Authenticated : AppIntent
    data object Logout : AppIntent
}
