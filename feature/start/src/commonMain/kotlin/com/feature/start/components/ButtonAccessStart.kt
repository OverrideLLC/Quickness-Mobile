package com.feature.start.components

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.feature.start.screen.StartViewModel
import com.shared.resources.drawable.ResourceNameKey
import com.shared.resources.strings.Strings
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun ButtonAccessStart(
    viewModel: StartViewModel,
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
                            text = Strings.Authentication.LOGIN,
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorScheme.background
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = painterResource(viewModel.getDrawable(ResourceNameKey.START_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)),
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
                        text = Strings.Authentication.REGISTER,
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    )
}