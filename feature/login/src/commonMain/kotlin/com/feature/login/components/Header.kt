package com.feature.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.feature.login.screen.LoginViewModel
import com.shared.resources.drawable.ResourceNameKey
import com.shared.resources.strings.Strings
import com.shared.ui.components.component.LogoAndTitle

@Composable
internal fun Header(viewModel: LoginViewModel) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LogoAndTitle(
            title = Strings.Authentication.LOGIN,
            drawableResource = viewModel.getDrawable(ResourceNameKey.LOGOQUICKNESSQC.name)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}