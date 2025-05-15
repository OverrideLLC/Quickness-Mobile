package org.override.quickness.feature.home.service.eva.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.feature.home.service.eva.EvaAction
import org.override.quickness.feature.home.service.eva.EvaState
import org.override.quickness.feature.home.service.eva.EvaViewModel
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.ui.fields.TextFieldAi
import org.override.quickness.shared.ui.styles.TextStyleBrush

@Composable
internal fun Content(
    state: EvaState,
    displayedText: String,
    alpha: Animatable<Float, *>,
    viewModel: EvaViewModel = koinViewModel(),
    onAction: (EvaAction) -> Unit,
    onBackNavigate: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = BackgroundAnimatedEva()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            AnimatedContent(
                targetState = state.chatActive,
                content = { chatActive ->
                    if (chatActive) {
                        Chat(state)
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp)
                                .padding(bottom = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Icon(
                                    painter = painterResource(
                                        viewModel.getDrawable(
                                            ResourceNameKey.EVA_LOGO.name
                                        )
                                    ),
                                    contentDescription = "Logo EVA",
                                    tint = colorScheme.primary,
                                    modifier = Modifier.size(100.dp)
                                )
                                Text(
                                    text = displayedText,
                                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                    fontSize = 40.sp,
                                    textAlign = TextAlign.Center,
                                    style = TextStyleBrush(),
                                    modifier = Modifier.alpha(alpha.value)
                                )
                            }
                        }
                        IconButton(
                            onClick = { onAction(EvaAction.OpenCamera) },
                            content = {
                                Icon(
                                    painter = painterResource(
                                        viewModel.getDrawable(ResourceNameKey.PHOTO_CAMERA_24DP_E3E3E3_FILL0_WGHT400_GRAD0_OPSZ24.name)
                                    ),
                                    contentDescription = "Icono de cámara",
                                    tint = colorScheme.primary,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        )
                    }
                }
            )
        }

        if (state.showServiceSuggestions && state.serviceSuggestions.isNotEmpty()) {
            ServiceSuggestionsBox(
                suggestions = state.serviceSuggestions,
                onServiceSelected = { service ->
                    onAction(EvaAction.SelectService(service))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )
        }

        TextFieldAi(
            state = state.textFieldState,
            isError = state.isError,
            modifier = Modifier,
            onValueChange = {
                onAction(EvaAction.UpdateTextFieldState(it))
            },
            onClickServices = {
            },
            onSubmitClick = {
                onAction(EvaAction.SendMessage)
            },
            buttonEnabled = state.textFieldState.text.isNotEmpty(),
            leadingIconResource = viewModel.getDrawable(ResourceNameKey.ADD_BOX_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name),
            trailingIconResource = viewModel.getDrawable(ResourceNameKey.SEND_48DP_E3E3E3_FILL1_WGHT400_GRAD0_OPSZ48.name),
            leadingIconContentDescription = "Icono de servicios",
            trailingIconContentDescription = "Icono de enviar"
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        contentAlignment = Alignment.TopStart,
    ) {
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
                        contentDescription = "Atrás",
                        tint = colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
            Text(
                text = "Eva",
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                fontSize = 30.sp,
                textAlign = TextAlign.Start,
                style = TextStyleBrush(),
            )
        }
    }
}