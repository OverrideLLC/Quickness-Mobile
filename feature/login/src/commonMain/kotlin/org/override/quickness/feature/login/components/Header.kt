package org.override.quickness.feature.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.override.quickness.feature.login.screen.LoginViewModel
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.resources.strings.Strings
import org.override.quickness.shared.ui.component.LogoAndTitle

@Composable
internal fun Header(viewModel: LoginViewModel) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LogoAndTitle(
            title = Strings.Authentication.LOGIN,
            drawableResource = viewModel.getDrawable(ResourceNameKey.LOGOQUICKNESSQC.name)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}