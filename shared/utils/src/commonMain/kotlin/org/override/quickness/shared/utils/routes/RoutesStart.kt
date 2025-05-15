package org.override.quickness.shared.utils.routes

sealed class RoutesStart(val route: String) {
    data object Home : RoutesStart("Home")
    data object Start : RoutesStart("Start")
    data object Eva : RoutesStart("Eva")
    data object Camera : RoutesStart("Camera")
    data object CamAnalyzer : RoutesStart("CamAnalyzer")
}