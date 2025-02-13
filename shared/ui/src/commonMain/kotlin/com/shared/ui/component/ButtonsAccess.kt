package com.shared.ui.component

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ButtonAccess(
    resourceButton1: Pair<StringResource, DrawableResource>,
    resourceButton2: Pair<StringResource, DrawableResource>,
    font: FontFamily,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp))
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = { onLoginClick() },
                    modifier = Modifier.height(50.dp).fillMaxWidth().padding(horizontal = 20.dp),
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
                            text = stringResource(resourceButton1.first),
                            fontSize = 18.sp,
                            fontFamily = font,
                            color = colorScheme.background
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = painterResource(resourceButton1.second),
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
                        containerColor = colorScheme.background,
                        contentColor = colorScheme.tertiary,
                    )
                ) {
                    Text(
                        text = stringResource(resourceButton2.first),
                        fontSize = 18.sp,
                        fontFamily = font,
                    )
                }
            }
        }
    )
}