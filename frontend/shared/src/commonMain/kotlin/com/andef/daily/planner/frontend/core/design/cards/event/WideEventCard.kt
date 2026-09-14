package com.andef.daily.planner.frontend.core.design.cards.event

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
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
fun WideEventCard(
    event: Event,
    onIntent: (PlannerIntent) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.widthIn(min = 92.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = event.startsAt.displayTime(),
                color = Blue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "до ${event.endsAt.displayTime()}",
                color = GrayForLight,
                fontSize = 13.sp
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 18.dp)
        ) {
            Text(
                text = event.title,
                color = Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(7.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.location),
                    contentDescription = null,
                    tint = GrayForLight,
                    modifier = Modifier.size(17.dp)
                )

                Text(
                    text = event.location,
                    color = GrayForLight,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }

        IconButton(
            onClick = {
                onIntent(PlannerIntent.EditClick(event))
            }
        ) {
            Icon(
                painter = painterResource(Res.drawable.edit),
                contentDescription = "Изменить",
                tint = Blue
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
                tint = Red
            )
        }
    }
}