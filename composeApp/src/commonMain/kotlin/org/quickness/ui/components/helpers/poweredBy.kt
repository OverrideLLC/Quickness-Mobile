package org.quickness.ui.components.helpers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import quickness.composeapp.generated.resources.Poppins_Medium
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.logo_swiftid_centrado
import quickness.composeapp.generated.resources.override
import quickness.composeapp.generated.resources.powered

@Composable
fun powered() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.wrapContentSize()
    ) {
        Text(
            text = stringResource(Res.string.powered),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(resource = Res.font.Poppins_Medium)),
            color = Color.White
        )
        Image(
            painter = painterResource(Res.drawable.logo_swiftid_centrado),
            contentDescription = "Logo",
            modifier = Modifier.size(50.dp)
        )
        Text(
            text = stringResource(Res.string.override),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(resource = Res.font.Poppins_Medium)),
            color = Color.White
        )
    }
}