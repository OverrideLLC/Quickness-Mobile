package org.override.quickness.feature.home.service.lyra

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import org.override.quickness.feature.home.service.lyra.LyraAction.OnDaySelected
import org.override.quickness.feature.home.service.states.DayItem

class LyraViewModel : ViewModel() {

    private val _state = MutableStateFlow(LyraState())
    val state = _state
        .onStart { }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = LyraState()
        )

    fun onAction(action: LyraAction) {
        when (action) {
            is OnDaySelected -> {
                _state.update { it.copy(daySelected = action.item) }
            }
        }
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