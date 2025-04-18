package org.quickness.ui.components.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.quickness.plataform.Uri
import quickness.composeapp.generated.resources.Poppins_Bold
import quickness.composeapp.generated.resources.Poppins_Light
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.read_more

@Composable
fun ItemWithLink(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: () -> Unit
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
            fontFamily = FontFamily(Font(resource = Res.font.Poppins_Bold)),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            color = colorScheme.tertiary,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(resource = Res.font.Poppins_Light)),
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = {
                Uri("https://override.com.mx/Terminos-y-CondicionesQuickness.html#parrafo1").navigate()
            },
            content = {
                Text(
                    text = stringResource(Res.string.read_more),
                    color = colorScheme.primary,
                    fontFamily = FontFamily(Font(resource = Res.font.Poppins_Bold)),
                )
            }
        )
        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckedChange()
            }
        )
    }
}