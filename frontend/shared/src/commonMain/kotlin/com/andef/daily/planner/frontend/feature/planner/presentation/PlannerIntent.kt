package com.andef.daily.planner.frontend.feature.planner.presentation

import com.andef.daily.planner.frontend.feature.planner.domain.model.Event
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

/**
 * Намерения экрана расписания
 */
sealed interface PlannerIntent {
    data class DayChange(val value: String) : PlannerIntent
    data object DatePickerOpen : PlannerIntent
    data object DatePickerDismiss : PlannerIntent
    data class DaySelect(val value: LocalDate) : PlannerIntent
    data class SearchTextChange(val value: String) : PlannerIntent
    data object Search : PlannerIntent
    data object Refresh : PlannerIntent
    data object AddClick : PlannerIntent
    data class EditClick(val event: Event) : PlannerIntent
    data object EditorDismiss : PlannerIntent
    data class TitleChange(val value: String) : PlannerIntent
    data class LocationChange(val value: String) : PlannerIntent
    data class EditorDatePickerOpen(val field: EventDateTimeField) : PlannerIntent
    data object EditorDatePickerDismiss : PlannerIntent
    data class EditorDateSelect(val value: LocalDate) : PlannerIntent
    data class EditorTimePickerOpen(val field: EventDateTimeField) : PlannerIntent
    data object EditorTimePickerDismiss : PlannerIntent
    data class EditorTimeSelect(val value: LocalTime) : PlannerIntent
    data object Save : PlannerIntent
    data class DeleteClick(val event: Event) : PlannerIntent
    data object DeleteDismiss : PlannerIntent
    data object DeleteConfirm : PlannerIntent
    data object ErrorDismiss : PlannerIntent
}

/**
 * Поле даты и времени мероприятия
 */
enum class EventDateTimeField {
    /**
     * Начало мероприятия
     */
    Start,

    /**
     * Окончание мероприятия
     */
    End
}
