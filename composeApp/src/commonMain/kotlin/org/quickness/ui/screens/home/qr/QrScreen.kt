package org.quickness.ui.screens.home.qr

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
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

@Composable
fun QrScreen(sharedPreference: SharedPreference) = Screen(sharedPreference = sharedPreference)

@OptIn(KoinExperimentalAPI::class)
@Composable
private fun Screen(viewModel: QrViewModel = koinViewModel(), sharedPreference: SharedPreference) {
    TicketScreen(viewModel, sharedPreference)
}

@Composable
private fun TicketScreen(viewModel: QrViewModel, sharedPreference: SharedPreference) {
    var isClick by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { isVisible = true }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = enterTransition,
                    exit = exitTransition
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(colorScheme.onBackground)
                            .padding(32.dp),
                        content = {
                            TicketQRCode(
                                viewModel = viewModel,
                                sharedPreference = sharedPreference,
                                isClick = { isClick = !isClick })
                            AnimatedVisibility(
                                visible = isClick,
                                enter = fadeIn(animationSpec = tween(durationMillis = 200)) +
                                        scaleIn(
                                            initialScale = 0.7f,
                                            animationSpec = tween(durationMillis = 200)
                                        ) +
                                        slideInVertically(
                                            initialOffsetY = { -it },
                                            animationSpec = tween(durationMillis = 200)
                                        ),
                                content = {
                                    DottedLine()
                                    TicketHeader()
                                }
                            )
                        }
                    )
                }
            }
        }
    )
}


@Composable
private fun DottedLine() {
    Canvas(
        modifier = Modifier.fillMaxWidth(),
        onDraw = {
            val dashWidth = 50f
            val dashGap = 50f
            drawLine(
                color = Color.White,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 15f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, dashGap), 0f)
            )
        }
    )
}

@Composable
private fun TicketHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ticket Information",
            style = typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            content = {
                TicketInfoItem(
                    title = "Company",
                    content = "Collectives"
                )
                TicketInfoItem(
                    title = "issues date",
                    content = "8 de sep 2024"
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DottedLine()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 16.dp),
            content = {
                Text(
                    text = "Important information",
                    style = typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ImportantInfoItem(text = "valid: 8 de sep 2024")
                ImportantInfoItem(text = "Single use")
                ImportantInfoItem(text = "Not shared code")
            }
        )
    }
}

@Composable
private fun TicketInfoItem(title: String, content: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Text(
                text = title,
                style = typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Text(text = content, style = typography.bodySmall, color = Color.White)
        }
    )
}

@Composable
private fun ImportantInfoItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp),
        content = {
            Icon(
                painter = painterResource(Res.drawable.error_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24),
                contentDescription = "Info Icon",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = typography.bodyMedium,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    )
}


@Composable
private fun TicketQRCode(
    viewModel: QrViewModel,
    sharedPreference: SharedPreference,
    isClick: () -> Unit
) {
    var qrCodeBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(Unit) {
        qrCodeBitmap = withContext(Dispatchers.IO) {
            viewModel.generateQRCode(sharedPreference)
        }
    }

    Box(
        modifier = Modifier
            .size(400.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                isClick()
            }
            .background(colorScheme.onBackground),
        contentAlignment = Alignment.Center,
        content = {
            qrCodeBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = "Generated QR Code",
                    modifier = Modifier.size(600.dp)
                )
            } ?: run {
                CircularProgressIndicator()
            }
        }
    )
}
