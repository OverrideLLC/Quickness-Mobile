package org.quickness.utils.enums

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.quickness.utils.routes.RoutesSettings
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.about_us
import quickness.composeapp.generated.resources.account_settings
import quickness.composeapp.generated.resources.add_box_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.admin_panel_settings_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.app_settings
import quickness.composeapp.generated.resources.display_settings
import quickness.composeapp.generated.resources.display_settings_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.help_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.help_center
import quickness.composeapp.generated.resources.language_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.language_settings
import quickness.composeapp.generated.resources.notification_settings
import quickness.composeapp.generated.resources.notifications_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.privacy_settings
import quickness.composeapp.generated.resources.qr_code_2_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.qr_settings
import quickness.composeapp.generated.resources.security_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.security_settings
import quickness.composeapp.generated.resources.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.settings_applications_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.visibility_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24

/**
 * Enum que representa las diferentes secciones de la configuración de la aplicación.
 */
enum class Settings(
    val titleRes: StringResource,
    val iconRes: DrawableResource,
    val route: String
) {
    QR_SETTINGS(
        titleRes = Res.string.qr_settings,
        iconRes = Res.drawable.qr_code_2_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
        route = RoutesSettings.SettingsQr.route
    ),
    ACCOUNT_SETTINGS(
        titleRes = Res.string.account_settings,
        iconRes = Res.drawable.admin_panel_settings_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
        route = RoutesSettings.AccountSettings.route
    ),
    PRIVACY_SETTINGS(
        titleRes = Res.string.privacy_settings,
        iconRes = Res.drawable.visibility_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
        route = RoutesSettings.PrivacySettings.route
    ),
    NOTIFICATION_SETTINGS(
        titleRes = Res.string.notification_settings,
        iconRes = Res.drawable.notifications_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
        route = RoutesSettings.NotificationSettings.route
    ),
    DISPLAY_SETTINGS(
        titleRes = Res.string.display_settings,
        iconRes = Res.drawable.display_settings_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
        route = RoutesSettings.DisplaySettings.route
    ),
    LANGUAGE_SETTINGS(
        titleRes = Res.string.language_settings,
        iconRes = Res.drawable.language_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
        route = RoutesSettings.LanguageSettings.route
    ),
    SECURITY_SETTINGS(
        titleRes = Res.string.security_settings,
        iconRes = Res.drawable.security_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
        route = RoutesSettings.SecuritySettings.route
    ),
    APP_SETTINGS(
        titleRes = Res.string.app_settings,
        iconRes = Res.drawable.settings_applications_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
        route = RoutesSettings.AppSettings.route
    ),
    HELP_CENTER(
        titleRes = Res.string.help_center,
        iconRes = Res.drawable.help_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
        route = RoutesSettings.HelpCenter.route
    ),
    ABOUT_US(
        titleRes = Res.string.about_us,
        iconRes = Res.drawable.add_box_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
        route = RoutesSettings.AboutUs.route
    );

    /**
     * Devuelve la lista de todas las secciones de configuración.
     */
    companion object {
        fun getAllSettings(): List<Settings> = entries
    }
}