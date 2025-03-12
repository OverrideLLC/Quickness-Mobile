package com.feature.home.shop.states

import androidx.compose.ui.graphics.Color
import com.shared.resources.drawable.ResourceNameKey
import org.jetbrains.compose.resources.DrawableResource

data class ProductData(
    val text: String,
    val icon: ResourceNameKey,
    val containerColor: Color,
    val iconTint: Color,
    val colorText: Color,
    val brushStartColor: Color? = null,
    val onClickAction: () -> Unit
)