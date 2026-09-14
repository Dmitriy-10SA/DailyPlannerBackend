package com.andef.daily.planner.frontend.core.design.button.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.daily.planner.frontend.core.design.buttonColors
import com.andef.daily.planner.frontend.core.design.buttonShape

@Composable
fun UiButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = buttonShape(),
        colors = buttonColors()
    ) {
        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(vertical = 11.dp),
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}
