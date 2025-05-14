package org.override.quickness.feature.home.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.override.quickness.feature.home.screen.HomeViewModel
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.ui.styles.TextStyleBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBar(
    title: String,
    showBackButton: Boolean = false,
    viewModel: HomeViewModel,
    onEvaClick: () -> Unit = {},
    onCameraClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                style = TextStyleBrush()
            )
        },
        actions = {
            Box(
                modifier = Modifier
                    .height(35.dp)
                    .width(80.dp)
                    .background(
                        color = colorScheme.onBackground,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center,
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize(),
                        content = {
                            IconButton(
                                onClick = { onCameraClick() },
                                modifier = Modifier.fillMaxWidth(.5f),
                                content = {
                                    Icon(
                                        painter = painterResource(
                                            viewModel.getDrawable(
                                                ResourceNameKey.PHOTO_CAMERA_24DP_E3E3E3_FILL0_WGHT400_GRAD0_OPSZ24.name
                                            )
                                        ),
                                        contentDescription = "Camera",
                                        tint = colorScheme.tertiary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            )
                            IconButton(
                                onClick = { onEvaClick() },
                                modifier = Modifier.fillMaxWidth(1f),
                                content = {
                                    Icon(
                                        painter = painterResource(
                                            viewModel.getDrawable(
                                                ResourceNameKey.EVA_LOGO.name
                                            )
                                        ),
                                        contentDescription = "Eva",
                                        tint = colorScheme.tertiary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            )
                        }
                    )
                }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = colorScheme.tertiary,
            navigationIconContentColor = colorScheme.tertiary,
            actionIconContentColor = colorScheme.tertiary,
        ),
        modifier = Modifier.background(Color.Transparent)
    )
}
