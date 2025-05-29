package org.override.quickness.feature.home.service.apollo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.override.quickness.feature.home.service.apollo.ApolloAction
import org.override.quickness.feature.home.service.apollo.ApolloState
import org.override.quickness.feature.home.service.apollo.utils.CourseData

@Composable
internal fun CourseComponent(
    course: CourseData,
    onCourseClick: () -> Unit,
) {
    Card(
        onClick = onCourseClick,
        modifier = Modifier.size(
                width = 170.dp,
                height = 150.dp
            ),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainer.copy(alpha = 0.7f),
            contentColor = colorScheme.onSurface,
        ),
        content = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(
                        text = course.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp
                    )
                    Text(
                        text = course.degree,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            )
        }
    )
}

@Composable
internal fun CourseRow(
    state: ApolloState,
    onAction: (ApolloAction) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        content = {
            items(state.courses.size) { index ->
                val course = state.courses[index]
                CourseComponent(
                    course = course,
                    onCourseClick = { onAction(ApolloAction.OnCourseClick(course)) }
                )
            }
        }
    )
}