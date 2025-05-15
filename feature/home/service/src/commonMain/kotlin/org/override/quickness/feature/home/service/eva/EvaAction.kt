package org.override.quickness.feature.home.service.eva

import org.override.quickness.feature.home.service.utils.EvaService

sealed interface EvaAction {
    data object SendMessage : EvaAction
    data object UpdateNavigationBarVisible : EvaAction
    data class UpdateTextFieldState(val text: String) : EvaAction
    data class SelectService(val service: EvaService) : EvaAction // New action to select a service
    data object DismissServiceSuggestions : EvaAction
    data object OpenCamera : EvaAction
}