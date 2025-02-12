package org.quickness.ui.screens.home.service

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.quickness.GoogleMaps

@Composable
fun ServiceScreen() = Screen()

@Composable
private fun Screen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMaps().Map()
    }
}