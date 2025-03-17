package com.feature.home.service.services.lyra

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.feature.home.service.components.CalendarCarousel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LyraScreen() = Screen()

@Composable
fun Screen(
    viewModel: LyraViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    CalendarCarousel(
        year = state.currentDay.year,
        month = state.currentDay.monthNumber,
        viewModel = viewModel,
        color = 0xfffc0c00,
        onDaySelected = { day -> viewModel.update { copy(daySelected = day) } }
    )
}