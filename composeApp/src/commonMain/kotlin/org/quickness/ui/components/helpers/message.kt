package org.quickness.ui.components.helpers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.quickness.ui.theme.Success
import quickness.composeapp.generated.resources.Poppins_Medium
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.check_circle_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.error_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24

@Composable
fun Message(
    visibility: Boolean,
    message: String,
    isWarning: Boolean = true,
    actionPostDelayed: () -> Unit
) {
    AnimatedVisibility(
        visible = visibility,
        enter = slideInVertically(
            initialOffsetY = { -it }, // Entra desde arriba
            animationSpec = tween(durationMillis = 900)
        ) + scaleIn(
            initialScale = 0.5f // Escala pequeña como gota al entrar
        ) + fadeIn(), // Se desvanece mientras entra
        exit = slideOutVertically(
            targetOffsetY = { -it }, // Sale hacia arriba
            animationSpec = tween(durationMillis = 900)
        ) + scaleOut(
            targetScale = 0.5f // Se reduce en tamaño mientras sale
        ) + fadeOut() // Se desvanece mientras sale
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().size(150.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.error
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize().padding(10.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = message,
                        fontFamily = FontFamily(Font(Res.font.Poppins_Medium)),
                        color = colorScheme.tertiary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painterResource(Res.drawable.error_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24),
                        contentDescription = "Warning",
                        tint = colorScheme.tertiary,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
        LaunchedEffect(Unit) {
            delay(3000)
            actionPostDelayed()
        }
    }
}

@Composable
fun MessageSuccess(
    visibility: Boolean,
    message: String,
    actionPostDelayed: () -> Unit
) {
    AnimatedVisibility(
        visible = visibility,
        enter = slideInVertically(
            initialOffsetY = { -it }, // Entra desde arriba
            animationSpec = tween(durationMillis = 900)
        ) + scaleIn(
            initialScale = 0.5f // Escala pequeña como gota al entrar
        ) + fadeIn(), // Se desvanece mientras entra
        exit = slideOutVertically(
            targetOffsetY = { -it }, // Sale hacia arriba
            animationSpec = tween(durationMillis = 900)
        ) + scaleOut(
            targetScale = 0.5f // Se reduce en tamaño mientras sale
        ) + fadeOut() // Se desvanece mientras sale
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().size(150.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Success
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize().padding(10.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = message,
                        fontFamily = FontFamily(Font(Res.font.Poppins_Medium)),
                        color = colorScheme.tertiary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painterResource(Res.drawable.check_circle_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24),
                        contentDescription = "Success",
                        tint = colorScheme.tertiary,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
        LaunchedEffect(Unit) {
            delay(3000)
            actionPostDelayed()
        }
    }
}