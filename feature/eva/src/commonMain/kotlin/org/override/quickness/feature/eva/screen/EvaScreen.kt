package org.override.quickness.feature.eva.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.feature.eva.components.Content

@Composable
fun EvaRoot(
    viewModel: EvaViewModel = koinViewModel(),
    onBackNavigate: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EvaScreen(
        state = state,
        onAction = viewModel::onAction,
        onBackNavigate = { onBackNavigate() }
    )
}

@Composable
internal fun EvaScreen(
    state: EvaState,
    viewModel: EvaViewModel = koinViewModel(),
    onAction: (EvaAction) -> Unit,
    onBackNavigate: () -> Unit,
    initiallyVisible: Boolean = false,
    blockFadeInDurationMillis: Int = 500,
    typingSpeedPerCharMillis: Long = 75L,
    startDelayMillis: Long = 0L,
    text: String = "Hello, how can I help you?"
) {
    var displayedText by remember(text, initiallyVisible) {
        mutableStateOf(if (initiallyVisible) text else "")
    }
    val alpha = remember(text, initiallyVisible) {
        Animatable(if (initiallyVisible) 1f else 0f)
    }

    LaunchedEffect(key1 = text, key2 = initiallyVisible) {
        if (!initiallyVisible) {
            alpha.snapTo(0f)
            displayedText = ""
            delay(startDelayMillis)
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = blockFadeInDurationMillis,
                    easing = LinearEasing
                )
            )
            if (blockFadeInDurationMillis == 0 && alpha.value < 1f) alpha.snapTo(1f)

            text.forEachIndexed { index, _ ->
                displayedText = text.substring(0, index + 1)
                delay(typingSpeedPerCharMillis)
            }
        } else {
            alpha.snapTo(1f)
            displayedText = text
        }
    }

    Scaffold(
        modifier = Modifier,
        content = { padding ->
            Content(
                state = state,
                displayedText = displayedText,
                alpha = alpha,
                onAction = onAction,
                onBackNavigate = onBackNavigate,
            )
        }
    )
}
