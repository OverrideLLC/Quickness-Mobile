package org.quickness.ui.screens.start

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.quickness.ui.components.LogoAndTitle
import org.quickness.utils.routes.RoutesStart
import quickness.composeapp.generated.resources.Poppins_Medium
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.app_name
import quickness.composeapp.generated.resources.login
import quickness.composeapp.generated.resources.register
import quickness.composeapp.generated.resources.start_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24

@Composable
fun StartScreen(navController: NavController) = Screen(navController)

@Composable
private fun Screen(navController: NavController) {
    val infiniteTransition = rememberInfiniteTransition()

    // Animación de colores oscuros
    val color1 by infiniteTransition.animateColor(
        initialValue = colorScheme.background, // Background oscuro
        targetValue = colorScheme.onBackground, // Turquesa oscuro (Primary)
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val color2 by infiniteTransition.animateColor(
        initialValue = colorScheme.onBackground, // Secundario oscuro
        targetValue = colorScheme.primary, // Verde oscuro (Success)
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing, delayMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(color1, color2),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LogoAndTitle(
            title = stringResource(Res.string.app_name)
        )
        Spacer(modifier = Modifier.weight(1f))
        ButtonAccessStart(
            onLoginClick = { navController.navigate(RoutesStart.Login.route) },
            onRegisterClick = { navController.navigate(RoutesStart.Register.route) }
        )
    }
}

@Composable
fun ButtonAccessStart(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .offset(y = 20.dp)
            .background(
                color = colorScheme.background.copy(alpha = 0.8f),
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.BottomCenter,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 50.dp)
            ) {
                Button(
                    onClick = { onLoginClick() },
                    modifier = Modifier.height(50.dp).fillMaxWidth().padding(horizontal = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.tertiary,
                        contentColor = colorScheme.secondary
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(
                            text = stringResource(Res.string.login),
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(resource = Res.font.Poppins_Medium)),
                            color = colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = painterResource(Res.drawable.start_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24),
                            contentDescription = "Login",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { onRegisterClick() },
                    modifier = Modifier.height(50.dp).fillMaxWidth().padding(horizontal = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.secondary,
                        contentColor = colorScheme.tertiary,
                    )
                ) {
                    Text(
                        text = stringResource(Res.string.register),
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(resource = Res.font.Poppins_Medium)),
                    )
                }
            }
        }
    )
}