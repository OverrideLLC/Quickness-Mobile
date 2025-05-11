package org.override.quickness.feature.api.navigations

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import org.override.quickness.feature.api.NavigationViewModel
import org.override.quickness.feature.home.screen.HomeScreen
import org.override.quickness.feature.login.screen.LoginScreen
import org.override.quickness.feature.register.screen.RegisterScreen
import org.override.quickness.feature.start.screen.StartScreen
import org.override.quickness.feature.home.cam.CameraRoot
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.shared.ui.component.Progress
import org.override.quickness.shared.utils.deeplinks.DeepLinksStart
import org.override.quickness.shared.utils.routes.RoutesHome
import org.override.quickness.shared.utils.routes.RoutesStart

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
                composable(
                    route = RoutesStart.Start.route,
                    deepLinks = listOf(navDeepLink { uriPattern = DeepLinksStart.Start.deepLink })
                ) {
                    StartScreen(
                        navController = navControllerStart,
                        contentAuth = { LoginScreen(navControllerStart) },
                        contentRegister = { RegisterScreen(navControllerStart) }
                    )
                }
                composable(
                    route = RoutesStart.Home.route,
                    deepLinks = listOf(navDeepLink { uriPattern = DeepLinksStart.Home.deepLink })
                ) {
                    val navController = rememberNavController()
                    HomeScreen(
                        navController = navController,
                        navControllerStart = navControllerStart
                    ) {
                        NavControllerHome(
                            navController = navController,
                            startDestination = RoutesHome.Qr.route,
                            paddingValues = it,
                        )
                    }
                }
                composable(
                    route = RoutesStart.Camera.route,
                    deepLinks = listOf(navDeepLink { uriPattern = DeepLinksStart.Camera.deepLink })
                ) {
                    CameraRoot(navController = navControllerStart)
                }
            }
        }
    }
}