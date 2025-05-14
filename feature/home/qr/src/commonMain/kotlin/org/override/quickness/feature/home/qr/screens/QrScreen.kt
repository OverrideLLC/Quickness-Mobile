package org.override.quickness.feature.home.qr.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.feature.biometric.BiometricAuthImpl
import org.override.quickness.feature.home.qr.states.QrState
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.resources.strings.Strings
import org.override.quickness.shared.ui.animations.ContentSwitchAnimation.enterTransition
import org.override.quickness.shared.ui.animations.ContentSwitchAnimation.exitTransition
import org.override.quickness.shared.ui.styles.ShimmerItem
import qrgenerator.qrkitpainter.QrPainter

@Composable
fun QrScreen(
    paddingValues: PaddingValues
) = Screen(
    paddingValues = paddingValues
)

@Composable
private fun Screen(viewModel: QrViewModel = koinViewModel(), paddingValues: PaddingValues) {
    TicketScreen(
        viewModel = viewModel,
        state = viewModel.qrState.collectAsState().value,
        paddingValues = paddingValues
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TicketScreen(viewModel: QrViewModel, state: QrState, paddingValues: PaddingValues) {
    LaunchedEffect(Unit) {
        viewModel.update { copy(isVisible = true) }
        viewModel.getColors()
        viewModel.updateQrCodeForCurrentInterval()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedVisibility(
            visible = state.isVisible,
            enter = enterTransition,
            exit = exitTransition
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (!state.isExpanded)
                            Color(state.colors[1]).copy(0.5f)
                        else
                            Color.Transparent
                    )
                    .height(400.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .widthIn(
                            min = 400.dp,
                            max = 500.dp
                        )
                        .background(Color.Transparent)
                ) {
                    Icon(
                        painter = painterResource(viewModel.getDrawable(ResourceNameKey.LOGOQUICKNESSQC.name)),
                        contentDescription = "Logo",
                        tint = if (!state.isExpanded) Color(state.colors[0]) else Color.Transparent,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    // QR Code with smooth animation
                    TicketQRCode(
                        isExpanded = state.isExpanded,
                        isBlurred = state.isBlurred,
                        qrCode = state.qrCode,
                        colorBackground = Color(state.colors[1])
                    ) {
                        viewModel.update { copy(isExpanded = !isExpanded) }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedVisibility(
                            visible = !state.isExpanded,
                            enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                                    slideInVertically(initialOffsetY = { -it / 2 }),
                            exit = fadeOut(animationSpec = tween(durationMillis = 500)) +
                                    slideOutVertically(targetOffsetY = { -it / 2 })
                        ) {
                            blurQr(
                                isBlurred = state.isBlurred,
                                viewModel = viewModel,
                                color = if (state.isExpanded) colorScheme.tertiary else Color(state.colors[0]),
                                onActive = {
                                    viewModel.update { copy(isBlurred = true) }
                                },
                                onInactive = {
                                    viewModel.update { copy(showBiometric = true) }
                                }
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        IconButton(
                            onClick = {
                                viewModel.updateQrCodeForCurrentInterval()
                            },
                            modifier = Modifier.size(24.dp),
                            content = {
                                Icon(
                                    painter = painterResource(viewModel.getDrawable(ResourceNameKey.REFRESH_24DP_E3E3E3_FILL0_WGHT400_GRAD0_OPSZ24.name)),
                                    tint = if (state.isExpanded) colorScheme.tertiary else Color(state.colors[0]),
                                    contentDescription = "Boton de refrescar",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        )
                    }
                }
            }
        }
    }
    if (state.showBiometric) {
        print("showBiometric")
        BiometricAuthImpl().Authenticate(
            onSuccess = {
                viewModel.update {
                    copy(
                        isBlurred = false,
                        showBiometric = false
                    )
                }
            },
            onError = {
                viewModel.update {
                    copy(
                        isBlurred = true,
                        showBiometric = false
                    )
                }
            }
        )
    }
}


@Composable
private fun blurQr(
    isBlurred: Boolean,
    viewModel: QrViewModel,
    color: Color,
    onActive: () -> Unit,
    onInactive: () -> Unit
) {
    IconButton(
        onClick = {
            if (isBlurred) onInactive() else onActive()
        },
        modifier = Modifier.size(24.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = colorScheme.tertiary
        ),
        content = {
            Icon(
                painter = painterResource(
                    viewModel.getDrawable(
                        if (isBlurred)
                            ResourceNameKey.LOCK_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name
                        else
                            ResourceNameKey.LOCK_OPEN_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name
                    )
                ),
                tint = color,
                contentDescription = "Boton de ocultar qr",
            )
        }
    )
}

@Composable
private fun TicketQRCode(
    isExpanded: Boolean,
    isBlurred: Boolean,
    qrCode: QrPainter?,
    colorBackground: Color,
    onClick: () -> Unit,
) {
    qrCode?.also {
        AnimatedContent(
            targetState = isExpanded,
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .animateContentSize()
                    .background(colorBackground),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = qrCode,
                    contentDescription = "Código QR generado",
                    modifier = Modifier.size(250.dp).blur(if (isBlurred) 20.dp else 0.dp)
                )

            }
        }
    } ?: run {
        ShimmerItem()
    }
}

@Composable
private fun ImportantInfoItem(color: Color, viewModel: QrViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(viewModel.getDrawable(ResourceNameKey.ERROR_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)),
            contentDescription = "Info Icon",
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = Strings.GeneralAppStrings.INFO_TOKEN,
            style = typography.bodyMedium,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}