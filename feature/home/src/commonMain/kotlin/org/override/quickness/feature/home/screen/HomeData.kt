package org.override.quickness.feature.home.screen

data class HomeData(
    val isLoading: Boolean = false,
    val error: String? = null,
    val openCamera: Boolean = false,
)