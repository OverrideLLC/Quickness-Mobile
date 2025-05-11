package org.override.quickness.shared.utils.routes

sealed class RoutesSettings(val route: String) {
    data object Settings : RoutesSettings("settings")
    data object SettingsQr : RoutesSettings("settings-qr")
    data object AccountSettings : RoutesSettings("account-settings")
    data object PrivacySettings : RoutesSettings("privacy-settings")
    data object NotificationSettings : RoutesSettings("notification-settings")
    data object DisplaySettings : RoutesSettings("display-settings")
    data object LanguageSettings : RoutesSettings("language-settings")
    data object SecuritySettings : RoutesSettings("security-settings")
    data object AppSettings : RoutesSettings("app-settings")
    data object HelpCenter : RoutesSettings("help-center")
    data object AboutUs : RoutesSettings("about-us")
}