package com.shared.ui.components.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shared.resources.Res
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LogoAndTitle(
    title: String,
    drawableResource: DrawableResource
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(drawableResource),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = title,
            fontSize = 50.sp,
            fontFamily = MaterialTheme.typography.titleSmall.fontFamily,
            fontWeight = FontWeight.Bold,
            color = colorScheme.primary
        )
    }
}
