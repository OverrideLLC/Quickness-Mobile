package org.override.quickness.feature.home.service.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun MacrosItem(
    name: String,
    count: Int,
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(
                color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                shape = RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center,
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = colorScheme.tertiary,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "$count%",
                        color = colorScheme.tertiary,
                        fontSize = 14.sp
                    )
                }
            )
        }
    )
}