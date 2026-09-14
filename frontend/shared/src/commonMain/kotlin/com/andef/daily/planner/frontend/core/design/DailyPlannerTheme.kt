package com.andef.daily.planner.frontend.core.design

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = White,
    secondary = White,
    tertiary = White,
    background = White,
    surface = White,
    onPrimary = Black,
    onSecondary = Black,
    onTertiary = Black,
    onBackground = Black,
    onSurface = Black
)

/**
 * Тема ежедневника
 */
@Composable
fun DailyPlannerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography(),
        content = content
    )
}
