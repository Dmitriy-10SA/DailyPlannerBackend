package com.andef.daily.planner.frontend.core.design

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Blue = Color(0xFF00ACFF)
val Red = Color(0xFFFF4848)
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
val DarkGray = Color(0xFF121212)
val GrayForLight = Color(0xFF8B8F92)
val GrayForDark = Color(0xFFB6BABD)

private fun anim(duration: Int): TweenSpec<Color> =
    tween(durationMillis = duration, easing = FastOutSlowInEasing)

@Composable
fun grayColor(isLightTheme: Boolean, duration: Int = 800) = animateColorAsState(
    targetValue = if (isLightTheme) GrayForLight else GrayForDark,
    animationSpec = anim(duration)
).value

@Composable
fun blackOrWhiteColor(isLightTheme: Boolean, duration: Int = 800) = animateColorAsState(
    targetValue = if (isLightTheme) Black else White,
    animationSpec = anim(duration)
).value

@Composable
fun darkGrayOrWhiteColor(isLightTheme: Boolean, duration: Int = 800) = animateColorAsState(
    targetValue = if (isLightTheme) White else DarkGray,
    animationSpec = anim(duration)
).value

@Composable
fun textButtonColors(isLightTheme: Boolean) = ButtonDefaults.textButtonColors(
    containerColor = Color.Transparent,
    contentColor = blackOrWhiteColor(isLightTheme = isLightTheme),
    disabledContentColor = blackOrWhiteColor(isLightTheme = isLightTheme).copy(alpha = 0.3f),
    disabledContainerColor = Color.Transparent
)

@Composable
fun buttonColors() = ButtonDefaults.buttonColors(
    containerColor = Blue,
    contentColor = White,
    disabledContainerColor = Blue.copy(alpha = 0.3f),
    disabledContentColor = White
)

@Composable
fun cardColors(isLightTheme: Boolean) = CardDefaults.cardColors(
    containerColor = darkGrayOrWhiteColor(isLightTheme = isLightTheme),
    contentColor = blackOrWhiteColor(isLightTheme = isLightTheme),
    disabledContainerColor = darkGrayOrWhiteColor(isLightTheme = isLightTheme),
    disabledContentColor = blackOrWhiteColor(isLightTheme = isLightTheme)
)

@Composable
fun textFieldColors(value: String, isLightTheme: Boolean) = OutlinedTextFieldDefaults.colors(
    focusedTextColor = grayColor(isLightTheme = isLightTheme),
    focusedContainerColor = darkGrayOrWhiteColor(isLightTheme = isLightTheme),
    focusedLabelColor = blackOrWhiteColor(isLightTheme = isLightTheme),
    focusedPlaceholderColor = grayColor(isLightTheme = isLightTheme),
    focusedLeadingIconColor = when (value.isEmpty()) {
        true -> grayColor(isLightTheme = isLightTheme)
        else -> blackOrWhiteColor(isLightTheme = isLightTheme)
    },
    focusedTrailingIconColor = when (value.isEmpty()) {
        true -> grayColor(isLightTheme = isLightTheme)
        else -> blackOrWhiteColor(isLightTheme = isLightTheme)
    },
    unfocusedTrailingIconColor = when (value.isEmpty()) {
        true -> grayColor(isLightTheme = isLightTheme)
        else -> blackOrWhiteColor(isLightTheme = isLightTheme)
    },
    focusedBorderColor = grayColor(isLightTheme = isLightTheme),
    unfocusedTextColor = grayColor(isLightTheme = isLightTheme),
    unfocusedContainerColor = darkGrayOrWhiteColor(isLightTheme = isLightTheme),
    unfocusedLabelColor = blackOrWhiteColor(isLightTheme = isLightTheme),
    unfocusedPlaceholderColor = grayColor(isLightTheme = isLightTheme),
    unfocusedLeadingIconColor = when (value.isEmpty()) {
        true -> grayColor(isLightTheme = isLightTheme)
        else -> blackOrWhiteColor(isLightTheme = isLightTheme)
    },
    cursorColor = blackOrWhiteColor(isLightTheme = isLightTheme),
    unfocusedBorderColor = grayColor(isLightTheme = isLightTheme),
    selectionColors = TextSelectionColors(
        handleColor = Blue,
        backgroundColor = Blue.copy(alpha = 0.2f)
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun timePickerColors(isLightTheme: Boolean) = TimePickerDefaults.colors(
    clockDialColor = darkGrayOrWhiteColor(isLightTheme),
    selectorColor = Blue,
    periodSelectorBorderColor = blackOrWhiteColor(isLightTheme),
    containerColor = darkGrayOrWhiteColor(isLightTheme),
    periodSelectorSelectedContainerColor = Blue,
    periodSelectorUnselectedContainerColor = Color.Transparent,
    periodSelectorSelectedContentColor = White,
    periodSelectorUnselectedContentColor = blackOrWhiteColor(isLightTheme),
    timeSelectorSelectedContainerColor = Blue,
    clockDialSelectedContentColor = White,
    clockDialUnselectedContentColor = blackOrWhiteColor(isLightTheme),
    timeSelectorUnselectedContainerColor = Color.Transparent,
    timeSelectorSelectedContentColor = White,
    timeSelectorUnselectedContentColor = blackOrWhiteColor(isLightTheme)
)