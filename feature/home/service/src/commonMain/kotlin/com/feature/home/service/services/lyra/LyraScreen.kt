package com.feature.home.service.services.lyra

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.feature.home.service.components.CalendarCarousel
import com.feature.home.service.components.CircleCalorieProgress
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LyraScreen() = Screen()

@Composable
fun Screen(
    viewModel: LyraViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            CalendarCarousel(
                year = state.currentDay.year,
                month = state.currentDay.monthNumber,
                viewModel = viewModel,
                color = 0xfffc0c00,
                onDaySelected = { day -> viewModel.update { copy(daySelected = day) } }
            )
            Spacer(modifier = Modifier.padding(10.dp))
        }
        item {
            CircleCalorieProgress(
                currentCalories = 1000,
                maxCalories = 2000
            )
        }
    }
}