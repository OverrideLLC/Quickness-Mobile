package com.feature.home.screens.shop

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource

data class ProductData(
    val text: String,
    val icon: DrawableResource,
    val containerColor: Color,
    val iconTint: Color,
    val colorText: Color,
    val brushStartColor: Color? = null,
    val onClickAction: () -> Unit
)

