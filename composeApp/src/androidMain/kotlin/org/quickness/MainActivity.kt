package org.quickness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setupSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            val uri = Uri(
                url = "https://override.com.mx/Terminos-y-CondicionesQuickness.html",
                context = this
            )
            val sharedPreference = SharedPreference(this)

            systemUiController.setSystemBarsColor(
                color = Color(0xFF1b1b1b),
                darkIcons = Color(0xFF1b1b1b).luminance() > 0.5f
            )

            App(
                uri,
                sharedPreference
            )
        }
    }

    private fun setupSplashScreen() {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { false } // La pantalla de inicio desaparece inmediatamente
    }
}