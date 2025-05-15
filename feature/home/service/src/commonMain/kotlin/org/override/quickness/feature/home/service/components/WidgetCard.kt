package org.override.quickness.feature.home.service.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun WidgetCard(
    name: String,
    icon: DrawableResource,
    onClick: () -> Unit
){
    Card(
        colors = CardDefaults.cardColors(
            contentColor = colorScheme.surface,
            containerColor = colorScheme.onSurface
        ),
        onClick = onClick,
        modifier = Modifier
            .size(150.dp),
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = name,
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 16.sp
                    )
                }
            )
        }
    )
}