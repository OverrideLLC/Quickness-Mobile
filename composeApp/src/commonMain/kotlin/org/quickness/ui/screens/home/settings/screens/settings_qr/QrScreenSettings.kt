package org.quickness.ui.screens.home.settings.screens.settings_qr

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.quickness.SharedPreference
import org.quickness.ui.components.DropdownSettings
import org.quickness.ui.components.SettingsItemSwitch
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.color_qr_settings
import quickness.composeapp.generated.resources.format_qr_settings
import quickness.composeapp.generated.resources.improve_qr_readability
import quickness.composeapp.generated.resources.palette_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.qr_code_2_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24

@Composable
fun QrScreenSettings() = Screen()

@OptIn(KoinExperimentalAPI::class)
@Composable
private fun Screen(
    viewModel: QrSettingsViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    val sharedPreference = SharedPreference()

    if (state.isLoadings)
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            SettingsItemSwitch(
                name = Res.string.format_qr_settings,
                icon = Res.drawable.qr_code_2_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                description = Res.string.improve_qr_readability,
                active = state.format,
                isActive = { viewModel.toggleFormat() }
            )
            Spacer(Modifier.padding(10.dp))
        }
        item {
            DropdownSettings(
                name = Res.string.color_qr_settings,
                icon = Res.drawable.palette_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                options = listOf("Blue", "Black", "White"),
                exposedHeight = 200.dp,
                selectedOption = state.colorTag,
                onOptionSelected = {
                    when (it) {
                        "Blue" -> viewModel.toggleColor(
                            colorQr = Color(0xFF5ce1e6).toArgb(),
                            colorBackground = Color.Black.toArgb(),
                            colorTag = it
                        )

                        "Black" -> viewModel.toggleColor(
                            colorQr = Color.Black.toArgb(),
                            colorBackground = Color.White.toArgb(),
                            colorTag = it
                        )

                        "White" -> viewModel.toggleColor(
                            colorQr = Color.White.toArgb(),
                            colorBackground = Color.Black.toArgb(),
                            colorTag = it
                        )
                    }
                },
            )
        }
    }
}