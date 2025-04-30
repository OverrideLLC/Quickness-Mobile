package com.quickness.shared.utils.routes

sealed class RoutesStart(val route: String) {
    data object Home : RoutesStart("Home")
    data object Start : RoutesStart("Start")
    data object Camera : RoutesStart("Camera")
}