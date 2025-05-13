package org.override.quickness.feature.home.service.eva

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.ui.fields.TextFieldAi
import org.override.quickness.shared.ui.styles.TextStyleBrush

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
    blockFadeInDurationMillis: Int = 500, // Duración para el difuminado del bloque
    typingSpeedPerCharMillis: Long = 75L,  // Velocidad de "tipeo"
    startDelayMillis: Long = 0L,
    text: String = "Hello, how can I help you?"
) {
    var displayedText by remember(text, initiallyVisible) {
        mutableStateOf(if (initiallyVisible) text else "")
    }
    // Controla el alfa (opacidad) general del bloque de texto.
    val alpha = remember(text, initiallyVisible) {
        Animatable(if (initiallyVisible) 1f else 0f)
    }
    var isNavigationBarVisible by remember { mutableStateOf(false) }

    // LaunchedEffect se usa para iniciar la animación cuando el Composable entra en la composición
    // o cuando cambian las claves 'text' o 'initiallyVisible'.
    LaunchedEffect(key1 = text, key2 = initiallyVisible) {
        if (!initiallyVisible) {
            // Asegura que el estado se reinicie correctamente para la animación
            alpha.snapTo(0f) // Comienza completamente transparente
            displayedText = ""   // Comienza sin texto visible

            // Aplica el retraso inicial
            delay(startDelayMillis)

            // 1. Anima el alfa general del bloque de texto para el efecto de difuminado
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = blockFadeInDurationMillis,
                    easing = LinearEasing
                )
            )

            // 2. Anima la aparición de cada letra
            // Asegura que el alfa sea 1 si la duración del difuminado del bloque es 0
            if (blockFadeInDurationMillis == 0 && alpha.value < 1f) {
                alpha.snapTo(1f)
            }

            text.forEachIndexed { index, _ ->
                displayedText = text.substring(0, index + 1)
                delay(typingSpeedPerCharMillis)
            }
        } else {
            // Si debe ser visible inicialmente, establece el texto completo y el alfa a 1.
            alpha.snapTo(1f)
            displayedText = text
        }
    }

    val infiniteTransition = rememberInfiniteTransition()
    val color1 by infiniteTransition.animateColor(
        initialValue = colorScheme.primaryContainer,
        targetValue = colorScheme.background,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val color2 by infiniteTransition.animateColor(
        initialValue = colorScheme.background,
        targetValue = colorScheme.background,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5000,
                easing = LinearEasing,
                delayMillis = 1000
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    Scaffold(
        modifier = Modifier
            .pointerInput(Unit) {
                var totalDragAmount = 0f
                val dragThreshold = 50f

                detectHorizontalDragGestures(
                    onDragStart = {
                        totalDragAmount = 0f
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        totalDragAmount += dragAmount

                        if (totalDragAmount > dragThreshold && !isNavigationBarVisible) {
                            isNavigationBarVisible = !isNavigationBarVisible
                            onBackNavigate()
                            totalDragAmount = 0f
                        } else if (totalDragAmount < -dragThreshold) {
                            totalDragAmount = 0f
                        }
                        change.consume()
                    },
                    // ...
                )
            },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                color1,
                                color2,
                                color2,
                            )
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                AnimatedContent(
                    modifier = Modifier,
                    targetState = state.chatActive,
                    content = { chatActive ->
                        if (chatActive) {
                            Chat(state)
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(0.8f)
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp)
                                    .padding(bottom = 10.dp),
                                content = {
                                    Text(
                                        text = displayedText,
                                        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                        fontSize = 40.sp,
                                        textAlign = TextAlign.Center,
                                        style = TextStyleBrush(),
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .alpha(alpha.value)
                                    )
                                }
                            )
                        }
                    }
                )
                TextFieldAi(
                    state = state.textFieldState,
                    isError = state.isError,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(bottom = 10.dp),
                    onValueChange = {
                        viewModel.onValueChange(it)
                    },
                    onClickServices = {},
                    onSubmitClick = {
                        onAction(EvaAction.SendMessage)
                    },
                    buttonEnabled = state.textFieldState.text.isNotEmpty(),
                    leadingIconResource = viewModel.getDrawable(ResourceNameKey.APPS_48DP_E3E3E3_FILL1_WGHT400_GRAD0_OPSZ48.name),
                    trailingIconResource = viewModel.getDrawable(ResourceNameKey.SEND_48DP_E3E3E3_FILL1_WGHT400_GRAD0_OPSZ48.name),
                    leadingIconContentDescription = "Icono de servicios",
                    trailingIconContentDescription = "Icono de enviar"
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(padding),
                contentAlignment = Alignment.TopStart,
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth().background(Color.Transparent)
                    ) {
                        IconButton(
                            onClick = { onBackNavigate() },
                            content = {
                                Icon(
                                    painter = painterResource(viewModel.getDrawable(ResourceNameKey.ARROW_BACK_IOS_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)),
                                    contentDescription = null,
                                    tint = colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        )
                        Text(
                            text = "Eva",
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                            fontSize = 40.sp,
                            textAlign = TextAlign.Start,
                            style = TextStyleBrush(),
                        )
                    }
                }
            )
        }
    )
}

@Composable
internal fun Chat(state: EvaState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {
        items(state.messages.size) { index ->
            val message = state.messages[index]
            MessageBubble(
                message = message,
                isLastMessage = index == state.messages.size - 1
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Show loading animation for AI message if needed
        if (state.isLoadingMessages) {
            item {
                LoadingBubble()
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun MessageBubble(
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

@Composable
private fun LoadingBubble() {
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