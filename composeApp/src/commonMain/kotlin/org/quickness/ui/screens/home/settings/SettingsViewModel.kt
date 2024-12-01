package org.quickness.ui.screens.home.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.quickness.SharedPreference
import org.quickness.interfaces.QRCodeGenerator
import org.quickness.utils.routes.RoutesSettings
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.about_us
import quickness.composeapp.generated.resources.account_settings
import quickness.composeapp.generated.resources.app_settings
import quickness.composeapp.generated.resources.display_settings
import quickness.composeapp.generated.resources.help_center
import quickness.composeapp.generated.resources.language_settings
import quickness.composeapp.generated.resources.notification_settings
import quickness.composeapp.generated.resources.privacy_settings
import quickness.composeapp.generated.resources.qr_code_2_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.qr_settings
import quickness.composeapp.generated.resources.security_settings
import quickness.composeapp.generated.resources.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24

class SettingsViewModel(
    private val sharedPreference: SharedPreference,
) : ViewModel() {
    data class SettingsState(
        val settingsMap: Map<String, List<Any>> = mapOf(
            "settings" to listOf(
                Res.string.qr_settings,
                Res.drawable.qr_code_2_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                RoutesSettings.SettingsQr.route
            ),
            "account_settings" to listOf(
                Res.string.account_settings,
                Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                RoutesSettings.AccountSettings.route
            ),
            "privacy_settings" to listOf(
                Res.string.privacy_settings,
                Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                RoutesSettings.PrivacySettings.route
            ),
            "notification_settings" to listOf(
                Res.string.notification_settings,
                Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                RoutesSettings.NotificationSettings.route
            ),
            "display_settings" to listOf(
                Res.string.display_settings,
                Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                RoutesSettings.DisplaySettings.route
            ),
            "language_settings" to listOf(
                Res.string.language_settings,
                Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                RoutesSettings.LanguageSettings.route
            ),
            "security_settings" to listOf(
                Res.string.security_settings,
                Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                RoutesSettings.SecuritySettings.route
            ),
            "app_settings" to listOf(
                Res.string.app_settings,
                Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                RoutesSettings.AppSettings.route
            ),
            "help_center" to listOf(
                Res.string.help_center,
                Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                RoutesSettings.HelpCenter.route
            ),
            "about_us" to listOf(
                Res.string.about_us,
                Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                RoutesSettings.AboutUs.route
            )
        )

    )

    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState = _settingsState
}