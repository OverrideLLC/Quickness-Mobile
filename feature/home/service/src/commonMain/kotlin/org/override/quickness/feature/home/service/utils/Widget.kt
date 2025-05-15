package org.override.quickness.feature.home.service.utils

import org.jetbrains.compose.resources.DrawableResource

data class Widget(
    val id: String,
    val name: String,
    val icon: DrawableResource,
    val route: String
)
