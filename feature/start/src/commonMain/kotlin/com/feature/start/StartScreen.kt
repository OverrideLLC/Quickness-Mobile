package com.feature.start

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
import com.shared.ui.component.LogoAndTitle
import com.utils.routes.RoutesStart
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun StartScreen(
    navController: NavController,
    title: StringResource,
    recurseButton1: Pair<StringResource, DrawableResource>,
    recurseButton2: Pair<StringResource, DrawableResource>,
    fontFamily: FontFamily,
    icon: DrawableResource
) = Screen(
    icon = icon,
    navController = navController,
    title = title,
    recurseButton1 = recurseButton1,
    recurseButton2 = recurseButton2,
    fontFamily = fontFamily
)

@Composable
private fun Screen(
    navController: NavController,
    title: StringResource,
    recurseButton1: Pair<StringResource, DrawableResource>,
    recurseButton2: Pair<StringResource, DrawableResource>,
    icon: DrawableResource,
    fontFamily: FontFamily,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val color1 by infiniteTransition.animateColor(
        initialValue = colorScheme.background,
        targetValue = colorScheme.onBackground,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val color2 by infiniteTransition.animateColor(
        initialValue = colorScheme.onBackground,
        targetValue = colorScheme.primary,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 4000,
                easing = LinearEasing,
                delayMillis = 1000
            ),
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
        LogoAndTitle(title = stringResource(title), icon = icon, font = fontFamily)
        Spacer(modifier = Modifier.weight(1f))
        ButtonAccessStart(
            onLoginClick = { navController.navigate(RoutesStart.Login.route) },
            onRegisterClick = { navController.navigate(RoutesStart.Register.route) },
            recurseButton1 = recurseButton1,
            recurseButton2 = recurseButton2,
            fontFamily = fontFamily
        )
    }
}

@Composable
fun ButtonAccessStart(
    recurseButton1: Pair<StringResource, DrawableResource>,
    recurseButton2: Pair<StringResource, DrawableResource>,
    fontFamily: FontFamily,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
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
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.tertiary,
                        contentColor = colorScheme.background
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(
                            text = stringResource(recurseButton1.first),
                            fontSize = 18.sp,
                            fontFamily = fontFamily,
                            color = colorScheme.background
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = painterResource(recurseButton1.second),
                            contentDescription = "Login",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { onRegisterClick() },
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.background,
                        contentColor = colorScheme.tertiary,
                    )
                ) {
                    Text(
                        text = stringResource(recurseButton2.first),
                        fontSize = 18.sp,
                        fontFamily = fontFamily,
                    )
                }
            }
        }
    )
}