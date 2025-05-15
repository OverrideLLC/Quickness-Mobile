package org.override.quickness.feature.home.service.eva.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import org.override.quickness.feature.home.service.eva.EvaViewModel

@Composable
internal fun MessageBubble(
    message: EvaViewModel.Message,
    isLastMessage: Boolean
) {
    // Animation parameters
    val animatedAlpha = remember { Animatable(0f) }
    val animatedScale = remember { Animatable(0.8f) }

    // Animate the message appearance
    LaunchedEffect(message.id) {
        // Sequential animations
        animatedScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
        animatedAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(200)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(animatedAlpha.value)
            .scale(animatedScale.value)
            .padding(vertical = 4.dp),
        contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(
                    color = if (message.isUser)
                        colorScheme.primaryContainer
                    else
                        colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (message.isUser) 16.dp else 4.dp,
                        bottomEnd = if (message.isUser) 4.dp else 16.dp
                    )
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium,
                color = if (message.isUser)
                    colorScheme.onPrimaryContainer
                else
                    colorScheme.onSecondaryContainer
            )
        }
    }
}
