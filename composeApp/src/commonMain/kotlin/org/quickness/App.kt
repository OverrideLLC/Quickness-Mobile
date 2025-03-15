package org.quickness

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.api.repository.DataStoreRepository
import com.feature.api.navigations.NavControllerStart
import com.quickness.shared.utils.objects.KeysCache.IS_DARK_THEME_KEY
import com.quickness.shared.utils.routes.RoutesStart
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.quickness.theme.MaterialThemeApp

@Composable
fun App(
    viewModel: AppViewModel = koinViewModel()
) {
    val isDarkTheme = viewModel.isDarkTheme.collectAsState().value
    MaterialThemeApp(
        isDarkTheme = isDarkTheme,
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                NavControllerStart(
                    startDestination = RoutesStart.Start.route,
                    navController = rememberNavController()
                )
            }
        }
    )
}