package org.override.quickness.feature.home.service.eva

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
) {
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
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
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
                            Chat()
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(0.8f)
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp)
                                    .padding(bottom = 10.dp),
                                content = {
                                    Text(
                                        text = "Hello, how can I help you?",
                                        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                        fontSize = 40.sp,
                                        textAlign = TextAlign.Center,
                                        style = TextStyleBrush(),
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            )
                        }
                    }
                )
                TextFieldAi(
                    state = state.textFieldState,
                    isError = state.isError,
                    modifier = Modifier.padding(horizontal = 10.dp).padding(bottom = 10.dp),
                    onValueChange = {
                        viewModel.onValueChange(it)
                    },
                    onClickServices = {},
                    onSubmitClick = {},
                    leadingIconResource = viewModel.getDrawable(ResourceNameKey.APPS_48DP_E3E3E3_FILL1_WGHT400_GRAD0_OPSZ48.name),
                    trailingIconResource = viewModel.getDrawable(ResourceNameKey.SEND_48DP_E3E3E3_FILL1_WGHT400_GRAD0_OPSZ48.name),
                    leadingIconContentDescription = "Icono de servicios",
                    trailingIconContentDescription = "Icono de enviar"
                )
            }
        }
    )
}

@Composable
internal fun Chat() {
    LazyColumn(
        modifier = Modifier
    ) {

    }
}