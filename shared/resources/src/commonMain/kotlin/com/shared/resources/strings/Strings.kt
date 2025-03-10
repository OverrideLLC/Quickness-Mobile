package com.shared.resources.strings

sealed class Strings {
    object GeneralAppStrings {
        const val APP_NAME = "Quickness"
        const val OVERRIDE = "Override"
        const val POWERED = "Powered by"
        const val CREDIT = "Credits"
        const val INFO_TOKEN = "This ticket is valid for one use only."
    }

    object Authentication {
        const val LOGIN = "Sign In"
        const val REGISTER = "Sign Up"
        const val FORGOT_PASSWORD = "Forgot Password"
        const val EMAIL = "Email"
        const val PASSWORD = "Password"
        const val CONFIRM_PASSWORD = "Confirm Password"
        const val VERIFICATION_CODE = "Verification Code"
    }

    object PersonalInformation {
        const val LAST_NAME = "Last Name"
        const val CURP = "CURP"
        const val NUMBER_PHONE = "Number Phone"
    }

    object TermsAndPolicies {
        const val TERMS_AND_CONDITIONS = "Terms and Conditions"
        const val TERMS_AND_CONDITIONS_DESCRIPTION = "By registering, you agree to our terms and conditions."
        const val PRIVACY_POLICY = "Privacy Policy"
        const val PRIVACY_POLICY_DESCRIPTION = "Read our privacy policy to learn more."
        const val DATA_ANALYTICS = "Data Analytics"
        const val DATA_ANALYTICS_DESCRIPTION = "Consent to the use of anonymized data."
        const val READ_MORE = "Read more"
    }

    object Navigation {
        const val NEXT = "Next"
        const val SETTINGS = "Settings"
    }

    object QrSettings {
        const val QR_SETTINGS = "QR Settings"
        const val FORMAT_QR_SETTINGS = "QR Format"
        const val COLOR_QR_SETTINGS = "QR Color"
        const val ROUNDED_QR_SETTINGS = "Rounded QR"
        const val IMPROVE_QR_READABILITY = "Activating this option improves the readability of the QR code by adjusting its design, ensuring better scanning compatibility with most devices."
        const val EXPIRE_QR_CODE = "Expire QR Code: "
    }

    object GeneralSettings {
        const val ACCOUNT_SETTINGS = "Account Settings"
        const val PRIVACY_SETTINGS = "Privacy Settings"
        const val NOTIFICATION_SETTINGS = "Notification Settings"
        const val DISPLAY_SETTINGS = "Display Settings"
        const val LANGUAGE_SETTINGS = "Language"
        const val SECURITY_SETTINGS = "Security"
        const val APP_SETTINGS = "App Settings"
    }

    object AccountSettings {
        const val PROFILE = "Profile"
        const val CHANGE_EMAIL = "Change Email"
        const val NEW_EMAIL = "New Email"
        const val UPDATE_EMAIL = "Update Email"
        const val CHANGE_PASSWORD = "Change Password"
        const val CHANGE_PASSWORD_INFO = "You can request a password change. You will receive an email with instructions."
        const val REQUEST_PASSWORD_CHANGE = "Request Password Change"
        const val DELETE_ACCOUNT = "Delete Account"
        const val DELETE_ACCOUNT_WARNING = "Deleting your account is an irreversible action. You will lose access to all your data and services associated with this account."
        const val CONFIRM_DELETE_ACCOUNT = "Delete Account"
        const val LOG_OUT = "Log Out"
        const val LOG_OUT_WARNING = "Logging out means you will need to sign in again to access your account."
        const val CONFIRM_LOG_OUT = "Log Out"
    }

    object Profile {
        const val NAME = "Name"
        const val UID = "UID:"
    }

    object PrivacySettings {
        const val MANAGE_DATA = "Manage My Data"
        const val DATA_DOWNLOAD = "Download My Data"
        const val DATA_DELETION_REQUEST = "Request Data Deletion"
    }

    object NotificationSettings {
        const val PUSH_NOTIFICATIONS = "Push Notifications"
        const val EMAIL_NOTIFICATIONS = "Email Notifications"
        const val SMS_NOTIFICATIONS = "SMS Notifications"
        const val IN_APP_NOTIFICATIONS = "In-App Notifications"
        const val SOUND_AND_VIBRATION = "Sound and Vibration"
    }

    object DisplaySettings {
        const val DARK_MODE = "Dark Mode"
        const val LIGHT_MODE = "Light Mode"
        const val SYSTEM_DEFAULT = "System Default"
        const val FONT_SIZE = "Font Size"
        const val THEME = "Theme"
    }

    object LanguageSettings {
        const val SELECT_LANGUAGE = "Select Language"
        const val LANGUAGE_ENGLISH = "English"
        const val LANGUAGE_SPANISH = "Spanish"
        const val LANGUAGE_FRENCH = "French"
        const val LANGUAGE_GERMAN = "German"
    }

    object SecuritySettings {
        const val ENABLE_2FA = "Enable Two-Factor Authentication"
        const val CHANGE_PIN = "Change PIN"
        const val BIOMETRIC_AUTHENTICATION = "Biometric Authentication"
        const val SESSION_MANAGEMENT = "Manage Sessions"
    }

    object AppSettings {
        const val VERSION = "Version"
        const val ABOUT_US = "About Us"
        const val FEEDBACK = "Send Feedback"
        const val RATE_US = "Rate Us"
        const val HELP_CENTER = "Help Center"
        const val CONTACT_SUPPORT = "Contact Support"
        const val LEGAL = "Legal"
        const val TERMS_OF_SERVICE = "Terms of Service"
        const val OPEN_SOURCE_LICENSES = "Open Source Licenses"
    }

    object AdvancedSettings {
        const val DEVELOPER_OPTIONS = "Developer Options"
        const val RESET_APP = "Reset App"
        const val CLEAR_CACHE = "Clear Cache"
        const val LOG_OUT_EVERYWHERE = "Log Out Everywhere"
        const val DIAGNOSTICS = "Diagnostics"
    }
}