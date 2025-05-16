package org.override.quickness.feature.eva.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.override.quickness.feature.eva.utils.EvaService

@Composable
fun ServiceSuggestionsBox(
    suggestions: List<EvaService>,
    onServiceSelected: (EvaService) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface( // Surface para darle un fondo y elevación si es necesario
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp), // Altura fija para la caja de sugerencias, ajusta según necesites
        shape = RoundedCornerShape(8.dp),
        color = colorScheme.surfaceContainer, // Un color de fondo distinguible
        tonalElevation = 4.dp,
        shadowElevation = 4.dp
    ) {
        LazyColumn(
            modifier = Modifier.padding(8.dp)
        ) {
            items(suggestions) { service ->
                ServiceSuggestionItem(
                    service = service,
                    onClick = { onServiceSelected(service) }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun ServiceSuggestionItem(
    service: EvaService,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(colorScheme.surfaceContainer, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Column {
            Text(
                text = service.name,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onSurface
            )
            if (service.description.isNotEmpty()) {
                Text(
                    text = service.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onSurface
                )
            }
        }
    }
}
