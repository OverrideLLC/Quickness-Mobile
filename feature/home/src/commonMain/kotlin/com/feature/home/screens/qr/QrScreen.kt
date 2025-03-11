package com.feature.home.screens.qr

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.feature.home.plataform.BiometricAuthImpl
import com.feature.home.states.QrState
import com.shared.resources.drawable.ResourceNameKey
import com.shared.resources.strings.Strings
import com.shared.ui.components.animations.ContentSwitchAnimation.enterTransition
import com.shared.ui.components.animations.ContentSwitchAnimation.exitTransition
import com.shared.ui.components.styles.ShimmerItem
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import qrgenerator.qrkitpainter.QrPainter

@Composable
fun QrScreen() = Screen()

@OptIn(KoinExperimentalAPI::class)
@Composable
private fun Screen(viewModel: QrViewModel = koinViewModel()) {
    TicketScreen(
        viewModel = viewModel,
        state = viewModel.qrState.collectAsState().value
    )
}

@Composable
private fun TicketScreen(viewModel: QrViewModel, state: QrState) {
    LaunchedEffect(Unit) {
        viewModel.update { copy(isVisible = true) }
        viewModel.getColors()
        viewModel.updateQrCodeForCurrentInterval()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
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
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth().background(Color.Transparent)
                    ) {
                        // Header Animation
                        AnimatedVisibility(
                            visible = !state.isExpanded,
                            enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                                    slideInVertically(initialOffsetY = { -it / 2 }),
                            exit = fadeOut(animationSpec = tween(durationMillis = 500)) +
                                    slideOutVertically(targetOffsetY = { -it / 2 })
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(viewModel.getDrawable(ResourceNameKey.LOGO_SWIFTID_CENTRADO.name)),
                                    contentDescription = "Logo",
                                    tint = Color(state.colors[0]),
                                    modifier = Modifier.size(100.dp)
                                )
                            }
                        }

                        // QR Code with smooth animation
                        TicketQRCode(
                            isExpanded = state.isExpanded,
                            isBlurred = state.isBlurred,
                            qrCode = state.qrCode,
                            colorBackground = Color(state.colors[1])
                        ) {
                            viewModel.update { copy(isExpanded = !isExpanded) }
                        }

                        blurQr(
                            isBlurred = state.isBlurred,
                            viewModel = viewModel,
                            color = if (state.isExpanded) Color.White else Color(state.colors[0]),
                            onActive = {
                                viewModel.update { copy(isBlurred = true) }
                            },
                            onInactive = {
                                viewModel.update { copy(showBiometric = true) }
                            }
                        )

                        AnimatedVisibility(
                            visible = !state.isExpanded,
                            enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                                    slideInVertically(initialOffsetY = { it / 2 }),
                            exit = fadeOut(animationSpec = tween(durationMillis = 500)) +
                                    slideOutVertically(targetOffsetY = { it / 2 })
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            ImportantInfoItem(color = Color(state.colors[0]), viewModel = viewModel)
                        }
                        Spacer(Modifier.padding(10.dp))
                    }
                }
            }
        }
    }
    if (state.showBiometric)
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
        modifier = Modifier.size(30.dp),
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
    val transition = updateTransition(targetState = isExpanded, label = "QR Code Transition")
    val qrSize by transition.animateDp(
        transitionSpec = { tween(durationMillis = 500) },
        label = "QR Size Animation"
    ) { expanded ->
        if (expanded) 400.dp else 250.dp
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .animateContentSize()
            .background(colorBackground),
        contentAlignment = Alignment.Center
    ) {
        qrCode?.also {
            Image(
                painter = qrCode,
                contentDescription = "Código QR generado",
                modifier = Modifier
                    .size(qrSize)
                    .blur(if (isBlurred) 20.dp else 0.dp)
                    .padding(5.dp)
            )
        } ?: run {
            ShimmerItem()
        }
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