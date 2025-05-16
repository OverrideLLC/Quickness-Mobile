package org.override.quickness.feature.home.service.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.override.quickness.feature.home.service.lyra.LyraViewModel
import org.override.quickness.feature.home.service.states.DayItem

@Composable
fun DayItemView(
    dayItem: DayItem,
    color: Color,
    onDayClick: (DayItem) -> Unit
) {
    Box(
        modifier = Modifier
            .height(70.dp)
            .width(70.dp)
            .padding(horizontal = 8.dp)
            .background(
                color = if (dayItem.isSelected) color.copy(alpha = 0.5f) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            ),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onDayClick(dayItem) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = dayItem.dayOfWeek,
                    style = MaterialTheme.typography.titleMedium,
                    color = colorScheme.tertiary
                )

                Text(
                    text = dayItem.dayOfMonth.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = colorScheme.tertiary
                )
            }
        }
    )
}


@Composable
fun CalendarCarousel(
    year: Int,
    month: Int,
    viewModel: LyraViewModel,
    color: Color,
    onDaySelected: (DayItem) -> Unit
) {
    val initialDays = remember { viewModel.getDayItemsForMonth(year, month) }
    var days by remember { mutableStateOf(initialDays) }
    val currentDayIndex = days.indexOfFirst { it.isSelected }.let { if (it == -1) 0 else it }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = currentDayIndex)

    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        items(days) { dayItem ->
            DayItemView(
                dayItem = dayItem,
                color = color,
                onDayClick = { selectedDay ->
                    days = days.map {
                        if (it.date == selectedDay.date) it.copy(isSelected = true)
                        else it.copy(isSelected = false)
                    }
                    onDaySelected(selectedDay)
                }
            )
        }
    }
}