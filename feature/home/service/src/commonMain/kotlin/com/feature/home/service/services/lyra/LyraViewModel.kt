package com.feature.home.service.services.lyra

import androidx.lifecycle.ViewModel
import com.feature.home.service.states.DayItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

class LyraViewModel : ViewModel() {
    data class LyraState(
        var isLoading: Boolean = false,
        var daySelected: DayItem? = null,
        val currentDay: LocalDate = now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    )

    private val _state = MutableStateFlow(LyraState())
    val state = _state.asStateFlow()

    fun update(update: LyraState.() -> LyraState) {
        _state.value = update(_state.value)
    }

    private fun getDaysInMonth(year: Int, month: Int): List<LocalDate> {
        // Siguiente mes. Si es diciembre, saltamos al año siguiente, mes 1
        val nextMonthDate = if (month == 12) {
            LocalDate(year + 1, 1, 1)
        } else {
            LocalDate(year, month + 1, 1)
        }

        // Último día del mes actual = (siguiente mes) - 1 día
        val lastDayOfMonth = nextMonthDate.minus(1, DateTimeUnit.DAY)
        val lengthOfMonth = lastDayOfMonth.dayOfMonth

        // Generamos la lista de LocalDate
        return (1..lengthOfMonth).map { day ->
            LocalDate(year, month, day)
        }
    }

    fun getDayItemsForMonth(year: Int, month: Int): List<DayItem> {
        val today = now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date

        val daysInMonth = getDaysInMonth(year, month)

        return daysInMonth.map { date ->
            DayItem(
                dayOfWeek = date.dayOfWeek.name.take(3),
                dayOfMonth = date.dayOfMonth,
                date = date,
                isSelected = date == today,
                isHighlighted = (date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY)
            )
        }
    }
}