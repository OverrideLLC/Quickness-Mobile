package org.quickness.utils

sealed class RoutesStart(val route: String) {
    data object Home : RoutesStart("Home")
    data object Start : RoutesStart("Start")
    data object Login : RoutesStart("Login")
    data object Register : RoutesStart("Register")
    data object ForgotPassword : RoutesStart("ForgotPassword")
}