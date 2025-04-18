package com.feature.home.settings.screens_settings.settings_qr

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.feature.home.settings.components.DropdownSettings
import com.feature.home.settings.components.SettingsItemSwitch
import com.quickness.shared.utils.qr_options.ColorQrOptions
import com.quickness.shared.utils.qr_options.FormatQrOptions
import com.quickness.shared.utils.qr_options.RoundedQrOptions
import com.shared.resources.drawable.ResourceNameKey
import com.shared.resources.strings.Strings
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun QrScreenSettings(paddingValues: PaddingValues) = Screen(paddingValues = paddingValues)

@OptIn(KoinExperimentalAPI::class)
@Composable
private fun Screen(
    viewModel: QrSettingsViewModel = koinViewModel(),
    paddingValues: PaddingValues
) {
    val state = viewModel.state.collectAsState().value
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp).padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            SettingsItemSwitch(
                name = Strings.QrSettings.FORMAT_QR_SETTINGS,
                icon = viewModel.getDrawable(ResourceNameKey.QR_CODE_2_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name),
                description = Strings.QrSettings.IMPROVE_QR_READABILITY,
                active = state.format == FormatQrOptions.Low.option,
                isActive = { viewModel.toggleFormat() }
            )
            Spacer(Modifier.padding(10.dp))
        }
        item {
            DropdownSettings(
                name = Strings.QrSettings.COLOR_QR_SETTINGS,
                icon = viewModel.getDrawable(ResourceNameKey.PALETTE_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name),
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
                name = Strings.QrSettings.ROUNDED_QR_SETTINGS,
                icon = viewModel.getDrawable(ResourceNameKey.ROUNDED_CORNER_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name),
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