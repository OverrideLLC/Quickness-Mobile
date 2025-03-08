package org.quickness.ui.screens.home

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.quickness.shared.utils.routes.RoutesHome
import com.shared.resources.ResourceNameKey
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.quickness.ui.components.styles.TextStyleBrush
import org.quickness.ui.navegation.NavigationHome
import quickness.composeapp.generated.resources.Poppins_Medium
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.map_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.map_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.qr_code_2_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.settings_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.shopping_cart_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.shopping_cart_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.warning_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24

@Composable
fun HomeScreen(navController: NavHostController) =
    Screen(homeViewModel = koinViewModel(), navController = navController)

@Composable
private fun Screen(homeViewModel: HomeViewModel, navController: NavHostController) {
    var topName by remember { mutableStateOf(RoutesHome.Qr.route) }
    val animatedBrush = remember { Animatable(0f) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }
    val infiniteTransition = rememberInfiniteTransition()
    val color1 by infiniteTransition.animateColor(
        initialValue = colorScheme.background,
        targetValue = colorScheme.primaryContainer,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val color2 by infiniteTransition.animateColor(
        initialValue = colorScheme.primaryContainer,
        targetValue = colorScheme.background,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 6000,
                easing = LinearEasing,
                delayMillis = 1000
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
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
            permissions = Permission.COARSE_LOCATION,
            controller = controller
        )
        homeViewModel.checkPermissions(
            permissions = Permission.LOCATION,
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
                        brush = Brush.verticalGradient(
                            colors = listOf(color1, color2)
                        ),
                    )
            ) {
                NavigationHome(
                    navController = navController,
                    paddingValues = padding,
                )
            }
        },
        bottomBar = { BottomBar(navController) { topName = it } },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier.fillMaxSize(),
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    title: String,
    showBackButton: Boolean = false,
    viewModel: HomeViewModel,
    onBackClick: () -> Unit = {},
    onEmergencyClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontFamily = FontFamily(Font(Res.font.Poppins_Medium)),
                fontSize = 50.sp,
                textAlign = TextAlign.Start,
                style = TextStyleBrush()
            )
        },
        navigationIcon = {
            if (showBackButton) IconButton(
                onClick = onBackClick,
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            )
        },
        actions = {
            IconButton(
                onClick = onEmergencyClick,
                content = {
                    Icon(
                        painter = painterResource(viewModel.getDrawable(ResourceNameKey.WARNING_24DP_E8EAED_FILL1_WGHT400_GRAD0_OPSZ24.name)),
                        contentDescription = "Boton de enmergencia",
                        tint = colorScheme.error,
                        modifier = Modifier.size(40.dp)
                    )
                }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = colorScheme.tertiary,
            navigationIconContentColor = colorScheme.tertiary,
            actionIconContentColor = colorScheme.tertiary,
        ),
        modifier = Modifier.background(Color.Transparent)
    )
}

@Composable
private fun BottomBar(
    navigationController: NavController,
    topName: (String) -> Unit
) {
    var selected by remember { mutableStateOf(Res.drawable.qr_code_2_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24) }

    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.background,
                shape = RoundedCornerShape(40.dp)
            ),
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                content = {
                    BottomAppBarIcon(
                        iconRes = if (selected != Res.drawable.shopping_cart_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24) Res.drawable.shopping_cart_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24 else Res.drawable.shopping_cart_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                        isSelected = selected == Res.drawable.shopping_cart_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24 || selected == Res.drawable.shopping_cart_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                        onClick = {
                            selected =
                                Res.drawable.shopping_cart_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
                            topName(RoutesHome.Shop.route)
                            navigationController.navigate(RoutesHome.Shop.route) { popUpTo(0) }
                        }
                    )
                    BottomAppBarIcon(
                        iconRes = Res.drawable.qr_code_2_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                        isSelected = selected == Res.drawable.qr_code_2_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                        onClick = {
                            selected =
                                Res.drawable.qr_code_2_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
                            topName(RoutesHome.Qr.route)
                            navigationController.navigate(RoutesHome.Qr.route) { popUpTo(0) }
                        }
                    )
                    BottomAppBarIcon(
                        iconRes = if (selected != Res.drawable.map_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24) Res.drawable.map_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24 else Res.drawable.map_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                        isSelected = selected == Res.drawable.map_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24 || selected == Res.drawable.map_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                        onClick = {
                            selected =
                                Res.drawable.map_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
                            topName(RoutesHome.Service.route)
                            navigationController.navigate(RoutesHome.Service.route) { popUpTo(0) }
                        }
                    )
                    BottomAppBarIcon(
                        iconRes = if (selected != Res.drawable.settings_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24) Res.drawable.settings_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24 else Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                        isSelected = selected == Res.drawable.settings_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24 || Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24 == selected,
                        onClick = {
                            selected = Res.drawable.settings_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
                            topName(RoutesHome.Settings.route)
                            navigationController.navigate(RoutesHome.Settings.route) { popUpTo(0) }
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun BottomAppBarIcon(
    iconRes: DrawableResource,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val scale = remember { Animatable(1f) }
    LaunchedEffect(isSelected) {
        scale.animateTo(
            targetValue = if (isSelected) 1.4f else 1.2f,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
        )
    }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Transparent)
            .scale(scale.value),
        content = {
            IconButton(
                onClick = onClick,
                colors = IconButtonDefaults.iconButtonColors(
                ),
                content = {
                    Icon(
                        painter = painterResource(iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(90.dp),
                        tint = if (isSelected) colorScheme.primary else colorScheme.tertiary
                    )
                }
            )
        }
    )
}