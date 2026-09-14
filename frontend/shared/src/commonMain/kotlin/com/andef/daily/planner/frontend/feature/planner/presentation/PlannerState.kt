package com.andef.daily.planner.frontend.feature.planner.presentation

import com.andef.daily.planner.frontend.feature.planner.domain.model.Event

/**
 * Состояние экрана расписания
 */
data class PlannerState(
    val day: String,
    val searchText: String = "",
    val events: List<Event> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
    val datePickerVisible: Boolean = false,
    val editor: EventEditorState? = null,
    val deletingEvent: Event? = null
)

/**
 * Состояние редактора мероприятия
 */
data class EventEditorState(
    val eventId: Long? = null,
    val title: String = "",
    val location: String = "",
    val startsAt: String,
    val endsAt: String,
    val datePickerField: EventDateTimeField? = null,
    val timePickerField: EventDateTimeField? = null,
    val saving: Boolean = false,
    val error: String? = null
) {
    val submitEnabled: Boolean
        get() = title.isNotBlank() && location.isNotBlank() &&
                startsAt.length >= 16 && endsAt.length >= 16 && !saving
}
