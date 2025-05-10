package org.override.quickness.feature.home.service.states

import androidx.compose.runtime.Composable
import org.override.quickness.shared.resources.drawable.ResourceNameKey

data class ServiceData(
    val image: ResourceNameKey,
    val titleService: String,
    val color: Long,
    val content: @Composable () -> Unit
)
