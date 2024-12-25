package org.quickness.ui.components.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import quickness.composeapp.generated.resources.LogoQuicknessQC
import quickness.composeapp.generated.resources.Poppins_Light
import quickness.composeapp.generated.resources.Res

@Composable
fun LogoAndTitle(title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(Res.drawable.LogoQuicknessQC),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = title,
            fontSize = 50.sp,
            fontFamily = FontFamily(Font(resource = Res.font.Poppins_Light)),
            color = colorScheme.primary
        )
    }
}