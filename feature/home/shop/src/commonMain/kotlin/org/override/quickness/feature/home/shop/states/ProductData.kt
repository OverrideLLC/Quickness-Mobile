package org.override.quickness.feature.home.shop.states

import androidx.compose.ui.graphics.Color
import org.override.quickness.shared.resources.drawable.ResourceNameKey

data class ProductData(
    val text: String,
    val icon: ResourceNameKey,
    val containerColor: Color,
    val iconTint: Color,
    val colorText: Color,
    val brushStartColor: Color? = null,
    val onClickAction: () -> Unit
)