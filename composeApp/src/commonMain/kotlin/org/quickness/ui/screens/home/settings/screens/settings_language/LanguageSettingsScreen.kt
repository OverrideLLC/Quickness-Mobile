package org.quickness.ui.screens.home.settings.screens.settings_language

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import org.quickness.ui.components.component.DropdownSettings
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.language_english
import quickness.composeapp.generated.resources.language_french
import quickness.composeapp.generated.resources.language_german
import quickness.composeapp.generated.resources.language_spanish
import quickness.composeapp.generated.resources.select_language
import quickness.composeapp.generated.resources.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24

// Language Settings
@Composable
fun LanguageSettingsScreen() = Screen()

@Composable
fun Screen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            DropdownSettings(
                name = Res.string.select_language,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                options = listOf(
                    stringResource(Res.string.language_english),
                    stringResource(Res.string.language_spanish),
                    stringResource(Res.string.language_french),
                    stringResource(Res.string.language_german)
                ),
                selectedOption = stringResource(Res.string.language_english),
                onOptionSelected = {}
            )
        }
    }
}