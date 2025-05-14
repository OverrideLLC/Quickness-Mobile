package org.override.quickness.feature.home.componets

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import org.override.quickness.feature.home.screen.HomeViewModel
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.utils.routes.RoutesHome

@Composable
internal fun BottomBar(
    navigationController: NavController,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    topName: (String) -> Unit
) {
    var selected by remember { mutableStateOf(ResourceNameKey.QR_CODE_2_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24) }
    val selectedShopping =
        selected == ResourceNameKey.SHOPPING_CART_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24
    var selectedQr = selected == ResourceNameKey.QR_CODE_2_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24
    var selectedConfig = selected == ResourceNameKey.SETTINGS_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24
    val scaleSelected = 0.6f
    val scaleNotSelected = .2f

    val scaleSpec = tween<Float>(
        durationMillis = 800,
        delayMillis = 0,
    )

    var scaleAnimationShopping = animateFloatAsState(
        targetValue = if (selectedShopping) scaleSelected else scaleNotSelected,
        label = "",
        animationSpec = scaleSpec,
    )
    var scaleAnimationQr = animateFloatAsState(
        targetValue = if (selectedQr) scaleSelected else scaleNotSelected,
        label = "",
        animationSpec = scaleSpec,
    )
    var scaleAnimationConfig = animateFloatAsState(
        targetValue = if (selectedConfig) scaleSelected else scaleNotSelected,
        label = "",
        animationSpec = scaleSpec,
    )

    Box(
        modifier = modifier
            .widthIn(
                min = 360.dp,
                max = 400.dp
            )
            .height(height = 40.dp)
            .background(
                color = colorScheme.surfaceContainer,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center,
        content = {
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                content = {
                    BottomAppBarIcon(
                        iconResSelected = ResourceNameKey.SHOPPING_CART_24DP_E8EAED_FILL1_WGHT400_GRAD0_OPSZ24,
                        iconResNotSelected = ResourceNameKey.SHOPPING_CART_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
                        iconSelected = selected,
                        viewModel = viewModel,
                        modifier = Modifier.weight(scaleAnimationShopping.value),
                        name = "Shop",
                        onClick = {
                            selected =
                                ResourceNameKey.SHOPPING_CART_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24
                            topName(RoutesHome.Shop.route)
                            navigationController.navigate(RoutesHome.Shop.route) { popUpTo(0) }
                        }
                    )
                    BottomAppBarIcon(
                        iconResSelected = ResourceNameKey.QR_CODE_2_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
                        iconResNotSelected = ResourceNameKey.QR_CODE_2_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
                        iconSelected = selected,
                        viewModel = viewModel,
                        modifier = Modifier.weight(scaleAnimationQr.value),
                        name = "Qr",
                        onClick = {
                            selected =
                                ResourceNameKey.QR_CODE_2_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24
                            topName(RoutesHome.Qr.route)
                            navigationController.navigate(RoutesHome.Qr.route) { popUpTo(0) }
                        }
                    )
                    BottomAppBarIcon(
                        iconResNotSelected = ResourceNameKey.SETTINGS_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24,
                        iconResSelected = ResourceNameKey.SETTINGS_24DP_E8EAED_FILL1_WGHT400_GRAD0_OPSZ24,
                        iconSelected = selected,
                        viewModel = viewModel,
                        modifier = Modifier.weight(scaleAnimationConfig.value),
                        name = "Config",
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun BottomAppBarIcon(
    iconResSelected: ResourceNameKey,
    iconResNotSelected: ResourceNameKey,
    iconSelected: ResourceNameKey,
    viewModel: HomeViewModel,
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val isSelected = iconResSelected == iconSelected || iconResNotSelected == iconSelected
    val iconResSelected =
        if (iconSelected == iconResNotSelected) iconResSelected else iconResNotSelected

    Box(
        modifier = modifier
            .height(40.dp)
            .width(120.dp)
            .clip(CircleShape)
            .background(
                if (isSelected) colorScheme.primaryContainer else colorScheme.surfaceContainer
            ),
        contentAlignment = Alignment.Center,
        content = {
            AnimatedContent(
                transitionSpec = {
                    scaleIn(tween(500)) togetherWith (scaleOut(tween(500)))
                },
                targetState = isSelected,
                content = { isSelected ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (isSelected) colorScheme.primaryContainer else colorScheme.surfaceContainer,
                                shape = shapes.small
                            )
                            .clickable(
                                enabled = !isSelected
                            ) {
                                if (!isSelected) onClick()
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        if (isSelected) {
                            IconButton(
                                enabled = !isSelected,
                                onClick = { },
                                colors = IconButtonDefaults.iconButtonColors(),
                                content = {
                                    Icon(
                                        painter = painterResource(
                                            viewModel.getDrawable(
                                                iconResSelected.name
                                            )
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier.size(30.dp),
                                        tint = if (isSelected) colorScheme.tertiary else colorScheme.onSurface
                                    )
                                }
                            )
                            Text(
                                text = name,
                                color = if (isSelected) colorScheme.tertiary else colorScheme.onSurface,
                                style = TextStyle(
                                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        } else {
                            IconButton(
                                enabled = !isSelected,
                                onClick = onClick,
                                colors = IconButtonDefaults.iconButtonColors(),
                                content = {
                                    Icon(
                                        painter = painterResource(
                                            viewModel.getDrawable(
                                                iconResSelected.name
                                            )
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier.size(30.dp),
                                        tint = if (isSelected) colorScheme.tertiary else colorScheme.onSurface
                                    )
                                }
                            )
                        }
                    }
                }
            )
        }
    )
}