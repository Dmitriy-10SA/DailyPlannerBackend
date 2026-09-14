package com.andef.daily.planner.frontend.app

import com.andef.daily.planner.frontend.core.navigation.AppDestination

data class AppState(val destination: AppDestination = AppDestination.Auth)
