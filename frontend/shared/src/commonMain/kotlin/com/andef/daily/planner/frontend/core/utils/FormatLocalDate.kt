package com.andef.daily.planner.frontend.core.utils

import com.andef.daily.planner.frontend.core.platform.date.minusDays
import com.andef.daily.planner.frontend.core.platform.date.now
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number

fun formatLocalDate(date: LocalDate): String {
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)

    return when (date) {
        today -> "Сегодня"
        yesterday -> "Вчера"
        else -> "${date.day.toString().padStart(2, '0')}." +
                "${date.month.number.toString().padStart(2, '0')}." +
                "${date.year}"
    }
}