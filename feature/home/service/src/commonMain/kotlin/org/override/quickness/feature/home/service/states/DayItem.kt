package org.override.quickness.feature.home.service.states

import kotlinx.datetime.LocalDate

data class DayItem(
    val dayOfWeek: String,   // "Mon", "Tue", etc.
    val dayOfMonth: Int,     // 20, 21, 22...
    val date: LocalDate,     // Para referencia completa
    val isSelected: Boolean = false,
    val isHighlighted: Boolean = false
)