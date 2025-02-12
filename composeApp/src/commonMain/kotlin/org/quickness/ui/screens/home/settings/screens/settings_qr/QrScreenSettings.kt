package org.quickness.ui.screens.home.settings.screens.settings_qr

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.quickness.options.qr.ColorQrOptions
import org.quickness.options.qr.FormatQrOptions
import org.quickness.options.qr.RoundedQrOptions
import org.quickness.ui.components.component.DropdownSettings
import org.quickness.ui.components.component.SettingsItemSwitch
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.color_qr_settings
import quickness.composeapp.generated.resources.format_qr_settings
import quickness.composeapp.generated.resources.improve_qr_readability
import quickness.composeapp.generated.resources.palette_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.qr_code_2_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.rounded_corner_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.rounded_qr_settings

@Composable
fun QrScreenSettings() = Screen()

@OptIn(KoinExperimentalAPI::class)
@Composable
private fun Screen(
    viewModel: QrSettingsViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            SettingsItemSwitch(
                name = Res.string.format_qr_settings,
                icon = Res.drawable.qr_code_2_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                description = Res.string.improve_qr_readability,
                active = state.format == FormatQrOptions.Low.option,
                isActive = { viewModel.toggleFormat() }
            )
            Spacer(Modifier.padding(10.dp))
        }
        item {
            DropdownSettings(
                name = Res.string.color_qr_settings,
                icon = Res.drawable.palette_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                options = ColorQrOptions.entries.map { it.option },
                exposedHeight = 400.dp,
                selectedOption = state.colorQr,
                onOptionSelected = {
                    ColorQrOptions.fromOption(it)
                        ?.let { color -> viewModel.toggleColor(color) }
                },
            )
            Spacer(Modifier.padding(10.dp))
        }
        item {
            DropdownSettings(
                name = Res.string.rounded_qr_settings,
                icon = Res.drawable.rounded_corner_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                options = RoundedQrOptions.entries.map { it.option },
                exposedHeight = 200.dp,
                selectedOption = state.rounded,
                onOptionSelected = {
                    RoundedQrOptions.fromOption(it)
                        ?.let { rounded -> viewModel.toggleRounded(rounded) }
                },
            )
        }
    }
}