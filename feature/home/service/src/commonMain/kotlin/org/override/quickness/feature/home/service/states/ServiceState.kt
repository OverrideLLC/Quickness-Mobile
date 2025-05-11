package org.override.quickness.feature.home.service.states

import org.override.quickness.feature.home.service.lyra.LyraScreen
import org.override.quickness.shared.resources.drawable.ResourceNameKey

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