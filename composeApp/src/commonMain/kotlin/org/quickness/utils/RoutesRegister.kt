package org.quickness.utils

sealed class RoutesRegister(val route: String) {
    data object EmailAndPassword : RoutesRegister("EmailAndPassword")
    data object InformationPersonal : RoutesRegister("InformationPersonal")
    data object Approbation : RoutesRegister("Approbation")
}