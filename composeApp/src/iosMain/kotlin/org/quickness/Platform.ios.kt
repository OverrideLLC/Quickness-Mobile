package org.quickness

import androidx.compose.runtime.Composable
import org.quickness.interfaces.plataform.Uri

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class GoogleMaps actual constructor() {
    @Composable
    actual fun Map() {
        TODO("Not yet implemented")
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Uri actual constructor(url: String) : Uri {
    actual override fun navigate() {
        TODO("Not yet implemented")
    }
}