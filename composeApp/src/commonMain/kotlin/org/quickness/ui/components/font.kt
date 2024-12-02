package org.quickness.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

val TitleStyle = TextStyle(
    fontSize = 50.sp,
    fontWeight = FontWeight.Normal,
    fontStyle = FontStyle.Normal,
    textAlign = TextAlign.Center,
    shadow = Shadow(
        color = Color(0xff42b2dc),
        offset = Offset(0.0f, 0.0f),
        blurRadius = 30f
    ),
)

val TitleStylePrimary = TextStyle(
    fontSize = 50.sp,
    fontWeight = FontWeight.Normal,
    fontStyle = FontStyle.Normal,
    textAlign = TextAlign.Center,
    shadow = Shadow(
        color = Color(0xff42b2dc),
        offset = Offset(0.0f, 0.0f),
        blurRadius = 30f
    ),
)