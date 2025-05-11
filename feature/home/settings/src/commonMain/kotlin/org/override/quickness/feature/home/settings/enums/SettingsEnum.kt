package org.override.quickness.feature.home.settings.enums

import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.resources.strings.Strings
import org.override.quickness.shared.utils.routes.RoutesSettings

/**
 * Enum que representa las diferentes secciones de la configuración de la aplicación.
 */
enum class SettingsEnum(
    val title: String,
    val icon: ResourceNameKey,
    val route: String
) {
    QR_SETTINGS(
        title = Strings.QrSettings.QR_SETTINGS,
        icon = ResourceNameKey.QR_CODE_2_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
        route = RoutesSettings.SettingsQr.route
    ),
    ACCOUNT_SETTINGS(
        title = Strings.GeneralSettings.ACCOUNT_SETTINGS,
        icon = ResourceNameKey.ADMIN_PANEL_SETTINGS_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
        route = RoutesSettings.AccountSettings.route
    ),
    PRIVACY_SETTINGS(
        title = Strings.GeneralSettings.PRIVACY_SETTINGS,
        icon = ResourceNameKey.VISIBILITY_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
        route = RoutesSettings.PrivacySettings.route
    ),
    NOTIFICATION_SETTINGS(
        title = Strings.GeneralSettings.NOTIFICATION_SETTINGS,
        icon = ResourceNameKey.NOTIFICATIONS_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
        route = RoutesSettings.NotificationSettings.route
    ),
    DISPLAY_SETTINGS(
        title = Strings.GeneralSettings.DISPLAY_SETTINGS,
        icon = ResourceNameKey.DISPLAY_SETTINGS_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
        route = RoutesSettings.DisplaySettings.route
    ),
    LANGUAGE_SETTINGS(
        title = Strings.GeneralSettings.LANGUAGE_SETTINGS,
        icon = ResourceNameKey.LANGUAGE_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
        route = RoutesSettings.LanguageSettings.route
    ),
    SECURITY_SETTINGS(
        title = Strings.GeneralSettings.SECURITY_SETTINGS,
        icon = ResourceNameKey.SECURITY_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
        route = RoutesSettings.SecuritySettings.route
    ),
    APP_SETTINGS(
        title = Strings.GeneralSettings.APP_SETTINGS,
        icon = ResourceNameKey.SETTINGS_APPLICATIONS_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
        route = RoutesSettings.AppSettings.route
    ),
    HELP_CENTER(
        title = Strings.AppSettings.HELP_CENTER,
        icon = ResourceNameKey.HELP_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
        route = RoutesSettings.HelpCenter.route
    ),
    ABOUT_US(
        title = Strings.AppSettings.ABOUT_US,
        icon = ResourceNameKey.ADD_BOX_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
        route = RoutesSettings.AboutUs.route
    );

    /**
     * Devuelve la lista de todas las secciones de configuración.
     */
    companion object {
        fun getAllSettings(): List<SettingsEnum> = entries
    }
}