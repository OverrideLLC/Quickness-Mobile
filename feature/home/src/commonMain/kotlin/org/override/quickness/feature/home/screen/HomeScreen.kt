package org.override.quickness.feature.home.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.feature.home.componets.BottomBar
import org.override.quickness.feature.home.componets.TopBar
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.ui.animations.BackgroundAnimated
import org.override.quickness.shared.utils.routes.RoutesHome
import org.override.quickness.shared.utils.routes.RoutesStart

@Composable
fun HomeScreen(
    navController: NavHostController,
    navControllerStart: NavController,
    navHome: @Composable (paddingValues: PaddingValues) -> Unit
) = Screen(
    viewModel = koinViewModel(),
    navController = navController,
    navHome = navHome,
    navControllerStart = navControllerStart,
)

@Composable
private fun Screen(
    viewModel: HomeViewModel,
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
    var isNavigationBarVisible by remember { mutableStateOf(false) }
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
        viewModel.checkPermissions(
            permissions = Permission.CAMERA,
            controller = controller
        )
    }

    Scaffold(
        topBar = {
            TopBar(
                title = topName,
                viewModel = viewModel,
                onCameraClick = {}
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier.fillMaxSize(),
                content = {
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 5.dp)
                            .padding(bottom = 10.dp),
                        contentAlignment = Alignment.BottomCenter,
                    ){
                        BottomBar(
                            navigationController = navController,
                            viewModel = viewModel,
                            topName = { topName = it }
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                var totalDragAmount = 0f
                val dragThreshold = 50f

                detectHorizontalDragGestures(
                    onDragStart = {
                        totalDragAmount = 0f
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        totalDragAmount += dragAmount

                        if (totalDragAmount > dragThreshold) {
                            // Acción para arrastre a la derecha
                            totalDragAmount = 0f  // Reinicia después de activar
                        } else if (totalDragAmount < -dragThreshold && !isNavigationBarVisible) {
                            isNavigationBarVisible = !isNavigationBarVisible
                            navControllerStart.navigate(RoutesStart.Eva.route)
                            totalDragAmount = 0f  // Reinicia después de activar
                        }
                        change.consume()
                    },
                )
            }
    )
}

//FloatingActionButton(
//                            containerColor = colorScheme.primaryContainer,
//                            contentColor = colorScheme.tertiary,
//                            shape = shapes.small,
//                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
//                            modifier = Modifier
//                                .size(50.dp)
//                                .weight(0.1f),
//                            onClick = { navControllerStart.navigate(RoutesStart.Camera.route) },
//                            content = {
//                                Icon(
//                                    painter = painterResource(viewModel.getDrawable(ResourceNameKey.PHOTO_CAMERA_24DP_E3E3E3_FILL0_WGHT400_GRAD0_OPSZ24.name)),
//                                    contentDescription = null,
//                                    modifier = Modifier.size(24.dp)
//                                )
//                            }
//                        )