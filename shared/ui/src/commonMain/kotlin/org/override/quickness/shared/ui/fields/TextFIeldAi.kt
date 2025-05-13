package org.override.quickness.shared.ui.fields

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
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
    onDone: () -> Unit = {},
    leadingIconResource: DrawableResource,
    leadingIconContentDescription: String,
    trailingIconResource: DrawableResource,
    trailingIconContentDescription: String,
    placeholder: String = "Ask something to EVA",
    buttonEnabled: Boolean = true,
    onSubmitClick: () -> Unit = {},
    onClickServices: () -> Unit = {},
    onValueChange: (String) -> Unit // This will be called when state.text changes
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Define effective colors based on the original OutlinedTextField styling
    val unfocusedContainerColorEffective = colorScheme.onSurface.copy(alpha = 0f)
    val focusedContainerColorEffective = colorScheme.primaryContainer.copy(alpha = 0f)
    val errorContainerColorEffective = colorScheme.errorContainer.copy(alpha = 0.7f)

    val unfocusedIndicatorColorEffective = colorScheme.onSurface.copy(alpha = 0.7f)
    val focusedIndicatorColorEffective = colorScheme.primaryContainer.copy(alpha = 0.7f)
    val errorIndicatorColorEffective = colorScheme.errorContainer.copy(alpha = 0.7f)

    val errorPlaceholderColorEffective = colorScheme.errorContainer.copy(alpha = 0.7f)
    val focusedPlaceholderColorEffective = colorScheme.primaryContainer.copy(alpha = 0.7f)
    val unfocusedPlaceholderColorEffective = colorScheme.onSurface.copy(alpha = 0.7f)

    val focusedTextColorEffective = colorScheme.onSurface
    val unfocusedTextColorEffective = colorScheme.onSurface
    // ---

    val currentContainerColor = when {
        isError -> errorContainerColorEffective
        isFocused -> focusedContainerColorEffective
        else -> unfocusedContainerColorEffective
    }

    val currentIndicatorColor = when {
        isError -> errorIndicatorColorEffective
        isFocused -> focusedIndicatorColorEffective
        else -> unfocusedIndicatorColorEffective
    }

    val currentPlaceholderColor = when {
        isError -> errorPlaceholderColorEffective
        isFocused -> focusedPlaceholderColorEffective
        else -> unfocusedPlaceholderColorEffective
    }

    val currentTextColor = if (isFocused) focusedTextColorEffective else unfocusedTextColorEffective

    var exceedLimit by remember { mutableStateOf(false) }
    var shape = animateDpAsState(
        targetValue = if (exceedLimit) 12.dp else 120.dp,
        label = "Shape"
    )
    val overallShape = RoundedCornerShape(shape.value)
    val iconButtonCornerRadius = 12.dp
    val iconButtonSize = 34.dp

    // Call onValueChange when state.text changes
    LaunchedEffect(state.text) {
        onValueChange(state.text.toString())
    }

    Box(
        modifier = modifier // User-provided modifier (e.g., for external padding, fillMaxWidth)
            .imePadding()
            .defaultMinSize(minHeight = 56.dp) // Maintain a typical text field height
            .background(currentContainerColor, overallShape)
            .border(1.dp, currentIndicatorColor, overallShape)
            .clip(overallShape) // Clip contents like text cursor to the shape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp) // Padding inside the border, before icons
                .heightIn(min = 56.dp), // Ensure row itself respects min height for alignment
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Leading Icon
            Box(
                modifier = Modifier
                    .size(iconButtonSize)
                    .background(
                        color = colorScheme.primaryContainer.copy(alpha = 0f), // Transparent, shape for ripple
                        shape = RoundedCornerShape(iconButtonCornerRadius)
                    ),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onClickServices) {
                    Icon(
                        painter = painterResource(leadingIconResource),
                        contentDescription = leadingIconContentDescription,
                        modifier = Modifier.size(24.dp),
                        tint = colorScheme.onSurface // Or adapt tint based on state if needed
                    )
                }
            }

            Spacer(Modifier.width(8.dp)) // Space between leading icon and text field

            // BasicTextField and Placeholder
            BasicTextField(
                state = state,
                modifier = Modifier.weight(1f), // Take available space
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = currentTextColor,
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily // Ensure consistency
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                onKeyboardAction = {
                    KeyboardActions(
                        onDone = { onDone() }
                    )
                },
                interactionSource = interactionSource,
                cursorBrush = BackgroundAnimated(
                    colorPrimary = colorScheme.primaryContainer,
                    colorSecondary = colorScheme.primary
                ), // Standard cursor color
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
                        innerTextField() // Renders the actual text input
                    }
                },
                onTextLayout = { textLayoutResult ->
                    val currentLineCount = textLayoutResult()?.lineCount
                    val hasExceeded = (currentLineCount ?: 0) > 1
                    if (hasExceeded) exceedLimit = true
                }
            )

            Spacer(Modifier.width(8.dp)) // Space between text field and trailing icon

            // Trailing Icon
            Box(
                modifier = Modifier
                    .size(iconButtonSize)
                    .background(
                        color = colorScheme.primaryContainer.copy(alpha = 0f), // Transparent, shape for ripple
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onSubmitClick,
                    enabled = buttonEnabled
                ) {
                    Icon(
                        painter = painterResource(trailingIconResource),
                        contentDescription = trailingIconContentDescription,
                        modifier = Modifier.size(24.dp),
                        tint = colorScheme.onSurface // Or adapt tint
                    )
                }
            }
        }
    }
}