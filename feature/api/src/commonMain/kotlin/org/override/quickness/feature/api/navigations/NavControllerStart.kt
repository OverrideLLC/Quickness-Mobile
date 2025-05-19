package org.override.quickness.feature.api.navigations

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.toIntRect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.feature.api.NavigationViewModel
import org.override.quickness.feature.eva.screen.EvaRoot
import org.override.quickness.feature.home.cam.analyzer.CamAnalyzerRoot
import org.override.quickness.feature.home.cam.scanner.CameraRoot
import org.override.quickness.feature.home.screen.HomeScreen
import org.override.quickness.feature.start.screen.StartScreen
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
                    route = RoutesStart.Eva.route,
                    deepLinks = listOf(navDeepLink { uriPattern = DeepLinksStart.Eva.deepLink }),
                    enterTransition = {
                        slideIn(
                            initialOffset = {
                                it.toIntRect().centerRight.copy(
                                    x = 1000,
                                    y = 0
                                )
                            },
                            animationSpec = tween(500)
                        )
                    },
                    exitTransition = {
                        slideOut(
                            targetOffset = {
                                it.toIntRect().centerRight.copy(
                                    x = 1000,
                                    y = 0
                                )
                            },
                            animationSpec = tween(500)
                        )
                    }
                ) {
                    EvaRoot(
                        onBackNavigate = { navControllerStart.popBackStack() }
                    )
                }

                composable(RoutesStart.CamAnalyzer.route) { CamAnalyzerRoot() }

                composable(
                    route = RoutesStart.Camera.route,
                    deepLinks = listOf(navDeepLink { uriPattern = DeepLinksStart.Camera.deepLink }),
                    enterTransition = {
                        slideIn(
                            initialOffset = {
                                it.toIntRect().centerRight.copy(
                                    x = 1000,
                                    y = 0
                                )
                            },
                            animationSpec = tween(500)
                        )
                    },
                    exitTransition = {
                        slideOut(
                            targetOffset = {
                                it.toIntRect().centerRight.copy(
                                    x = 1000,
                                    y = 0
                                )
                            },
                            animationSpec = tween(500)
                        )
                    }
                ) { CameraRoot(navController = navControllerStart) }

                composable(
                    route = RoutesStart.Start.route,
                    deepLinks = listOf(navDeepLink { uriPattern = DeepLinksStart.Start.deepLink })
                ) { StartScreen(navController = navControllerStart) }

                composable(
                    route = RoutesStart.Home.route,
                    deepLinks = listOf(navDeepLink { uriPattern = DeepLinksStart.Home.deepLink })
                ) { args ->
                    args.arguments?.getString("uid")?.let { uid -> viewModel.saveUid(uid) }
                    val navController = rememberNavController()
                    HomeScreen(
                        navController = navController,
                        navControllerStart = navControllerStart,
                    ) {
                        NavControllerHome(
                            navController = navController,
                            startDestination = RoutesHome.Qr.route,
                            paddingValues = it,
                            navControllerStart = navControllerStart
                        )
                    }
                }
            }
        }
    }
}