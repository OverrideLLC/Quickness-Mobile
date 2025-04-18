package org.quickness.plataform

import androidx.compose.runtime.Composable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class GoogleMaps() {
    @Composable
    fun Map()
}