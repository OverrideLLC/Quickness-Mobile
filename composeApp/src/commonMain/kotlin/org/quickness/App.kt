package org.quickness

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.feature.navControllers.NavControllerStart
import com.quickness.shared.utils.routes.RoutesStart
import org.quickness.ui.navegation.NavigationStart
import org.quickness.ui.theme.MaterialThemeApp

@Composable
fun App() {
    MaterialThemeApp(
        isDarkTheme = true,
        content = {
            Surface(modifier = Modifier.fillMaxSize()) {

                NavControllerStart(
                    startDestination = RoutesStart.Start.route,
                    navController = rememberNavController()
                )
            }
        }
    )
}