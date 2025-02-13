package com.services.lyra

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.quickness.recurces.LogoQuicknessQC
import com.quickness.recurces.Res
import org.jetbrains.compose.resources.painterResource

@Composable
fun WidgetLyra() {
    NutritionWidget()
}

@Composable
fun CircularProgress(
    progress: Float, // Valor entre 0-1
    colors: List<Color>
) {
    Canvas(modifier = Modifier.size(100.dp)) {
        // Fondo del círculo
        drawCircle(
            color = Color.LightGray.copy(alpha = 0.3f),
            radius = size.minDimension / 2 - 10,
            style = Stroke(width = 12f)
        )

        // Gradiente angular
        val brush = Brush.sweepGradient(
            colors = colors,
            center = center
        )

        // Arco de progreso
        drawArc(
            brush = brush,
            startAngle = -90f,
            sweepAngle = 360 * progress,
            useCenter = false,
            style = Stroke(
                width = 12f,
                cap = StrokeCap.Round
            ),
            size = Size(size.width - 20, size.height - 20)
        )
    }
}

@Composable
fun GradientProgressBar(
    progress: Float, // Valor entre 0-1
    colors: List<Color>,
    height: Dp = 16.dp
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(height)
            .clip(RoundedCornerShape(50))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val barWidth = size.width * progress

            // Gradiente horizontal
            val brush = Brush.horizontalGradient(
                colors = colors,
                startX = 0f,
                endX = barWidth
            )

            drawRect(
                brush = brush,
                topLeft = Offset.Zero,
                size = Size(barWidth, size.height)
            )
        }
    }
}

@Composable
fun NutritionWidget() {
    Column(
        modifier = Modifier.height(300.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(Res.drawable.LogoQuicknessQC),
            contentDescription = "Icono",
            tint = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Barras de macros
            Column {
                MacroRow("Proteínas", 0.8f, listOf(Color(0xFF6E8EF6), Color(0xFFA777E3)))
                MacroRow("Carbohidratos", 0.6f, listOf(Color(0xFFFACD68), Color(0xFFF76B1C)))
                MacroRow("Grasas", 0.3f, listOf(Color(0xFF5DD4C3), Color(0xFF2EBAC1)))
            }
            // Círculo de progreso principal
            CircularProgress(
                progress = 0.75f,
                colors = listOf(Color(0xFF6C8EE3), Color(0xFF4AC6FF))
            )
        }
    }
}

@Composable
fun MacroRow(label: String, progress: Float, colors: List<Color>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = Color.White)
        Spacer(Modifier.height(4.dp))
        GradientProgressBar(
            progress = progress,
            colors = colors,
            height = 8.dp
        )
    }
}