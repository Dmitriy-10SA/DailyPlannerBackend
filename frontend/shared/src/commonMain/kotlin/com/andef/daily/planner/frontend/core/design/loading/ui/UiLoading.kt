package com.andef.daily.planner.frontend.core.design.loading.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andef.daily.planner.frontend.core.design.Blue
import com.andef.daily.planner.frontend.core.design.blackOrWhiteColor
import com.andef.daily.planner.frontend.core.design.dialog.container.ui.UiDialogContainer
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun UiLoading(
    isVisible: Boolean,
    onDismissRequest: (() -> Unit)? = null,
    isLightTheme: Boolean
) {
    var visible by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(350.milliseconds)
            visible = isVisible
        } else {
            visible = false
        }
    }

    if (visible) {
        UiDialogContainer(isLightTheme = isLightTheme, onDismissRequest = onDismissRequest) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(56.dp),
                    color = Blue,
                    trackColor = blackOrWhiteColor(isLightTheme)
                )
            }
        }
    }
}