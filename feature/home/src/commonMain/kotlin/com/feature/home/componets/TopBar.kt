package com.feature.home.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.feature.home.screen.HomeViewModel
import com.shared.resources.drawable.ResourceNameKey
import com.shared.ui.components.styles.TextStyleBrush
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBar(
    title: String,
    showBackButton: Boolean = false,
    viewModel: HomeViewModel,
    onBackClick: () -> Unit = {},
    onEmergencyClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                fontSize = 50.sp,
                textAlign = TextAlign.Start,
                style = TextStyleBrush()
            )
        },
        navigationIcon = {
            if (showBackButton) IconButton(
                onClick = onBackClick,
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            )
        },
        actions = {
            IconButton(
                onClick = onEmergencyClick,
                content = {
                    Icon(
                        painter = painterResource(viewModel.getDrawable(ResourceNameKey.WARNING_24DP_E8EAED_FILL1_WGHT400_GRAD0_OPSZ24.name)),
                        contentDescription = "Boton de enmergencia",
                        tint = colorScheme.error,
                        modifier = Modifier.size(40.dp)
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
