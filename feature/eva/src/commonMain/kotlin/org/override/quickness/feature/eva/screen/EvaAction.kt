package org.override.quickness.feature.eva.screen

import androidx.compose.ui.graphics.ImageBitmap
import org.override.quickness.feature.eva.utils.EvaService

sealed interface EvaAction {
    data object SendMessage : EvaAction
    data object UpdateNavigationBarVisible : EvaAction
    data class UpdateTextFieldState(val text: String) : EvaAction
    data class SelectService(val service: EvaService) : EvaAction // New action to select a service
    data object DismissServiceSuggestions : EvaAction
    data object OpenCamera : EvaAction
    data class SelectImage(val image: ImageBitmap) : EvaAction
}