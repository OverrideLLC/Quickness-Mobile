package org.quickness.ui.screens.home.qr

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.quickness.SharedPreference
import org.quickness.ui.animations.ContentSwitchAnimation.enterTransition
import org.quickness.ui.animations.ContentSwitchAnimation.exitTransition
import org.quickness.ui.components.ShimmerItem
import org.quickness.utils.`object`.KeysCache.QR_COLOR_KEY
import qrgenerator.qrkitpainter.QrKitBallShape
import qrgenerator.qrkitpainter.QrKitBrush
import qrgenerator.qrkitpainter.QrKitColors
import qrgenerator.qrkitpainter.QrKitErrorCorrection
import qrgenerator.qrkitpainter.QrKitFrameShape
import qrgenerator.qrkitpainter.QrKitLogo
import qrgenerator.qrkitpainter.QrKitLogoKitShape
import qrgenerator.qrkitpainter.QrKitOptions
import qrgenerator.qrkitpainter.QrKitPixelShape
import qrgenerator.qrkitpainter.QrKitShapes
import qrgenerator.qrkitpainter.QrPainter
import qrgenerator.qrkitpainter.createRoundCorners
import qrgenerator.qrkitpainter.rememberQrKitPainter
import qrgenerator.qrkitpainter.solidBrush
import quickness.composeapp.generated.resources.Blanco
import quickness.composeapp.generated.resources.LogoQuicknessQC
import quickness.composeapp.generated.resources.Negro
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.error_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.info_token
import quickness.composeapp.generated.resources.lock_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.lock_open_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.logo_swiftid_centrado

@Composable
fun QrScreen() = Screen()

@OptIn(KoinExperimentalAPI::class)
@Composable
private fun Screen(viewModel: QrViewModel = koinViewModel()) {
    TicketScreen(viewModel)
}

@Composable
private fun TicketScreen(viewModel: QrViewModel) {
    val state = viewModel.qrState.collectAsState().value
    var isVisible by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }
    var isBlurred by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { isVisible = true }

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
                visible = isVisible,
                enter = enterTransition,
                exit = exitTransition
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (!isExpanded) colorScheme.onBackground.copy(alpha = 0.5f) else colorScheme.background)
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
                            visible = !isExpanded,
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
                                    painter = painterResource(Res.drawable.logo_swiftid_centrado),
                                    contentDescription = "Logo",
                                    tint = colorScheme.tertiary,
                                    modifier = Modifier.size(100.dp)
                                )
                            }
                        }

                        // QR Code with smooth animation
                        TicketQRCode(isExpanded, isBlurred, state.qrCode) {
                            isExpanded = !isExpanded
                        }

                        blurQr(isBlurred) {
                            isBlurred = !isBlurred
                        }

                        AnimatedVisibility(
                            visible = !isExpanded,
                            enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                                    slideInVertically(initialOffsetY = { it / 2 }),
                            exit = fadeOut(animationSpec = tween(durationMillis = 500)) +
                                    slideOutVertically(targetOffsetY = { it / 2 })
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            ImportantInfoItem()
                        }
                        Spacer(Modifier.padding(10.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun blurQr(
    isBlurred: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = { onClick() },
        modifier = Modifier.size(30.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = colorScheme.background,
            contentColor = colorScheme.tertiary
        ),
        content = {
            Icon(
                painter = painterResource(if (isBlurred) Res.drawable.lock_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24 else Res.drawable.lock_open_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24),
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
            .background(Color.Transparent)
            .clickable { onClick() }
            .animateContentSize(),
        contentAlignment = Alignment.Center
    ) {
        qrCode?.let {
            Image(
                painter = qrCode,
                contentDescription = "Código QR generado",
                modifier = Modifier
                    .size(qrSize)
                    .blur(if (isBlurred) 20.dp else 0.dp)
                    .background(
                        Color.White
                    )
                    .padding(5.dp)
            )
        } ?: run {
            ShimmerItem()
        }
    }
}

@Composable
private fun ImportantInfoItem() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(Res.drawable.error_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24),
            contentDescription = "Info Icon",
            tint = colorScheme.tertiary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(Res.string.info_token),
            style = typography.bodyMedium,
            color = colorScheme.tertiary,
            fontWeight = FontWeight.SemiBold
        )
    }
}