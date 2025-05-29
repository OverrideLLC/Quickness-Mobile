package org.override.quickness.feature.home.service.apollo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.feature.home.service.apollo.components.AnnouncementRow
import org.override.quickness.feature.home.service.apollo.components.CourseRow
import org.override.quickness.feature.home.service.apollo.components.WorkRow

@Composable
fun ApolloRoot(
    viewModel: ApolloViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ApolloScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
internal fun ApolloScreen(
    state: ApolloState,
    onAction: (ApolloAction) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            item {
                Text(
                    text = "Courses",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 16.dp),
                    fontSize = 20.sp,
                    color = colorScheme.onSurface
                )
                HorizontalDivider(
                    color = colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item {
                CourseRow(state = state, onAction = onAction)
            }
            state.selectedCourse?.let {
                item {
                    Text(
                        text = "Works",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontSize = 20.sp,
                        color = colorScheme.onSurface,
                        fontWeight = FontWeight.Normal
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                }
                item {
                    WorkRow(
                        courseData = it,
                        onAction = onAction
                    )
                }
                item {
                    Text(
                        text = "Announcements",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontSize = 20.sp,
                        color = colorScheme.onSurface,
                        fontWeight = FontWeight.Normal
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                }
                item {
                    AnnouncementRow(
                        announcements = state.selectedCourse.announcements
                    )
                }
            }
        }
    )
}