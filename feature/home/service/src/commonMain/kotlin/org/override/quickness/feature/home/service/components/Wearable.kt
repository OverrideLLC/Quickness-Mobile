package org.override.quickness.feature.home.service.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter

@Composable
internal fun Wearable() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TrackerItem()
        TrackerItem(
            iconUrl = "https://cdn0.iconfinder.com/data/icons/octicons/1024/steps-1024.png",
            name = "Steps",
            value = "1,234"
        )
        TrackerItem(
            iconUrl = "https://cdn3.iconfinder.com/data/icons/fluent-regular-24p-vol-3/24/ic_fluent_drop_24_regular-1024.png",
            name = "Glucose",
            value = "180 mg/dL"
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TrackerItem(
            iconUrl = "https://cdn0.iconfinder.com/data/icons/slicons-solid-essentials/24/fire-1024.png",
            name = "Calories",
            value = "2,345"
        )
        TrackerItem(
            iconUrl = "https://cdn0.iconfinder.com/data/icons/typicons-2/24/weather-night-1024.png",
            name = "Sleeping",
            value = "83%"
        )
        TrackerItem(
            iconUrl = "https://cdn1.iconfinder.com/data/icons/iconoir-vol-3/24/oxygen-1024.png",
            name = "Oxygen",
            value = "98%"
        )
    }
}

@Composable
internal fun TrackerItem(
    iconUrl: String = "https://cdn3.iconfinder.com/data/icons/feather-5/24/heart-512.png",
    name: String = "Heart Rate",
    value: String = "85 bpm"
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        content = {
            Column(
                modifier = Modifier.size(80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = rememberAsyncImagePainter(iconUrl),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    )
}