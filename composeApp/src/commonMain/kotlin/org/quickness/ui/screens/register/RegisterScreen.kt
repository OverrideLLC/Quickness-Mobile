package org.quickness.ui.screens.register

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.quickness.Uri
import org.quickness.ui.components.Message
import org.quickness.ui.navegation.NavigationRegister
import org.quickness.utils.RoutesRegister
import org.quickness.utils.RoutesStart
import quickness.composeapp.generated.resources.Poppins_Light
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.arrow_back_ios_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.next
import quickness.composeapp.generated.resources.register

/**
 * Composable principal para la pantalla de registro.
 *
 * @param navController Controlador de navegación principal.
 */
@Composable
fun RegisterScreen(
    navController: NavController,
    uri: Uri
) = RegisterContent(navController, uri)

/**
 * Pantalla de registro que contiene el encabezado, contenido y barra inferior.
 *
 * @param navController Controlador de navegación principal.
 * @param viewModel ViewModel para gestionar el estado y las acciones de la pantalla.
 */
@OptIn(KoinExperimentalAPI::class)
@Composable
fun RegisterContent(
    navController: NavController,
    uri: Uri,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val navControllerRegister = rememberNavController()
    val currentBackStackEntry by navControllerRegister.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            RegisterHeader(
                currentRoute = currentRoute ?: RoutesRegister.EmailAndPassword.route,
                navControllerRegister = navControllerRegister,
                navController = navController
            )
        },
        content = { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                NavigationRegister(
                    navController = navControllerRegister,
                    viewModel = viewModel,
                    state = state,
                    uri = uri
                )
            }
        },
        bottomBar = {
            RegisterBottomBar(
                navController = navController,
                navControllerRegister = navControllerRegister,
                currentRoute = currentRoute ?: RoutesRegister.EmailAndPassword.route,
                viewModel = viewModel
            )
        }
    )
    Message(
        message = state.errorMessage,
        visibility = state.isError,
        actionPostDelayed = { viewModel.toggleError() }
    )
}

/**
 * Encabezado de la pantalla de registro con barra de progreso y título.
 *
 * @param currentRoute Ruta actual de la navegación interna.
 * @param navControllerRegister Controlador de navegación interno.
 */
@Composable
private fun RegisterHeader(
    currentRoute: String,
    navControllerRegister: NavController,
    navController: NavController
) {
    val animatedProgress by animateFloatAsState(
        targetValue = calculateProgress(currentRoute),
        animationSpec = tween(durationMillis = 500)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp)
        ) {
            IconButton(
                onClick = {
                    if (currentRoute != RoutesRegister.EmailAndPassword.route)
                        navControllerRegister.popBackStack()
                    else
                        navController.popBackStack()
                },
                colors = IconButtonDefaults.iconButtonColors(Color.Transparent)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.arrow_back_ios_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24),
                    contentDescription = "Atrás",
                    modifier = Modifier.size(50.dp),
                    tint = colorScheme.primary
                )
            }
            Text(
                text = stringResource(Res.string.register),
                fontSize = 50.sp,
                fontFamily = FontFamily(Font(resource = Res.font.Poppins_Light)),
                color = colorScheme.primary
            )
        }
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.fillMaxWidth(),
            color = colorScheme.primary,
            trackColor = colorScheme.tertiary,
        )
    }
}

/**
 * Control de navegación para avanzar entre las pantallas del flujo de registro.
 *
 * @param navController Controlador de navegación principal.
 * @param navControllerRegister Controlador de navegación interno.
 * @param currentRoute Ruta actual de la navegación interna.
 * @param viewModel ViewModel para manejar las acciones y validaciones.
 */
@Composable
private fun RegisterBottomBar(
    navController: NavController,
    navControllerRegister: NavController,
    currentRoute: String,
    viewModel: RegisterViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            shape = RectangleShape,
            onClick = {
                when (currentRoute) {
                    RoutesRegister.EmailAndPassword.route ->
                        if (viewModel.validateEmailAndPassword())
                            navControllerRegister.navigate(RoutesRegister.InformationPersonal.route)
                        else
                            viewModel.toggleError()

                    RoutesRegister.InformationPersonal.route ->
                        if (viewModel.validatePersonalInfo())
                            navControllerRegister.navigate(RoutesRegister.Approbation.route)
                        else
                            viewModel.toggleError()

                    RoutesRegister.Approbation.route ->
                        if (viewModel.isTermsAndConditionsChecked()) {
                            viewModel.register(
                                onSuccess = {
                                    navControllerRegister.popBackStack()
                                    navController.navigate(RoutesStart.Start.route)
                                },
                                onError = {
                                    viewModel.toggleError()
                                }
                            )
                        }
                }
            },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = colorScheme.primary
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(Res.string.next),
                    fontSize = 30.sp,
                    color = colorScheme.primary,
                    fontFamily = FontFamily(Font(resource = Res.font.Poppins_Light))
                )
            }
        }
    }
}

/**
 * Calcula el progreso de la barra de progreso según la ruta actual.
 *
 * @param route Ruta actual de la navegación interna.
 * @return Progreso como un valor flotante entre 0 y 1.
 */
@Composable
private fun calculateProgress(
    route: String?
): Float {
    return when (route) {
        RoutesRegister.EmailAndPassword.route -> 0f
        RoutesRegister.InformationPersonal.route -> 0.5f
        RoutesRegister.Approbation.route -> 1f
        else -> 0f
    }
}