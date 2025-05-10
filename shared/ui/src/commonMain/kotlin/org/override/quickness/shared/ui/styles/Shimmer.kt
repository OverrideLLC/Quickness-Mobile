package org.override.quickness.shared.ui.styles

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffset by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ),
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.background,
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.background,
            ),
            start = Offset(startOffset, 0f),
            end = Offset(startOffset + size.width.toFloat() * 2, size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}

@Composable
fun ShimmerItem(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.shimmerEffect().size(250.dp))
}

@Composable
fun ShimmerListVertical(
    modifier: Modifier,
    count: Int = 3,
    isLoading: Boolean,
    content: @Composable () -> Unit
) {
    Crossfade(
        targetState = isLoading,
        modifier = Modifier.fillMaxSize(),
        label = "ShimmerListVertical"
    ){
        if (!it) {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize().padding(10.dp).padding()
            ) {
                items(count) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shimmerEffect()
                            .height(60.dp)
                            .border(
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, Color.Transparent)
                            )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        } else {
            content()
        }
    }
}