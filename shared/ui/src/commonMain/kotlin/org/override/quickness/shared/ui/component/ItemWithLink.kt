package org.override.quickness.shared.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.override.quickness.shared.resources.strings.Strings

@Composable
fun ItemWithLink(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: () -> Unit,
    uri: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorScheme.onBackground, shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = title,
            color = colorScheme.primary,
            textAlign = TextAlign.Center,
            fontFamily = MaterialTheme.typography.titleLarge.fontFamily
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            color = colorScheme.tertiary,
            textAlign = TextAlign.Center,
            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = {
                uri("https://override.com.mx/Terminos-y-CondicionesQuickness.html#parrafo1")
            },
            content = {
                Text(
                    text = Strings.TermsAndPolicies.READ_MORE,
                    color = colorScheme.primary,
                    fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                )
            }
        )
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange() }
        )
    }
}