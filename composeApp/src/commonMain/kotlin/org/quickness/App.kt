package org.quickness

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.feature.api.navigations.NavControllerStart
import com.quickness.shared.utils.routes.RoutesStart
import org.quickness.ui.theme.MaterialThemeApp

@Composable
fun App() {
    MaterialThemeApp(
        isDarkTheme = true,
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