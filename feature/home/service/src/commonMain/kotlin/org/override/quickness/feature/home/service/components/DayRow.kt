package org.override.quickness.feature.home.service.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.override.quickness.feature.home.service.lyra.LyraViewModel
import org.override.quickness.feature.home.service.states.DayItem

@Composable
fun DayItemView(
    dayItem: DayItem,
    color: Long,
    onDayClick: (DayItem) -> Unit
) {
    // Tamaños y colores de ejemplo, ajusta a tu gusto
    val circleSize = 48.dp
    val backgroundColor = when {
        dayItem.isSelected -> Color(color)
        else -> Color.Transparent
    }
    val borderStroke = if (!dayItem.isSelected) BorderStroke(2.dp, colorScheme.tertiary) else null

    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onDayClick(dayItem) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayItem.dayOfWeek,
            style = MaterialTheme.typography.titleMedium,
            color = colorScheme.tertiary
        )

        // Círculo con el día del mes
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(circleSize)
                .clip(CircleShape)
                .background(backgroundColor)
                .then(
                    if (borderStroke != null) Modifier.border(borderStroke, CircleShape)
                    else Modifier
                )
        ) {
            Text(
                text = dayItem.dayOfMonth.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = if (dayItem.isSelected) Color.White else colorScheme.tertiary
            )
        }
    }
}


@Composable
fun CalendarCarousel(
    year: Int,
    month: Int,
    viewModel: LyraViewModel,
    color: Long,
    onDaySelected: (DayItem) -> Unit
) {
    val initialDays = remember { viewModel.getDayItemsForMonth(year, month) }
    var days by remember { mutableStateOf(initialDays) }

    // Obtén el índice del día actual
    val currentDayIndex = days.indexOfFirst { it.isSelected }.let { if (it == -1) 0 else it }

    // Estado para el LazyRow, inicializando con el índice del día actual
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = currentDayIndex)

    // Si deseas un scroll animado adicional, podrías usar LaunchedEffect:
    LaunchedEffect(currentDayIndex) {
        listState.scrollToItem(currentDayIndex)
    }

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
                    // Actualiza la lista para reflejar la nueva selección
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