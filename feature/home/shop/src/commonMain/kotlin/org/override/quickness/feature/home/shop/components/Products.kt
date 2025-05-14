package org.override.quickness.feature.home.shop.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@Composable
fun Products(
    containerColor: Color,
    iconTint: Color,
    text: String,
    colorText: Color,
    brushStartColor: Color,
    brushEndColor: Color = colorScheme.background,
    icon: DrawableResource,
    onClick: () -> Unit
) {
    val animatedOffset = remember { Animatable(0f) }

    // Lanzar animación para moverse hacia el final
    LaunchedEffect(Unit) {
        animatedOffset.animateTo(
            targetValue = 1000f,
            animationSpec = tween(
                durationMillis = 5000,
                easing = LinearEasing
            )
        )
    }

    // Estado para la rotación de la tarjeta
    val rotation = remember { Animatable(0f) }

    // Controlador de visibilidad del BottomSheet
    val scope = rememberCoroutineScope()

    Card(
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .graphicsLayer(rotationY = rotation.value) // Aplica la rotación
            .clickable {
                scope.launch {
                    onClick()
                    rotation.animateTo(
                        targetValue = 360f,
                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                    )
                    rotation.snapTo(0f) // Resetea la rotación
                }
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            brushStartColor,
                            colorScheme.onBackground,
                            brushEndColor
                        ),
                        start = Offset(0f, animatedOffset.value),
                        end = Offset(0f,animatedOffset.value + 700f)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    tint = iconTint,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = text,
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    fontSize = 25.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun QuicknessShop(
    icon: DrawableResource
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(60.dp)
                .padding(bottom = 16.dp)
        )
        Text(
            text = "Quickness Shop",
            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
            fontSize = 36.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            onClick = {
            },
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
            ),
            content = {
                Text(
                    text = "Buy Now",
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    fontSize = 24.sp,
                    color = Color(0xFF00FF00),
                )
            }
        )
    }
}

@Composable
fun QuicknessPlus(icon: DrawableResource) {
    QuicknessCard(
        title = "Quickness Plus",
        benefits = listOf(
            "Access to a map with routes and service views.",
            "Access to real-time transportation view functionality.",
            "Access to traffic alerts or route changes.",
            "Access to multiple payments in one purchase.",
            "Access to special offers.",
            "Access to NFC functionality (Mobile, not cards).",
            "Access to an enhanced emergency button."
        ),
        backgroundGradient = listOf(Color(0xFFFFD700), Color(0xFFFDA400)),
        icon = icon
    )
}

@Composable
fun QuicknessStudent(icon: DrawableResource) {
    QuicknessCard(
        title = "Quickness Student",
        benefits = listOf(
            "Access to a map with routes.",
            "Access to route changes.",
            "Access to an enhanced emergency button."
        ),
        backgroundGradient = listOf(Color(0xFF42A5F5), Color(0xFF1E88E5)),
        icon = icon
    )
}

@Composable
fun QuicknessFamily(icon: DrawableResource) {
    QuicknessCard(
        title = "Quickness Family",
        benefits = listOf(
            "Includes all benefits of Quickness Plus.",
            "Access to parental control:",
            "  - Real-time family tracking.",
            "  - Real-time payment tracking.",
            "  - Notifications for arrivals at custom points (e.g., school).",
            "  - Enhanced emergency button for minors.",
            "Access for up to 4 child accounts."
        ),
        backgroundGradient = listOf(Color(0xFF66BB6A), Color(0xFF43A047)),
        icon = icon
    )
}

@Composable
fun QuicknessCard(
    title: String,
    benefits: List<String>,
    backgroundGradient: List<Color>,
    icon: DrawableResource,
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(backgroundGradient)
            )
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(backgroundGradient)
                )
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(bottom = 16.dp)
                )
                Text(
                    text = title,
                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                    fontSize = 36.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                benefits.forEach { benefit ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = benefit,
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}