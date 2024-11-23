package org.quickness.ui.components

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import quickness.composeapp.generated.resources.Poppins_Medium
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.login
import quickness.composeapp.generated.resources.register
import quickness.composeapp.generated.resources.start_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24

@Composable
fun ButtonAccess(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(color = colorScheme.onBackground, shape = RoundedCornerShape(20.dp))
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
                        containerColor = colorScheme.secondary,
                        contentColor = colorScheme.primary
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
                            color = colorScheme.primary
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
                        contentColor = colorScheme.primary
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