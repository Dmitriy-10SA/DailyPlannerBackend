package com.andef.daily.planner.frontend.feature.planner.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.daily.planner.frontend.feature.planner.domain.model.Event
import com.andef.daily.planner.frontend.feature.planner.domain.model.EventDraft
import com.andef.daily.planner.frontend.feature.planner.domain.usecases.CreateEventUseCase
import com.andef.daily.planner.frontend.feature.planner.domain.usecases.DeleteEventUseCase
import com.andef.daily.planner.frontend.feature.planner.domain.usecases.GetEventsUseCase
import com.andef.daily.planner.frontend.feature.planner.domain.usecases.UpdateEventUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * Модель состояния экрана расписания
 */
class PlannerViewModel(
    private val getEvents: GetEventsUseCase,
    private val createEvent: CreateEventUseCase,
    private val updateEvent: UpdateEventUseCase,
    private val deleteEvent: DeleteEventUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PlannerState(day = currentDay()))
    val state: StateFlow<PlannerState> = _state.asStateFlow()

    init {
        loadEvents()
    }

    /**
     * Обрабатывает намерение экрана
     */
    fun send(intent: PlannerIntent) {
        when (intent) {
            is PlannerIntent.DayChange -> {
                updateState { copy(day = intent.value, error = null) }
                if (intent.value.length == 10) loadEvents()
            }

            PlannerIntent.DatePickerOpen -> updateState { copy(datePickerVisible = true) }
            PlannerIntent.DatePickerDismiss -> updateState { copy(datePickerVisible = false) }
            is PlannerIntent.DaySelect -> {
                updateState { copy(day = intent.value.toString(), datePickerVisible = false, error = null) }
                loadEvents()
            }

            is PlannerIntent.SearchTextChange -> updateState {
                copy(searchText = intent.value, error = null)
            }

            PlannerIntent.Search, PlannerIntent.Refresh -> loadEvents()
            PlannerIntent.AddClick -> openEditor()
            is PlannerIntent.EditClick -> openEditor(intent.event)
            PlannerIntent.EditorDismiss -> updateState { copy(editor = null) }
            is PlannerIntent.TitleChange -> updateEditor {
                copy(
                    title = intent.value.take(100),
                    error = null
                )
            }

            is PlannerIntent.LocationChange -> updateEditor {
                copy(location = intent.value.take(300), error = null)
            }

            is PlannerIntent.EditorDatePickerOpen -> updateEditor {
                copy(datePickerField = intent.field, timePickerField = null)
            }

            PlannerIntent.EditorDatePickerDismiss -> updateEditor { copy(datePickerField = null) }
            is PlannerIntent.EditorDateSelect -> selectEditorDate(intent.value)
            is PlannerIntent.EditorTimePickerOpen -> updateEditor {
                copy(timePickerField = intent.field, datePickerField = null)
            }

            PlannerIntent.EditorTimePickerDismiss -> updateEditor { copy(timePickerField = null) }
            is PlannerIntent.EditorTimeSelect -> selectEditorTime(intent.value)

            PlannerIntent.Save -> saveEvent()
            is PlannerIntent.DeleteClick -> updateState { copy(deletingEvent = intent.event) }
            PlannerIntent.DeleteDismiss -> updateState { copy(deletingEvent = null) }
            PlannerIntent.DeleteConfirm -> deleteSelectedEvent()
            PlannerIntent.ErrorDismiss -> updateState { copy(error = null) }
        }
    }

    /**
     * Загружает мероприятия
     */
    private fun loadEvents() {
        val currentState = _state.value
        if (currentState.day.length != 10) return

        updateState { copy(loading = true, error = null) }
        viewModelScope.launch {
            runCatching {
                getEvents(
                    currentState.day,
                    currentState.searchText.trim().ifEmpty { null })
            }
                .onSuccess { events -> updateState { copy(events = events, loading = false) } }
                .onFailure { exception -> showError(exception) }
        }
    }

    /**
     * Открывает редактор мероприятия
     */
    private fun openEditor(event: Event? = null) {
        updateState {
            copy(
                editor = EventEditorState(
                    eventId = event?.id,
                    title = event?.title.orEmpty(),
                    location = event?.location.orEmpty(),
                    startsAt = event?.startsAt?.take(16).orEmpty(),
                    endsAt = event?.endsAt?.take(16).orEmpty()
                )
            )
        }
    }

    /**
     * Выбирает дату мероприятия
     */
    private fun selectEditorDate(date: LocalDate) {
        updateEditor {
            when (datePickerField) {
                EventDateTimeField.Start -> copy(
                    startsAt = date.withTimeFrom(startsAt),
                    datePickerField = null,
                    error = null
                )

                EventDateTimeField.End -> copy(
                    endsAt = date.withTimeFrom(endsAt),
                    datePickerField = null,
                    error = null
                )

                null -> this
            }
        }
    }

    /**
     * Выбирает время мероприятия
     */
    private fun selectEditorTime(time: LocalTime) {
        updateEditor {
            when (timePickerField) {
                EventDateTimeField.Start -> copy(
                    startsAt = startsAt.withTime(time),
                    timePickerField = null,
                    error = null
                )

                EventDateTimeField.End -> copy(
                    endsAt = endsAt.withTime(time),
                    timePickerField = null,
                    error = null
                )

                null -> this
            }
        }
    }

    /**
     * Сохраняет мероприятие
     */
    private fun saveEvent() {
        val editor = _state.value.editor ?: return
        if (!editor.submitEnabled) return
        if (editor.endsAt <= editor.startsAt) {
            updateEditor { copy(error = "Дата окончания должна быть позже даты начала") }
            return
        }

        updateEditor { copy(saving = true, error = null) }
        val draft = EventDraft(
            title = editor.title.trim(),
            location = editor.location.trim(),
            startsAt = editor.startsAt,
            endsAt = editor.endsAt
        )
        viewModelScope.launch {
            runCatching {
                editor.eventId?.let { updateEvent(it, draft) } ?: createEvent(draft)
            }.onSuccess {
                updateState { copy(editor = null) }
                loadEvents()
            }.onFailure { exception ->
                updateEditor {
                    copy(
                        saving = false,
                        error = exception.message ?: "Не удалось сохранить мероприятие"
                    )
                }
            }
        }
    }

    /**
     * Удаляет выбранное мероприятие
     */
    private fun deleteSelectedEvent() {
        val event = _state.value.deletingEvent ?: return
        updateState { copy(deletingEvent = null, loading = true, error = null) }
        viewModelScope.launch {
            runCatching { deleteEvent(event.id) }
                .onSuccess { loadEvents() }
                .onFailure { exception -> showError(exception) }
        }
    }

    /**
     * Показывает ошибку
     */
    private fun showError(exception: Throwable) {
        updateState {
            copy(loading = false, error = exception.message ?: "Не удалось выполнить запрос")
        }
    }

    /**
     * Изменяет состояние редактора
     */
    private inline fun updateEditor(transform: EventEditorState.() -> EventEditorState) {
        updateState { copy(editor = editor?.transform()) }
    }

    /**
     * Изменяет состояние экрана
     */
    private inline fun updateState(transform: PlannerState.() -> PlannerState) {
        _state.value = _state.value.transform()
    }

    /**
     * Возвращает текущий день
     */
    @OptIn(ExperimentalTime::class)
    private fun currentDay(): String = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
        .toString()

    /**
     * Объединяет выбранную дату с текущим временем
     */
    private fun LocalDate.withTimeFrom(dateTime: String): String =
        "$this" + "T" + dateTime.timePart()

    /**
     * Заменяет время в значении даты
     */
    private fun String.withTime(time: LocalTime): String =
        datePart() + "T" + time.hour.twoDigits() + ":" + time.minute.twoDigits()

    /**
     * Возвращает часть даты
     */
    private fun String.datePart(): String = substringBefore('T').takeIf { it.length == 10 }.orEmpty()

    /**
     * Возвращает часть времени
     */
    private fun String.timePart(): String = substringAfter('T', "").take(5)

    /**
     * Форматирует число двумя цифрами
     */
    private fun Int.twoDigits(): String = toString().padStart(2, '0')
}
