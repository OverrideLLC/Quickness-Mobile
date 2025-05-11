package org.override.quickness.feature.home.shop.states

data class ShopState(
    val balance: Double = 0.0,
    val showBottomSheetPlus: Boolean = false,
    val showBottomSheetStudent: Boolean = false,
    val showBottomSheetFamily: Boolean = false,
    val showBottomSheetShop: Boolean = false,
)