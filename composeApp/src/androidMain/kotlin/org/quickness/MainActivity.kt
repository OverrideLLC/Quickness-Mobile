package org.quickness

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        setupSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            WindowCompat.setDecorFitsSystemWindows(window, false)
            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = false
            )
            systemUiController.setNavigationBarColor(
                color = Color.Transparent,
                darkIcons = false
            )
            App()
        }
    }

    private fun setupSplashScreen() {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { false }
    }
}