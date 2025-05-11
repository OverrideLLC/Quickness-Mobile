package org.override.quickness.feature.home.service.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.override.quickness.feature.home.service.screen.ServiceViewModel
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun WidgetService(
    image: ResourceNameKey,
    titleService: String,
    color: Long,
    viewModel: ServiceViewModel,
    onEvent: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                color = colorScheme.background.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.medium
            )
            .pointerInput(Unit) { detectTapGestures { onEvent() } },
        contentAlignment = Alignment.Center,
        content = {
            IconTitle(
                image = image,
                titleService = titleService,
                color = color,
                viewModel = viewModel
            )
        }
    )
}

@Composable
internal fun IconTitle(
    image: ResourceNameKey,
    titleService: String,
    viewModel: ServiceViewModel,
    color: Long
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(viewModel.getDrawable(image.name)),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = colorScheme.tertiary
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = titleService,
            style = MaterialTheme.typography.titleLarge,
            color = colorScheme.tertiary,
            fontSize = 24.sp
        )
    }
}