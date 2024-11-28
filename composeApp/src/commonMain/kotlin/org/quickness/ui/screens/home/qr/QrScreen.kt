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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.quickness.SharedPreference
import org.quickness.ui.animations.ContentSwitchAnimation.enterTransition
import org.quickness.ui.animations.ContentSwitchAnimation.exitTransition
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.error_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.logo_swiftid_centrado

@Composable
fun QrScreen(sharedPreference: SharedPreference) = Screen(sharedPreference = sharedPreference)

@OptIn(KoinExperimentalAPI::class)
@Composable
private fun Screen(viewModel: QrViewModel = koinViewModel(), sharedPreference: SharedPreference) {
    TicketScreen(viewModel, sharedPreference)
}

@Composable
private fun TicketScreen(viewModel: QrViewModel, sharedPreference: SharedPreference) {
    var isVisible by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { isVisible = true }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
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
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    colorScheme.background,
                                    colorScheme.background,
                                    colorScheme.background
                                )
                            )
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Header Animation
                        AnimatedVisibility(
                            visible = !isExpanded,
                            enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                                    slideInVertically(initialOffsetY = { -it / 2 }),
                            exit = fadeOut(animationSpec = tween(durationMillis = 500)) +
                                    slideOutVertically(targetOffsetY = { -it / 2 })
                        ) {
                            TicketHeader()
                        }

                        // QR Code with smooth animation
                        TicketQRCode(viewModel, sharedPreference, isExpanded) {
                            isExpanded = !isExpanded
                        }

                        // Info Animation
                        AnimatedVisibility(
                            visible = !isExpanded,
                            enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                                    slideInVertically(initialOffsetY = { it / 2 }),
                            exit = fadeOut(animationSpec = tween(durationMillis = 500)) +
                                    slideOutVertically(targetOffsetY = { it / 2 })
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            ImportantInfoItem("Este ticket es válido para un solo uso.")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TicketQRCode(
    viewModel: QrViewModel,
    sharedPreference: SharedPreference,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    var qrCodeBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(Unit) {
        qrCodeBitmap = withContext(Dispatchers.IO) {
            viewModel.generateQRCode(sharedPreference)
        }
    }

    val transition = updateTransition(targetState = isExpanded, label = "QR Code Transition")
    val qrSize by transition.animateDp(
        transitionSpec = { tween(durationMillis = 500) },
        label = "QR Size Animation"
    ) { expanded ->
        if (expanded) 400.dp else 200.dp
    }
    val padding by transition.animateDp(
        transitionSpec = { tween(durationMillis = 500) },
        label = "Padding Animation"
    ) { expanded ->
        if (expanded) 0.dp else 16.dp
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(colorScheme.tertiary)
            .padding(padding)
            .clickable { onClick() }
            .animateContentSize(),
        contentAlignment = Alignment.Center
    ) {
        qrCodeBitmap?.let {
            Image(
                bitmap = it,
                contentDescription = "Código QR generado",
                modifier = Modifier.size(qrSize)
            )
        } ?: run {
            CircularProgressIndicator(color = colorScheme.onPrimary)
        }
    }
}


@Composable
private fun ImportantInfoItem(text: String) {
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
            text = text,
            style = typography.bodyMedium,
            color = colorScheme.tertiary,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun TicketHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorScheme.onBackground)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Transporte Público",
                style = typography.titleMedium,
                color = colorScheme.tertiary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Ticket #12345",
                style = typography.bodySmall,
                color = colorScheme.tertiary
            )
        }
        Icon(
            painter = painterResource(Res.drawable.logo_swiftid_centrado),
            contentDescription = "Logo",
            tint = colorScheme.tertiary,
            modifier = Modifier.size(60.dp)
        )
    }
}