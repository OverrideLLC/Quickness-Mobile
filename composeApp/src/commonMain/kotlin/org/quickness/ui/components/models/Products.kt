package org.quickness.ui.components.models

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.quickness.ui.theme.Success
import quickness.composeapp.generated.resources.LogoQuicknessQC
import quickness.composeapp.generated.resources.Poppins_Bold
import quickness.composeapp.generated.resources.Poppins_Light
import quickness.composeapp.generated.resources.Poppins_Medium
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.book_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.family_restroom_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.shopping_cart_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24


@OptIn(ExperimentalMaterial3Api::class)
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
            .height(250.dp)
            .graphicsLayer(rotationY = rotation.value) // Aplica la rotación
            .clickable {
                scope.launch {
                    onClick()
                    rotation.animateTo(
                        targetValue = 360f,
                        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
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
                        start = Offset(animatedOffset.value, 0f),
                        end = Offset(animatedOffset.value + 500f, 500f)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(icon),
                    tint = iconTint,
                    contentDescription = null,
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = text,
                    fontFamily = FontFamily(Font(Res.font.Poppins_Light)),
                    fontSize = 25.sp,
                    color = colorText,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun QuicknessShop() {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(Res.drawable.shopping_cart_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(60.dp)
                .padding(bottom = 16.dp)
        )
        Text(
            text = "Quickness Shop",
            fontFamily = FontFamily(Font(Res.font.Poppins_Bold)),
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
                    fontFamily = FontFamily(Font(Res.font.Poppins_Medium)),
                    fontSize = 24.sp,
                    color = Success,
                )
            }
        )
    }
}

@Composable
fun QuicknessPlus() {
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
        icon = Res.drawable.LogoQuicknessQC // Puedes personalizar con tus propios iconos
    )
}

@Composable
fun QuicknessStudent() {
    QuicknessCard(
        title = "Quickness Student",
        benefits = listOf(
            "Access to a map with routes.",
            "Access to route changes.",
            "Access to an enhanced emergency button."
        ),
        backgroundGradient = listOf(Color(0xFF42A5F5), Color(0xFF1E88E5)),
        icon = Res.drawable.book_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24 // Icono personalizado para estudiantes
    )
}

@Composable
fun QuicknessFamily() {
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
        icon = Res.drawable.family_restroom_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24 // Icono personalizado para familias
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
                    fontFamily = FontFamily(Font(Res.font.Poppins_Bold)),
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
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(
                            text = benefit,
                            fontFamily = FontFamily(Font(Res.font.Poppins_Medium)),
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}