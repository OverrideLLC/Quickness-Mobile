package org.override.quickness.shared.ui.fields

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.override.quickness.shared.ui.animations.BackgroundAnimated

@Composable
fun TextFieldAi(
    state: TextFieldState,
    isError: Boolean,
    modifier: Modifier = Modifier,
    leadingIconResource: DrawableResource,
    leadingIconContentDescription: String,
    trailingIconResource: DrawableResource,
    trailingIconContentDescription: String,
    cameraIconResource: DrawableResource,
    placeholder: String = "Ask something to EVA",
    buttonEnabled: Boolean = true,
    selectedImage: ImageBitmap? = null,
    onDone: () -> Unit = {},
    onSubmitClick: () -> Unit = {},
    onClickServices: () -> Unit = {},
    openCamera: () -> Unit,
    onValueChange: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val unfocusedContainerColorEffective = colorScheme.surfaceContainer
    val focusedContainerColorEffective = colorScheme.surfaceContainer
    val errorContainerColorEffective = colorScheme.errorContainer.copy(alpha = 0.7f)

    val errorPlaceholderColorEffective = colorScheme.errorContainer.copy(alpha = 0.7f)
    val focusedPlaceholderColorEffective = colorScheme.primaryContainer.copy(alpha = 0.7f)
    val unfocusedPlaceholderColorEffective = colorScheme.onSurface.copy(alpha = 0.7f)

    val focusedTextColorEffective = colorScheme.onSurface
    val unfocusedTextColorEffective = colorScheme.onSurface

    val currentContainerColor = when {
        isError -> errorContainerColorEffective
        isFocused -> focusedContainerColorEffective
        else -> unfocusedContainerColorEffective
    }

    val currentPlaceholderColor = when {
        isError -> errorPlaceholderColorEffective
        isFocused -> focusedPlaceholderColorEffective
        else -> unfocusedPlaceholderColorEffective
    }

    val currentTextColor = if (isFocused) focusedTextColorEffective else unfocusedTextColorEffective

    var exceedLimit by remember { mutableStateOf(false) }
    val overallShape = RoundedCornerShape(
        topStart = 10.dp,
        topEnd = 10.dp,
    )
    val iconButtonCornerRadius = 12.dp
    val iconButtonSize = 34.dp
    LaunchedEffect(state.text) {
        onValueChange(state.text.toString())
    }

    Box(
        modifier = modifier
            .imePadding()
            .defaultMinSize(minHeight = 56.dp)
            .background(currentContainerColor, overallShape)
            .clip(overallShape)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .heightIn(min = 56.dp),
        ) {
            selectedImage?.let {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorScheme.primaryContainer.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(iconButtonCornerRadius)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Card(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(8.dp),
                        content = {
                            Image(
                                bitmap = it,
                                contentDescription = "Imagen seleccionada",
                                modifier = Modifier.size(80.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .heightIn(min = 56.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BasicTextField(
                    state = state,
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = currentTextColor,
                        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    onKeyboardAction = {
                        KeyboardActions(
                            onDone = { onDone() },
                            onGo = { onDone() },
                            onNext = { onDone() },
                            onPrevious = { onDone() },
                            onSearch = { onDone() },
                            onSend = { onDone() }
                        )
                    },
                    interactionSource = interactionSource,
                    cursorBrush = BackgroundAnimated(
                        colorPrimary = colorScheme.primaryContainer,
                        colorSecondary = colorScheme.primary
                    ),
                    lineLimits = TextFieldLineLimits.MultiLine(
                        minHeightInLines = 1,
                        maxHeightInLines = 10
                    ),
                    decorator = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            if (state.text.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    fontSize = 14.sp,
                                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                    color = currentPlaceholderColor
                                )
                            }
                            innerTextField()
                        }
                    },
                    onTextLayout = { textLayoutResult ->
                        val currentLineCount = textLayoutResult()?.lineCount
                        val hasExceeded = (currentLineCount ?: 0) > 1
                        if (hasExceeded) exceedLimit = true
                    }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(
                            color = colorScheme.primaryContainer.copy(alpha = 0f),
                            shape = RoundedCornerShape(iconButtonCornerRadius)
                        ),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        IconButton(onClick = onClickServices) {
                            Icon(
                                painter = painterResource(leadingIconResource),
                                contentDescription = leadingIconContentDescription,
                                modifier = Modifier.size(24.dp),
                                tint = colorScheme.onSurface
                            )
                        }
                        IconButton(
                            onClick = { openCamera() },
                            modifier = Modifier,
                            content = {
                                Icon(
                                    painter = painterResource(cameraIconResource),
                                    contentDescription = "Icono de cámara",
                                    tint = colorScheme.onSurface,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(iconButtonSize)
                        .background(
                            color = colorScheme.primaryContainer.copy(alpha = 0f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    IconButton(
                        onClick = onSubmitClick,
                        enabled = buttonEnabled
                    ) {
                        Icon(
                            painter = painterResource(trailingIconResource),
                            contentDescription = trailingIconContentDescription,
                            modifier = Modifier.size(24.dp),
                            tint = colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}