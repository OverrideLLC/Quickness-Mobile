package com.feature.home.service.states

import com.feature.home.service.services.lyra.LyraScreen
import com.shared.resources.drawable.ResourceNameKey

data class ServiceState(
    var isLoading: Boolean = false,
    var showBottomSheet: Boolean = false,
    var serviceSelected: ServiceData? = null,
    val serviceList: List<ServiceData> = listOf(
        ServiceData(
            image = ResourceNameKey.LOGO_GAIA_SB,
            titleService = "Lyra",
            color = 0xfffc0c00,
            content = { LyraScreen() }
        ),
    )
)