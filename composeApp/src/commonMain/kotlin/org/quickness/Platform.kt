@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.quickness

import androidx.compose.runtime.Composable
import org.quickness.interfaces.plataform.Uri

expect class Uri(url: String) : Uri {
    override fun navigate()
}

expect class GoogleMaps() {
    @Composable
    fun Map()
}