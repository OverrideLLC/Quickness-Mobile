package org.override.quickness.feature.home.service.apollo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.override.quickness.feature.home.service.apollo.utils.AnnouncementData

@Composable
internal fun AnnouncementComponent(
    announcementData: AnnouncementData,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.7f),
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.size(
            width = 180.dp,
            height = 200.dp
        ),
        content = {
            Column(
                modifier = Modifier.fillMaxSize().padding(5.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = announcementData.title,
                    fontSize = 17.sp,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = announcementData.description,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    )
}

@Composable
internal fun AnnouncementRow(
    announcements: List<AnnouncementData>,
){
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        items(announcements) { announcement ->
            AnnouncementComponent(
                announcementData = announcement
            )
        }
    }
}