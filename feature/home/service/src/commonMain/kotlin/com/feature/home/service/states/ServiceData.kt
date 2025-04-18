package com.feature.home.service.states

import androidx.compose.runtime.Composable
import com.shared.resources.drawable.ResourceNameKey

data class ServiceData(
    val image: ResourceNameKey,
    val titleService: String,
    val color: Long,
    val content: @Composable () -> Unit
)
