package org.quickness.ui.states

data class PrivacySettingsState(
    val showBottomSheetDownload: Boolean = false,
    val isLoading: Boolean = false,
    val error: Boolean = false,
    val success: Boolean = false,
    val message: String? = null
)
