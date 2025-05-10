package org.override.quickness.feature.home.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import org.override.quickness.feature.home.componets.BottomBar
import org.override.quickness.feature.home.componets.TopBar
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.ui.animations.BackgroundAnimated
import org.override.quickness.shared.utils.routes.RoutesHome
import org.override.quickness.shared.utils.routes.RoutesStart

@Composable
fun HomeScreen(
    navController: NavHostController,
    navControllerStart: NavController,
    navHome: @Composable (paddingValues: PaddingValues) -> Unit
) =
    Screen(
        homeViewModel = koinViewModel(),
        navController = navController,
        navHome = navHome,
        navControllerStart = navControllerStart
    )

@Composable
private fun Screen(
    homeViewModel: HomeViewModel,
    navController: NavHostController,
    navControllerStart: NavController,
    navHome: @Composable (paddingValues: PaddingValues) -> Unit
) {
    var topName by remember { mutableStateOf(RoutesHome.Qr.route) }
    val animatedBrush = remember { Animatable(0f) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)
    LaunchedEffect(Unit) {
        while (true) {
            animatedBrush.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 5000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
    }

    scope.launch {
        homeViewModel.checkPermissions(
            permissions = Permission.CAMERA,
            controller = controller
        )
    }

    Scaffold(
        topBar = {
            TopBar(
                title = topName,
                viewModel = homeViewModel,
                onEmergencyClick = {}
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = BackgroundAnimated(
                            colorPrimary = colorScheme.primaryContainer,
                            colorSecondary = colorScheme.background
                        )
                    ),
                content = {
                    navHome(padding)
                }
            )
        },
        bottomBar = {
            BottomBar(
                navigationController = navController,
                viewModel = homeViewModel,
                topName = { topName = it }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = colorScheme.primaryContainer,
                contentColor = colorScheme.tertiary,
                shape = shapes.small,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                modifier = Modifier.size(70.dp),
                onClick = { navControllerStart.navigate(RoutesStart.Camera.route) },
                content = {
                    Icon(
                        painter = painterResource(homeViewModel.getDrawable(ResourceNameKey.QR_CODE_2_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier.fillMaxSize(),
    )
}