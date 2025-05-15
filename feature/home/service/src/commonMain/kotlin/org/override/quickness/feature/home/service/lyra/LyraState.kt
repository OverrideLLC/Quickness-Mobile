package org.override.quickness.feature.home.service.lyra

import androidx.compose.runtime.*
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.override.quickness.feature.home.service.states.DayItem

@Immutable
data class LyraState(
    val isLoading: Boolean = false,
    var daySelected: DayItem? = null,
    val currentDay: LocalDate = now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val macros: Map<String, Int> = mapOf(
        "Proteins" to 200,
        "Carbo" to 300,
        "Fats" to 100,
        "Water" to 2000,
        "Fiber" to 2000,
        "Iron" to 2000,
        "Sodium" to 2000,
        "Potassium" to 2000,
        "Vitamin A" to 2000,
        "Vitamin C" to 2000,
    )
)