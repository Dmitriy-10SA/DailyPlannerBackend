package com.andef.daily.planner.frontend.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.andef.daily.planner.frontend.core.design.DailyPlannerTheme
import com.andef.daily.planner.frontend.core.navigation.AppNavGraph
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(viewModel: AppViewModel = koinViewModel()) {
    val state = viewModel.state.collectAsState().value

    DailyPlannerTheme {
        AppNavGraph(state = state, viewModel = viewModel)
    }
}
