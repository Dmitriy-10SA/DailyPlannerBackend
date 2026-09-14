package com.andef.daily.planner.frontend.feature.planner.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.daily.planner.frontend.core.design.Black
import com.andef.daily.planner.frontend.core.design.Blue
import com.andef.daily.planner.frontend.core.design.GrayForLight
import com.andef.daily.planner.frontend.core.design.Red
import com.andef.daily.planner.frontend.core.design.White
import com.andef.daily.planner.frontend.core.design.alert.dialog.ui.UiAlertDialog
import com.andef.daily.planner.frontend.core.design.button.ui.UiButton
import com.andef.daily.planner.frontend.core.design.buttonColors
import com.andef.daily.planner.frontend.core.design.buttonShape
import com.andef.daily.planner.frontend.core.design.cards.event.EventCard
import com.andef.daily.planner.frontend.core.design.cards.event.displayTime
import com.andef.daily.planner.frontend.core.design.chooser.ui.UiChooser
import com.andef.daily.planner.frontend.core.design.date.picker.dialog.ui.UiDatePickerDialog
import com.andef.daily.planner.frontend.core.design.dialog.container.ui.UiDialogContainer
import com.andef.daily.planner.frontend.core.design.fab.ui.UiFAB
import com.andef.daily.planner.frontend.core.design.loading.ui.UiLoading
import com.andef.daily.planner.frontend.core.design.textfield.ui.UiTextField
import com.andef.daily.planner.frontend.core.design.time.picker.dialog.ui.UiTimePickerDialog
import com.andef.daily.planner.frontend.feature.planner.domain.model.Event
import com.andef.daily.planner.frontend.resources.Res
import com.andef.daily.planner.frontend.resources.add
import com.andef.daily.planner.frontend.resources.calendar
import com.andef.daily.planner.frontend.resources.choose
import com.andef.daily.planner.frontend.resources.edit
import com.andef.daily.planner.frontend.resources.location
import com.andef.daily.planner.frontend.resources.logout
import com.andef.daily.planner.frontend.resources.schedule
import com.andef.daily.planner.frontend.resources.search
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Экран расписания
 */
@Composable
fun PlannerScreen(
    onLogout: () -> Unit,
    viewModel: PlannerViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val addIcon = painterResource(Res.drawable.add)

    LaunchedEffect(Unit) {
        viewModel.send(PlannerIntent.Search)
    }

    ProvideTextStyle(value = TextStyle(fontFamily = FontFamily.SansSerif)) {
        Box(modifier = Modifier.fillMaxSize()) {
            PlannerContent(state = state, onLogout = onLogout, onIntent = viewModel::send)
            Box(
                modifier = Modifier.align(Alignment.BottomEnd).padding(28.dp)
            ) {
                UiFAB(
                    onClick = { viewModel.send(PlannerIntent.AddClick) },
                    icon = addIcon,
                    iconContentDescription = "Добавить мероприятие",
                    isVisible = !state.loading && state.editor == null
                )
            }
        }

        UiDatePickerDialog(
            isVisible = state.datePickerVisible,
            isLightTheme = true,
            modifier = Modifier.widthIn(max = 760.dp).fillMaxWidth(),
            onDismissRequest = { viewModel.send(PlannerIntent.DatePickerDismiss) },
            onOkClick = { viewModel.send(PlannerIntent.DaySelect(it)) }
        )
        state.editor?.let { editor ->
            EventEditorDialog(editor, viewModel::send)
            UiDatePickerDialog(
                isVisible = editor.datePickerField != null,
                isLightTheme = true,
                modifier = Modifier.widthIn(max = 760.dp).fillMaxWidth(),
                onDismissRequest = { viewModel.send(PlannerIntent.EditorDatePickerDismiss) },
                onOkClick = { viewModel.send(PlannerIntent.EditorDateSelect(it)) }
            )
            UiTimePickerDialog(
                isVisible = editor.timePickerField != null,
                isLightTheme = true,
                modifier = Modifier.widthIn(max = 600.dp).fillMaxWidth(),
                initialTime = editor.selectedTime(),
                onDismissRequest = { viewModel.send(PlannerIntent.EditorTimePickerDismiss) },
                onOkClick = { viewModel.send(PlannerIntent.EditorTimeSelect(it)) }
            )
        }
        UiAlertDialog(
            isLightTheme = true,
            isVisible = state.deletingEvent != null,
            title = "Удалить мероприятие?",
            subtitle = state.deletingEvent?.let { "«${it.title}» будет удалено без возможности восстановления" },
            modifier = Modifier.widthIn(max = 520.dp).fillMaxWidth(),
            yesTitle = "Удалить",
            yesTitleColor = Red,
            onDismissRequest = { viewModel.send(PlannerIntent.DeleteDismiss) },
            onYesClick = { viewModel.send(PlannerIntent.DeleteConfirm) },
            onCancelClick = { viewModel.send(PlannerIntent.DeleteDismiss) }
        )
        UiLoading(
            isVisible = state.loading || state.editor?.saving == true,
            isLightTheme = true
        )
    }
}

/**
 * Содержимое экрана расписания
 */
@Composable
private fun PlannerContent(
    state: PlannerState,
    onLogout: () -> Unit,
    onIntent: (PlannerIntent) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = White) {
        Column(modifier = Modifier.fillMaxSize()) {
            PlannerHeader(onLogout)
            HorizontalDivider(color = GrayForLight.copy(alpha = 0.3f), thickness = 1.dp)
            Column(
                modifier = Modifier
                    .widthIn(max = 1060.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 22.dp)
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
            ) {
                Filters(state = state, onIntent = onIntent)
                state.error?.let { message ->
                    Spacer(Modifier.height(12.dp))
                    Text(text = message, color = Red, fontSize = 14.sp)
                }
                Spacer(Modifier.height(22.dp))
                EventsContent(state = state, onIntent = onIntent)
            }
        }
    }
}

/**
 * Заголовок экрана
 */
@Composable
private fun PlannerHeader(onLogout: () -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        val compact = maxWidth < 400.dp

        Text(
            text = "Ежедневник",
            color = Black,
            fontSize = if (compact) 24.sp else 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )

        if (compact) {
            IconButton(
                onClick = onLogout,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.logout),
                    contentDescription = "Выйти",
                    tint = Blue,
                    modifier = Modifier.size(22.dp)
                )
            }
        } else {
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .height(48.dp),
                shape = buttonShape(),
                colors = buttonColors()
            ) {
                Icon(
                    painter = painterResource(Res.drawable.logout),
                    contentDescription = "Выйти",
                    tint = White,
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "Выйти",
                    color = White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

/**
 * Фильтры мероприятий
 */
@Composable
private fun Filters(state: PlannerState, onIntent: (PlannerIntent) -> Unit) {
    val searchIcon = painterResource(Res.drawable.search)
    val calendarIcon = painterResource(Res.drawable.calendar)
    val selectedDate = state.day.displayDate()
    val chooseIcon = painterResource(Res.drawable.choose)

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        if (maxWidth < 700.dp) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                UiChooser(
                    onClick = { onIntent(PlannerIntent.DatePickerOpen) },
                    isLightTheme = true,
                    value = selectedDate,
                    modifier = Modifier.fillMaxWidth().height(filterControlHeight),
                    placeholderText = "Дата",
                    leadingIcon = calendarIcon,
                    trailingIcon = chooseIcon,
                    leadingIconContentDescription = "Выбранная дата",
                    trailingIconContentDescription = "Выбрать дату"
                )
                UiTextField(
                    isLightTheme = true,
                    value = state.searchText,
                    onValueChange = { onIntent(PlannerIntent.SearchTextChange(it)) },
                    modifier = Modifier.fillMaxWidth().height(filterControlHeight),
                    placeholderText = "Название или место",
                    leadingIcon = searchIcon,
                    contentDescription = "Поиск мероприятий"
                )
                UiButton(
                    text = "Найти",
                    enabled = !state.loading,
                    onClick = { onIntent(PlannerIntent.Search) },
                    modifier = Modifier.fillMaxWidth().height(filterControlHeight)
                )
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                UiChooser(
                    onClick = { onIntent(PlannerIntent.DatePickerOpen) },
                    isLightTheme = true,
                    value = selectedDate,
                    modifier = Modifier.widthIn(min = 220.dp).height(filterControlHeight),
                    placeholderText = "Дата",
                    leadingIcon = calendarIcon,
                    trailingIcon = chooseIcon,
                    leadingIconContentDescription = "Выбранная дата",
                    trailingIconContentDescription = "Выбрать дату"
                )
                UiTextField(
                    isLightTheme = true,
                    value = state.searchText,
                    onValueChange = { onIntent(PlannerIntent.SearchTextChange(it)) },
                    modifier = Modifier.weight(1f).height(filterControlHeight),
                    placeholderText = "Название или место",
                    leadingIcon = searchIcon,
                    contentDescription = "Поиск мероприятий"
                )
                UiButton(
                    text = "Найти",
                    enabled = !state.loading,
                    onClick = { onIntent(PlannerIntent.Search) },
                    modifier = Modifier.widthIn(min = 140.dp).height(filterControlHeight)
                )
            }
        }
    }
}

/**
 * Список мероприятий
 */
@Composable
private fun EventsContent(state: PlannerState, onIntent: (PlannerIntent) -> Unit) {
    if (state.events.isEmpty() && !state.loading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.calendar),
                contentDescription = null,
                tint = Blue.copy(alpha = 0.45f),
                modifier = Modifier.size(58.dp)
            )
            Spacer(Modifier.height(14.dp))
            Text(
                text = "На выбранный день мероприятий нет",
                color = GrayForLight,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.events, key = Event::id) { event ->
                EventCard(event = event, onIntent = onIntent)
            }
            item { Spacer(Modifier.height(90.dp)) }
        }
    }
}

/**
 * Диалог редактирования мероприятия
 */
@Composable
private fun EventEditorDialog(editor: EventEditorState, onIntent: (PlannerIntent) -> Unit) {
    val editIcon = painterResource(Res.drawable.edit)
    val locationIcon = painterResource(Res.drawable.location)

    UiDialogContainer(
        isLightTheme = true,
        onDismissRequest = { if (!editor.saving) onIntent(PlannerIntent.EditorDismiss) }
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 520.dp)
                .fillMaxWidth()
                .heightIn(max = 680.dp)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                text = if (editor.eventId == null) "Создание мероприятия" else "Изменение мероприятия",
                color = Black,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(22.dp))
            EditorFieldLabel(text = "Название")
            Spacer(Modifier.height(7.dp))
            UiTextField(
                isLightTheme = true,
                value = editor.title,
                onValueChange = { onIntent(PlannerIntent.TitleChange(it)) },
                modifier = Modifier.fillMaxWidth().height(plannerControlHeight),
                placeholderText = "Введите название",
                leadingIcon = editIcon,
                contentDescription = "Название мероприятия"
            )
            Spacer(Modifier.height(12.dp))
            EditorFieldLabel(text = "Место проведения")
            Spacer(Modifier.height(7.dp))
            UiTextField(
                isLightTheme = true,
                value = editor.location,
                onValueChange = { onIntent(PlannerIntent.LocationChange(it)) },
                modifier = Modifier.fillMaxWidth().height(plannerControlHeight),
                placeholderText = "Введите место проведения",
                leadingIcon = locationIcon,
                contentDescription = "Место проведения"
            )
            Spacer(Modifier.height(12.dp))
            EventDateTimeChoosers(
                title = "Начало",
                value = editor.startsAt,
                field = EventDateTimeField.Start,
                onIntent = onIntent
            )
            Spacer(Modifier.height(12.dp))
            EventDateTimeChoosers(
                title = "Окончание",
                value = editor.endsAt,
                field = EventDateTimeField.End,
                onIntent = onIntent
            )
            editor.error?.let { message ->
                Spacer(Modifier.height(10.dp))
                Text(text = message, color = Red, fontSize = 14.sp)
            }
            Spacer(Modifier.height(22.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f).height(plannerControlHeight),
                    enabled = !editor.saving,
                    onClick = { onIntent(PlannerIntent.EditorDismiss) },
                    shape = buttonShape(),
                    border = BorderStroke(1.dp, GrayForLight.copy(alpha = 0.55f)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = White,
                        contentColor = Black
                    )
                ) {
                    Text(text = "Отмена", color = Black, fontSize = 16.sp)
                }
                UiButton(
                    text = "Сохранить",
                    enabled = editor.submitEnabled,
                    onClick = { onIntent(PlannerIntent.Save) },
                    modifier = Modifier.weight(1f).height(plannerControlHeight)
                )
            }
        }
    }
}

/**
 * Выбор даты и времени мероприятия
 */
@Composable
private fun EventDateTimeChoosers(
    title: String,
    value: String,
    field: EventDateTimeField,
    onIntent: (PlannerIntent) -> Unit
) {
    val clockIcon = painterResource(Res.drawable.schedule)
    val calendarIcon = painterResource(Res.drawable.calendar)
    val chooseIcon = painterResource(Res.drawable.choose)

    EditorFieldLabel(text = title)
    Spacer(Modifier.height(7.dp))
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        UiChooser(
            onClick = { onIntent(PlannerIntent.EditorDatePickerOpen(field)) },
            isLightTheme = true,
            value = value.displayDate(),
            modifier = Modifier.fillMaxWidth().height(plannerControlHeight),
            placeholderText = "Дата",
            leadingIcon = calendarIcon,
            trailingIcon = chooseIcon,
            leadingIconContentDescription = "Дата $title",
            trailingIconContentDescription = "Выбрать дату"
        )
        UiChooser(
            onClick = { onIntent(PlannerIntent.EditorTimePickerOpen(field)) },
            isLightTheme = true,
            value = value.displayTime(),
            modifier = Modifier.fillMaxWidth().height(plannerControlHeight),
            placeholderText = "Время",
            leadingIcon = clockIcon,
            trailingIcon = chooseIcon,
            leadingIconContentDescription = "Время $title",
            trailingIconContentDescription = "Выбрать время"
        )
    }
}

/**
 * Подпись поля редактора
 */
@Composable
private fun EditorFieldLabel(text: String) {
    Text(
        text = text,
        color = Black,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )
}

/**
 * Форматирует дату для отображения
 */
private fun String.displayDate(): String {
    val date = substringBefore('T')
    val parts = date.split('-')
    return if (parts.size == 3) "${parts[2]}.${parts[1]}.${parts[0]}" else date
}

/**
 * Возвращает время выбранного поля
 */
private fun EventEditorState.selectedTime(): kotlinx.datetime.LocalTime {
    val value = when (timePickerField) {
        EventDateTimeField.Start -> startsAt
        EventDateTimeField.End -> endsAt
        null -> "00:00"
    }.displayTime()
    val parts = value.split(':')
    return kotlinx.datetime.LocalTime(
        hour = parts.getOrNull(0)?.toIntOrNull() ?: 0,
        minute = parts.getOrNull(1)?.toIntOrNull() ?: 0
    )
}

/**
 * Высота элементов фильтра
 */
private val filterControlHeight = 56.dp

/**
 * Стандартная высота элементов
 */
private val plannerControlHeight = 56.dp
