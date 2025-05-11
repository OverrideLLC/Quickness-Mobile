package org.override.quickness.feature.home.shop.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.override.quickness.feature.home.shop.screen.ShopViewModel
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.resources.strings.Strings
import org.jetbrains.compose.resources.painterResource
import org.override.quickness.shared.ui.animations.BackgroundAnimated

@Composable
fun Balance(credits: String, viewModel: ShopViewModel) {
    val rotationX = remember { Animatable(0f) }
    val rotationY = remember { Animatable(0f) }
    val density = LocalDensity.current.density

    LaunchedEffect(Unit) {
        while (true) {
            rotationX.animateTo(
                targetValue = 10f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            rotationY.animateTo(
                targetValue = -10f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
    }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .graphicsLayer(
                rotationX = rotationX.value,
                rotationY = rotationY.value,
                cameraDistance = 12f * density,
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = BackgroundAnimated(
                        colorPrimary = colorScheme.onPrimary,
                        colorSecondary = colorScheme.background
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
                    text = "${Strings.GeneralAppStrings.CREDIT} $credits",
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    fontSize = 25.sp,
                    color = colorScheme.tertiary,
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        painter = painterResource(viewModel.getDrawable(ResourceNameKey.LOGO_SWIFTID_CENTRADO.name)),
                        modifier = Modifier.size(150.dp),
                        tint = colorScheme.tertiary,
                        contentDescription = "Logo Override"
                    )
                }
            }
        }
    }
}
