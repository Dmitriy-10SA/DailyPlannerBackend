package com.andef.daily.planner.frontend.core.design.cards.event

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.daily.planner.frontend.core.design.Black
import com.andef.daily.planner.frontend.core.design.Blue
import com.andef.daily.planner.frontend.core.design.GrayForLight
import com.andef.daily.planner.frontend.core.design.Red
import com.andef.daily.planner.frontend.feature.planner.domain.model.Event
import com.andef.daily.planner.frontend.feature.planner.presentation.PlannerIntent
import com.andef.daily.planner.frontend.resources.Res
import com.andef.daily.planner.frontend.resources.delete
import com.andef.daily.planner.frontend.resources.edit
import com.andef.daily.planner.frontend.resources.location
import org.jetbrains.compose.resources.painterResource

@Composable
fun CompactEventCard(
    event: Event,
    onIntent: (PlannerIntent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = event.startsAt.displayTime(),
                color = Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = " — ${event.endsAt.displayTime()}",
                color = GrayForLight,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    onIntent(PlannerIntent.EditClick(event))
                }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.edit),
                    contentDescription = "Изменить",
                    tint = Blue,
                    modifier = Modifier.size(20.dp)
                )
            }

            IconButton(
                onClick = {
                    onIntent(PlannerIntent.DeleteClick(event))
                }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.delete),
                    contentDescription = "Удалить",
                    tint = Red,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Text(
            text = event.title,
            color = Black,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(6.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.location),
                contentDescription = null,
                tint = GrayForLight,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = event.location,
                color = GrayForLight,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
}