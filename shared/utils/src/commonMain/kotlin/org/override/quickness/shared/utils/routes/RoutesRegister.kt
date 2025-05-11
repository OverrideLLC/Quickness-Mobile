package org.override.quickness.shared.utils.routes

/**
 * Defines the navigation routes for the registration flow.
 *
 * Each route is represented as a sealed class inheriting from `RoutesRegister`,
 * ensuring type safety and immutability in navigation.
 *
 * @property route The string representation of the route.
 */
sealed class RoutesRegister(val route: String) {

    /**
     * Route for the email and password input screen during registration.
     */
    data object EmailAndPassword : RoutesRegister("EmailAndPassword")

    /**
     * Route for the personal information input screen during registration.
     */
    data object InformationPersonal : RoutesRegister("InformationPersonal")

    /**
     * Route for the terms and conditions approval screen during registration.
     */
    data object Approbation : RoutesRegister("Approbation")
}
