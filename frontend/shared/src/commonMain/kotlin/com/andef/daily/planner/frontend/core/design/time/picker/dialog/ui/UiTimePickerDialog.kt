package com.andef.daily.planner.frontend.core.design.time.picker.dialog.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.daily.planner.frontend.core.design.Blue
import com.andef.daily.planner.frontend.core.design.White
import com.andef.daily.planner.frontend.core.design.blackOrWhiteColor
import com.andef.daily.planner.frontend.core.design.darkGrayOrWhiteColor
import com.andef.daily.planner.frontend.core.design.dialog.container.ui.UiDialogContainer
import com.andef.daily.planner.frontend.core.design.grayColor
import com.andef.daily.planner.frontend.core.design.textButtonColors
import com.andef.daily.planner.frontend.core.design.textButtonShape
import com.andef.daily.planner.frontend.core.design.timePickerColors
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiTimePickerDialog(
    isVisible: Boolean,
    isLightTheme: Boolean,
    modifier: Modifier = Modifier,
    initialTime: LocalTime = LocalTime(0, 0),
    onDismissRequest: () -> Unit,
    onOkClick: (LocalTime) -> Unit
) {
    if (isVisible) {
        val timePickerState = rememberTimePickerState(
            initialHour = initialTime.hour,
            initialMinute = initialTime.minute,
            is24Hour = true
        )
        UiDialogContainer(isLightTheme, onDismissRequest) {
            Column(modifier = modifier) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 28.dp),
                    contentAlignment = Alignment.Center
                ) {
                    TimePicker(
                        state = timePickerState,
                        colors = timePickerColors(isLightTheme)
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 0.5.dp,
                    color = grayColor(isLightTheme = isLightTheme)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    ActionButton(
                        modifier = Modifier.matchParentSize(),
                        onClick = {
                            onOkClick(
                                LocalTime(
                                    timePickerState.hour, timePickerState.minute
                                )
                            )
                        },
                        isLightTheme = isLightTheme,
                        text = "Сохранить",
                        color = Blue
                    )
                }
            }
        }
    }
}

@Composable
private fun ActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isLightTheme: Boolean,
    text: String,
    color: Color
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        shape = textButtonShape(topEnd = 0.dp, topStart = 0.dp),
        colors = textButtonColors(isLightTheme = isLightTheme)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
