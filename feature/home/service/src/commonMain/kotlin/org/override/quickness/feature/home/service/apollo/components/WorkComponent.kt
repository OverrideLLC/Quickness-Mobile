package org.override.quickness.feature.home.service.apollo.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.override.quickness.feature.home.service.apollo.ApolloAction
import org.override.quickness.feature.home.service.apollo.utils.CourseData
import org.override.quickness.feature.home.service.apollo.utils.WorkData

@Composable
internal fun WorkComponent(
    work: WorkData
) {
    var selected by remember { mutableStateOf(false) }
    val animatedContentSize = animateSizeAsState(
        targetValue = if (selected) Size(170f, 250f) else Size(170f, 150f),
        label = "WorkComponent"
    )
    Card(
        onClick = { selected = !selected },
        modifier = Modifier.size(
            width = animatedContentSize.value.width.dp,
            height = animatedContentSize.value.height.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainer.copy(alpha = 0.7f),
            contentColor = colorScheme.onSurface,
        ),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize()
                    .padding(5.dp),
                verticalArrangement = if (selected) Arrangement.Top else Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    if (!selected) {
                        Text(
                            text = work.title,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium
                        )
                    } else {
                        Text(
                            text = work.title,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = work.description,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Thin,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            )
        }
    )
}

@Composable
internal fun WorkRow(
    courseData: CourseData,
    onAction: (ApolloAction) -> Unit,
) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        content = {
            items(courseData.works) {
                WorkComponent(
                    work = WorkData(
                        title = it.title,
                        description = it.description
                    )
                )
            }
        }
    )
}