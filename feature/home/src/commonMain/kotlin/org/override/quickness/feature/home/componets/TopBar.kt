package org.override.quickness.feature.home.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.override.quickness.feature.home.screen.HomeViewModel
import org.jetbrains.compose.resources.painterResource
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.ui.styles.TextStyleBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBar(
    title: String,
    showBackButton: Boolean = false,
    viewModel: HomeViewModel,
    onBackClick: () -> Unit = {},
    onCameraClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                fontSize = 40.sp,
                textAlign = TextAlign.Start,
                style = TextStyleBrush()
            )
        },
        navigationIcon = {
            if (showBackButton) IconButton(
                onClick = onBackClick,
                content = {
                    Icon(
                        painter = painterResource(viewModel.getDrawable(ResourceNameKey.ARROW_BACK_IOS_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)),
                        contentDescription = null
                    )
                }
            )
        },
        actions = {  },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = colorScheme.tertiary,
            navigationIconContentColor = colorScheme.tertiary,
            actionIconContentColor = colorScheme.tertiary,
        ),
        modifier = Modifier.background(Color.Transparent)
    )
}
