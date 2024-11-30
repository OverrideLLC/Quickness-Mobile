package org.quickness.ui.screens.home.settings.screens.settings_qr

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.quickness.SharedPreference
import org.quickness.ui.components.SettingsItemSwitch
import org.quickness.utils.`object`.KeysCache.FORMAT_KEY
import org.quickness.utils.`object`.KeysCache.TOKENS_BITMAP_KEY
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.format_qr_settings
import quickness.composeapp.generated.resources.improve_qr_readability
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
    LazyColumn {
        item {
            SettingsItemSwitch(
                name = Res.string.format_qr_settings,
                icon = Res.drawable.qr_code_2_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                description = Res.string.improve_qr_readability,
                active = sharedPreference.getBoolean(FORMAT_KEY, true),
                isActive = {
                    viewModel.updateState { copy(format = !state.format) }
                    sharedPreference.setBitmap(TOKENS_BITMAP_KEY, mapOf())
                    if (state.format) {
                        sharedPreference.setBoolean(FORMAT_KEY, true)
                    } else {
                        sharedPreference.setBoolean(FORMAT_KEY, false)
                    }
                }
            )
        }
    }
}