package org.override.quickness.feature.eva.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.override.quickness.feature.eva.screen.EvaState

@Composable
internal fun Chat(state: EvaState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {
        items(state.messages.size) { index ->
            val message = state.messages[index]
            MessageBubble(
                message = message,
                isLastMessage = index == state.messages.size - 1
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (state.isLoadingMessages) {
            item {
                LoadingBubble()
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
