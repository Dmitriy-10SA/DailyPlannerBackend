package com.andef.daily.planner.frontend.core.design.cards.event

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andef.daily.planner.frontend.core.design.GrayForLight
import com.andef.daily.planner.frontend.core.design.cardColors
import com.andef.daily.planner.frontend.core.design.cardShape
import com.andef.daily.planner.frontend.feature.planner.domain.model.Event
import com.andef.daily.planner.frontend.feature.planner.presentation.PlannerIntent

/**
 * Карточка мероприятия
 */
@Composable
fun EventCard(event: Event, onIntent: (PlannerIntent) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = cardShape(16.dp, 16.dp, 16.dp, 16.dp),
        colors = cardColors(isLightTheme = true),
        border = BorderStroke(
            0.5.dp,
            GrayForLight.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (maxWidth < 500.dp) {
                CompactEventCard(
                    event = event,
                    onIntent = onIntent
                )
            } else {
                WideEventCard(
                    event = event,
                    onIntent = onIntent
                )
            }
        }
    }
}

/**
 * Форматирует время для отображения
 */
fun String.displayTime(): String = if ('T' in this) {
    substringAfter('T').take(5)
} else {
    take(5)
}