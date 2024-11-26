package org.quickness.utils.routes

sealed class RoutesHome(val route: String) {
    data object Qr : RoutesHome("Qr")
    data object Shop : RoutesHome("Shop")
    data object Settings : RoutesHome("Settings")
    data object Service : RoutesHome("Service")
}