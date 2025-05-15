package org.override.quickness.feature.home.service.utils

import androidx.compose.runtime.Immutable

@Immutable
data class EvaService(
    val id: String, // Unique identifier for the service, e.g., "gemini", "calculator"
    val name: String, // Display name, e.g., "@gemini", "@calculator"
    val description: String // Optional: A brief description of the service
)