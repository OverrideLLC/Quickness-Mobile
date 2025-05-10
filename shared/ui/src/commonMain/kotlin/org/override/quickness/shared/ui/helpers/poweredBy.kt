package org.override.quickness.shared.ui.helpers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.override.quickness.shared.resources.strings.Strings
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun powered(
    logo: DrawableResource
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.wrapContentSize()
    ) {
        Text(
            text = Strings.GeneralAppStrings.POWERED,
            fontSize = 16.sp,
            fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
            color = Color.White
        )
        Image(
            painter = painterResource(logo),
            contentDescription = "Logo",
            modifier = Modifier.size(50.dp)
        )
        Text(
            text = Strings.GeneralAppStrings.OVERRIDE,
            fontSize = 16.sp,
            fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
            color = Color.White
        )
    }
}