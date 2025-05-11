package org.override.quickness.feature.home.componets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.override.quickness.feature.home.screen.HomeViewModel
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.jetbrains.compose.resources.painterResource
import org.override.quickness.shared.utils.routes.RoutesHome

@Composable
internal fun BottomBar(
    navigationController: NavController,
    viewModel: HomeViewModel,
    topName: (String) -> Unit
) {
    var selected by remember { mutableStateOf(ResourceNameKey.QR_CODE_2_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24) }

    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                height = 60.dp
            )
            .background(
                color = colorScheme.background,
                shape = RoundedCornerShape(40.dp)
            ),
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                content = {
                    BottomAppBarIcon(
                        iconResSelected = ResourceNameKey.SHOPPING_CART_24DP_E8EAED_FILL1_WGHT400_GRAD0_OPSZ24,
                        iconResNotSelected = ResourceNameKey.SHOPPING_CART_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
                        iconSelected = selected,
                        viewModel = viewModel,
                        onClick = {
                            selected =
                                ResourceNameKey.SHOPPING_CART_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24
                            topName(RoutesHome.Shop.route)
                            navigationController.navigate(RoutesHome.Shop.route) { popUpTo(0) }
                        }
                    )
                    BottomAppBarIcon(
                        iconResSelected = ResourceNameKey.PHOTO_CAMERA_48DP_E3E3E3_FILL1_WGHT400_GRAD0_OPSZ48,
                        iconResNotSelected = ResourceNameKey.PHOTO_CAMERA_24DP_E3E3E3_FILL0_WGHT400_GRAD0_OPSZ24,
                        iconSelected = selected,
                        viewModel = viewModel,
                        onClick = {
                            selected = ResourceNameKey.PHOTO_CAMERA_24DP_E3E3E3_FILL0_WGHT400_GRAD0_OPSZ24
                            topName(RoutesHome.Camera.route)
                            navigationController.navigate(RoutesHome.Camera.route) { popUpTo(0) }
                        }
                    )
                    BottomAppBarIcon(
                        iconResSelected = ResourceNameKey.QR_CODE_2_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
                        iconResNotSelected = ResourceNameKey.QR_CODE_2_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
                        iconSelected = selected,
                        viewModel = viewModel,
                        onClick = {
                            selected =
                                ResourceNameKey.QR_CODE_2_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24
                            topName(RoutesHome.Qr.route)
                            navigationController.navigate(RoutesHome.Qr.route) { popUpTo(0) }
                        }
                    )
                    BottomAppBarIcon(
                        iconResNotSelected = ResourceNameKey.MAP_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
                        iconResSelected = ResourceNameKey.MAP_24DP_E8EAED_FILL1_WGHT400_GRAD0_OPSZ24,
                        iconSelected = selected,
                        viewModel = viewModel,
                        onClick = {
                            selected = ResourceNameKey.MAP_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24
                            topName(RoutesHome.Service.route)
                            navigationController.navigate(RoutesHome.Service.route) { popUpTo(0) }
                        }
                    )
                    BottomAppBarIcon(
                        iconResNotSelected = ResourceNameKey.SETTINGS_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
                        iconResSelected = ResourceNameKey.SETTINGS_24DP_E8EAED_FILL1_WGHT400_GRAD0_OPSZ24,
                        iconSelected = selected,
                        viewModel = viewModel,
                        onClick = {
                            selected =
                                ResourceNameKey.SETTINGS_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24
                            topName(RoutesHome.Settings.route)
                            navigationController.navigate(RoutesHome.Settings.route) { popUpTo(0) }
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun BottomAppBarIcon(
    iconResSelected: ResourceNameKey,
    iconResNotSelected: ResourceNameKey,
    iconSelected: ResourceNameKey,
    viewModel: HomeViewModel,
    onClick: () -> Unit,
) {
    val scale = remember { Animatable(1f) }
    val isSelected = iconResSelected == iconSelected || iconResNotSelected == iconSelected
    val iconResSelected =
        if (iconSelected == iconResNotSelected) iconResSelected else iconResNotSelected

    LaunchedEffect(isSelected) {
        scale.animateTo(
            targetValue = if (isSelected) 1.4f else 1f,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
        )
    }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Transparent)
            .scale(scale.value),
        content = {
            IconButton(
                onClick = onClick,
                colors = IconButtonDefaults.iconButtonColors(),
                content = {
                    Icon(
                        painter = painterResource(viewModel.getDrawable(iconResSelected.name)),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp),
                        tint = if (isSelected) colorScheme.primary else colorScheme.tertiary
                    )
                }
            )
        }
    )
}