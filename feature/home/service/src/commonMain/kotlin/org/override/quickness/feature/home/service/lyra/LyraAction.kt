package org.override.quickness.feature.home.service.lyra

import org.override.quickness.feature.home.service.states.DayItem

sealed interface LyraAction {
    data class OnDaySelected(val item: DayItem): LyraAction
}