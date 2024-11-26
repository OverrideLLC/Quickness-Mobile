package org.quickness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.quickness.data.remote.FirebaseService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            val uri = Uri(
                url = "https://github.com/Quickness-student/logic_gates_book_KMM.git",
                context = this
            )

            systemUiController.setSystemBarsColor(
                color = Color(0xFF1b1b1b),
                darkIcons = Color(0xFF1b1b1b).luminance() > 0.5f
            )
            App(
                uri
            )
        }
    }
}