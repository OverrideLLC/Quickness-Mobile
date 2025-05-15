package org.override.quickness.feature.home.service.eva.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
internal fun LoadingBubble() {
    val infiniteTransition = rememberInfiniteTransition()
    val dotsAlpha = List(3) { index ->
        infiniteTransition.animateFloat(
            initialValue = 0.2f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(500),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(index * 150)
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .widthIn(max = 100.dp)
                .background(
                    color = colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 16.dp
                    )
                )
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            dotsAlpha.forEach { animatedAlpha ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .alpha(animatedAlpha.value)
                        .background(
                            color = colorScheme.onSecondaryContainer,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}