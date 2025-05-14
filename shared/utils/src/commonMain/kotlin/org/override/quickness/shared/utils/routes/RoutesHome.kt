package org.override.quickness.shared.utils.routes

sealed class RoutesHome(val route: String) {
    data object Qr : RoutesHome("Qr")
    data object Shop : RoutesHome("Shop")
    data object Settings : RoutesHome("Config")
    data object Camera : RoutesHome("Camera")
}