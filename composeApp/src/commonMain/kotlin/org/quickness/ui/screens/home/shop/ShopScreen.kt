package org.quickness.ui.screens.home.shop

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import quickness.composeapp.generated.resources.LogoQuicknessQC
import quickness.composeapp.generated.resources.Poppins_Bold
import quickness.composeapp.generated.resources.Poppins_Light
import quickness.composeapp.generated.resources.Poppins_Medium
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.book_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.family_restroom_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.logo_swiftid_centrado

@Composable
fun ShopScreen() = Screen()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheetPlus by remember { mutableStateOf(false) }
    var showBottomSheetStudent by remember { mutableStateOf(false) }
    var showBottomSheetFamily by remember { mutableStateOf(false) }
    var showBottomSheetShop by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Balance()
        Spacer(modifier = Modifier.padding(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Products(
                    text = "Quickness Plus",
                    icon = Res.drawable.LogoQuicknessQC,
                    containerColor = colorScheme.primary,
                    iconTint = colorScheme.tertiary,
                    colorText = colorScheme.tertiary,
                    onClick = {
                        scope.launch {
                            showBottomSheetPlus = true
                            sheetState.show()
                        }
                    }
                )
            }
            item {
                Products(
                    text = "Quickness Student",
                    icon = Res.drawable.book_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                    containerColor = colorScheme.primary,
                    iconTint = colorScheme.tertiary,
                    colorText = colorScheme.tertiary,
                    brushStartColor = Color(0xFF1E88E5),
                    onClick = {
                        scope.launch {
                            showBottomSheetStudent = true
                            sheetState.show()
                        }
                    }
                )
            }
            item {
                Products(
                    text = "Quickness Family",
                    icon = Res.drawable.family_restroom_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                    containerColor = colorScheme.primary,
                    iconTint = colorScheme.tertiary,
                    colorText = colorScheme.tertiary,
                    brushStartColor = Color(0xFF66BB6A),
                    onClick = {
                        scope.launch {
                            showBottomSheetFamily = true
                            sheetState.show()
                        }
                    }
                )
            }
        }
    }
    if (showBottomSheetPlus) {
        BottomSheet(
            sheetState = sheetState,
            colorBackground = Color(0xFFFFD700),
            onDismissRequest = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheetPlus = false
                    }
                }
            },
            content = {
                QuicknessPlus()
            }
        )
    }
    if (showBottomSheetStudent) {
        BottomSheet(
            sheetState = sheetState,
            colorBackground = Color(0xFF42A5F5),
            onDismissRequest = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheetStudent = false
                    }
                }
            },
            content = {
                QuicknessStudent()
            }
        )
    }
    if (showBottomSheetFamily) {
        BottomSheet(
            sheetState = sheetState,
            colorBackground = Color(0xFF66BB6A),
            onDismissRequest = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheetFamily = false
                    }
                }
            },
            content = {
                QuicknessFamily()
            }
        )
    }

    if (showBottomSheetShop) {
        BottomSheet(
            sheetState = sheetState,
            colorBackground = colorScheme.background,
            onDismissRequest = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheetShop = false
                    }
                }
            },
            content = {
                Text(text = "Bottom sheet content")
            }
        )
    }
}

@Composable
private fun QuicknessPlus() {
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
private fun QuicknessStudent() {
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
private fun QuicknessFamily() {
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
private fun QuicknessCard(
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheet(
    sheetState: SheetState,
    colorBackground: Color,
    content: @Composable () -> Unit,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        sheetState = sheetState,
        content = { content() },
        containerColor = colorBackground,
        dragHandle = {
            BottomSheetDefaults.DragHandle()
        },
    )
}

@Composable
private fun Balance() {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth().height(150.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colorScheme.primary,
                            colorScheme.background,
                            colorScheme.background,
                            colorScheme.onBackground,
                            colorScheme.background
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Credits 100",
                    fontFamily = FontFamily(Font(Res.font.Poppins_Medium)),
                    fontSize = 25.sp,
                    color = colorScheme.tertiary,
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Image(
                        painter = painterResource(Res.drawable.logo_swiftid_centrado),
                        modifier = Modifier.size(150.dp),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Products(
    containerColor: Color,
    iconTint: Color,
    text: String,
    colorText: Color,
    brushStartColor: Color = Color(0xFFb88f14),
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
