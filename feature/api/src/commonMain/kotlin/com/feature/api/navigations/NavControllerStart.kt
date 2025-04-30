package com.feature.api.navigations

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.feature.api.NavigationViewModel
import com.feature.home.screen.HomeScreen
import com.feature.login.screen.LoginScreen
import com.feature.register.screen.RegisterScreen
import com.feature.start.screen.StartScreen
import com.override.home.cam.CameraRoot
import com.quickness.shared.utils.routes.RoutesHome
import com.quickness.shared.utils.routes.RoutesStart
import com.shared.ui.components.component.Progress
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavControllerStart(
    navControllerStart: NavHostController,
    startDestination: String,
    viewModel: NavigationViewModel = koinViewModel()
) {
    val session = viewModel.session.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    Crossfade(
        targetState = isLoading && session == null,
        modifier = Modifier.fillMaxSize()
    ) {
        if (it) {
            Progress(true)
        } else {
            NavHost(
                navController = navControllerStart,
                modifier = Modifier.fillMaxSize(),
                startDestination = if (session!!) RoutesStart.Home.route else startDestination,
            ) {
                composable(RoutesStart.Start.route) {
                    StartScreen(
                        navController = navControllerStart,
                        contentAuth = { LoginScreen(navControllerStart) },
                        contentRegister = { RegisterScreen(navControllerStart) }
                    )
                }
                composable(RoutesStart.Home.route) {
                    val navController = rememberNavController()
                    HomeScreen(navController = navController, navControllerStart = navControllerStart) {
                        NavControllerHome(
                            navController = navController,
                            startDestination = RoutesHome.Qr.route,
                            paddingValues = it,
                        )
                    }
                }
                composable(RoutesStart.Camera.route) {
                    CameraRoot(navController = navControllerStart)
                }
            }
        }
    }
}