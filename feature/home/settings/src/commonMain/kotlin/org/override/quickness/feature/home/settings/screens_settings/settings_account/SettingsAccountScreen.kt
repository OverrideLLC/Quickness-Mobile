package org.override.quickness.feature.home.settings.screens_settings.settings_account

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.utils.routes.RoutesHome
import org.override.quickness.shared.utils.routes.RoutesStart

@Composable
fun SettingsAccountRoot(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: SettingsAccountViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsAccountScreen(
        state = state,
        paddingValues = paddingValues,
        onAction = viewModel::onAction,
        viewModel = viewModel,
        navController = navController
    )
}

@Composable
internal fun SettingsAccountScreen(
    state: SettingsAccountState,
    paddingValues: PaddingValues,
    viewModel: SettingsAccountViewModel,
    navController: NavController,
    onAction: (SettingsAccountAction) -> Unit,
) {
    Content(
        paddingValues = paddingValues,
        viewModel = viewModel,
        state = state,
        onAction = onAction,
        navController = navController
    )
}

@Composable
fun Content(
    paddingValues: PaddingValues,
    viewModel: SettingsAccountViewModel,
    state: SettingsAccountState,
    navController: NavController,
    onAction: (SettingsAccountAction) -> Unit,
) {
    LazyColumn(
        content = {
            item {
                DataInfo(viewModel, state, onAction)
                HorizontalDivider(
                    color = colorScheme.tertiary.copy(alpha = 0.7f),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .padding(horizontal = 5.dp)
                )
            }
            item {
                UidInfo(state)
                HorizontalDivider(
                    color = colorScheme.tertiary.copy(alpha = 0.7f),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .padding(horizontal = 5.dp)
                )
            }
            item {
                Logout(state, navController, onAction)
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(bottom = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

@Composable
internal fun DataInfo(
    viewModel: SettingsAccountViewModel,
    state: SettingsAccountState,
    onAction: (SettingsAccountAction) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(viewModel.getDrawable(ResourceNameKey.ADMIN_PANEL_SETTINGS_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)),
            contentDescription = "Security",
            modifier = Modifier
                .padding(top = 10.dp)
                .size(50.dp),
            tint = colorScheme.onSurface,
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.surfaceContainer
            ),
            modifier = Modifier
                .animateContentSize()
                .padding(10.dp)
                .fillMaxWidth(),
            onClick = { onAction(SettingsAccountAction.OnOpen) },
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Protecting Your Data at Quickness",
                    color = colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                )
                Text(
                    text = """
                    At Quickness, your security and privacy are our top priority. We want to inform you transparently about how we handle your data:
                    """.trimIndent(),
                    color = colorScheme.tertiary,
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                if (state.onOpen) {
                    Text(
                        text = """
                        · Secure Storage: Unlike other applications, Quickness does not store any personal or sensitive information directly on your device (locally).
                        
                        · Protected Servers: All your data is stored securely on our servers. We implement robust measures to protect this information against unauthorized access.
                        
                        · Unique Identifier: The application on your device only stores a unique identifier that allows us to recognize you and access your information stored securely on our servers. This identifier does not contain direct personal data.
                        
                    We are committed to protecting your information and continuously work to maintain the highest security standards.
                    """.trimIndent(),
                        color = colorScheme.tertiary,
                        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
internal fun UidInfo(state: SettingsAccountState) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainer
        ),
        modifier = Modifier
            .animateContentSize()
            .padding(10.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Unique Identifier",
                color = colorScheme.tertiary,
                fontWeight = FontWeight.Bold,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )
            Text(
                text = state.uid,
                color = colorScheme.tertiary,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )
        }
    }
}

@Composable
internal fun Logout(
    state: SettingsAccountState,
    navController: NavController,
    onAction: (SettingsAccountAction) -> Unit
) {
    Button(
        onClick = { onAction(SettingsAccountAction.OnOpenDialog) },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.error,
            contentColor = colorScheme.tertiary
        ),
        modifier = Modifier
            .padding(10.dp),
        content = {
            Text(
                text = "Logout",
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                fontWeight = FontWeight.Bold
            )
        }
    )
    if (state.dialogOpen) {
        Dialog(
            onDismissRequest = { onAction(SettingsAccountAction.OnCloseDialog) },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            content = {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.errorContainer
                    ),
                    modifier = Modifier.animateContentSize()
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Text(
                            text = "Are you sure you want to logout?",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = {
                                onAction(SettingsAccountAction.Logout)
                                navController.navigate(RoutesStart.Start.route){
                                    popUpTo(RoutesStart.Home.route){
                                        inclusive = true
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = colorScheme.error
                            ),
                            modifier = Modifier.padding(10.dp),
                            content = {
                                Text(
                                    text = "Logout",
                                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        )
                    }
                }
            }
        )
    }
}