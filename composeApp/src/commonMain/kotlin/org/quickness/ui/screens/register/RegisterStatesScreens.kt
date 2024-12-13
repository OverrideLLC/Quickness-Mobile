package org.quickness.ui.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.quickness.Uri
import org.quickness.ui.components.AgeInputFields
import org.quickness.ui.components.DropdownField
import org.quickness.ui.components.ItemWithLink
import org.quickness.ui.components.TextFIelCustom
import org.quickness.ui.components.TextFieldCustomEmail
import org.quickness.ui.components.TextFieldCustomPassword
import org.quickness.utils.enums.MexicanState
import quickness.composeapp.generated.resources.Poppins_Light
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.badge_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.confirm_password
import quickness.composeapp.generated.resources.curp
import quickness.composeapp.generated.resources.data_analytics
import quickness.composeapp.generated.resources.data_analytics_description
import quickness.composeapp.generated.resources.email
import quickness.composeapp.generated.resources.name_complete
import quickness.composeapp.generated.resources.number_phone
import quickness.composeapp.generated.resources.password
import quickness.composeapp.generated.resources.person_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.phone_iphone_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.privacy_policy
import quickness.composeapp.generated.resources.privacy_policy_description
import quickness.composeapp.generated.resources.terms_and_conditions
import quickness.composeapp.generated.resources.terms_and_conditions_description

/**
 * Displays email and password input fields for user registration.
 *
 * @param viewModel The [RegisterViewModel] used to handle UI state and interactions.
 * @param state The current state of the registration screen, represented by [RegisterState].
 */
@Composable
fun EmailAndPassword(
    viewModel: RegisterViewModel,
    state: RegisterState
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        TextFieldCustomEmail(
            value = state.email,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { email -> viewModel.updateState { copy(email = email) } },
            text = stringResource(Res.string.email),
            isError = state.isError
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldCustomPassword(
            value = state.password,
            text = stringResource(Res.string.password),
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { password -> viewModel.updateState { copy(password = password) } },
            isPasswordVisible = state.isVisiblePassword,
            isError = state.isError,
            togglePasswordVisibility = { viewModel.updateState { copy(isVisiblePassword = isVisiblePassword.not()) } }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldCustomPassword(
            value = state.confirmPassword,
            text = stringResource(Res.string.confirm_password),
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { confirmPassword -> viewModel.updateState { copy(confirmPassword = confirmPassword) } },
            isPasswordVisible = state.isVisibleConfirmPassword,
            isError = state.isError,
            togglePasswordVisibility = { viewModel.updateState { copy(isVisibleConfirmPassword = isVisibleConfirmPassword.not()) } }
        )
    }
}

/**
 * Displays the personal information input fields for user registration.
 *
 * @param viewModel The [RegisterViewModel] used to handle UI state and interactions.
 * @param state The current state of the registration screen, represented by [RegisterState].
 */
@Composable
fun InformationPersonal(
    viewModel: RegisterViewModel,
    state: RegisterState
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            TextFIelCustom(
                value = state.name,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { name -> viewModel.updateState { copy(name = name) } },
                text = stringResource(Res.string.name_complete),
                placeholder = "surnames names",
                keyboardType = KeyboardType.Text,
                icon = Res.drawable.badge_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                isError = state.isError
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            TextFIelCustom(
                value = state.curp,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Text,
                placeholder = "##################",
                onValueChange = { curp -> viewModel.updateState { copy(curp = curp) } },
                icon = Res.drawable.person_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                text = stringResource(Res.string.curp),
                isError = state.isError
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            TextFIelCustom(
                value = state.phoneNumber,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Phone,
                placeholder = "### ### ####",
                onValueChange = { phoneNumber -> viewModel.updateState { copy(phoneNumber = phoneNumber) } },
                text = stringResource(Res.string.number_phone),
                icon = Res.drawable.phone_iphone_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                isError = state.isError
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Text(
                text = "Age",
                color = colorScheme.primary,
                fontFamily = FontFamily(Font(resource = Res.font.Poppins_Light)),
            )
            AgeInputFields(
                day = state.day,
                month = state.month,
                year = state.year,
                isError = state.isError,
                onDayChange = { day -> viewModel.updateState { copy(day = day) } },
                onMonthChange = { month -> viewModel.updateState { copy(month = month) } },
                onYearChange = { year -> viewModel.updateState { copy(year = year) } }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            DropdownField(
                label = "State",
                options = MexicanState.entries.map { it.displayName },
                selectedOption = state.selectedState,
                isError = state.isError,
                onOptionSelected = { selectedState -> viewModel.updateState { copy(selectedState = selectedState) } }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            DropdownField(
                selectedOption = state.sex,
                isError = state.isError,
                label = "Sex",
                options = listOf("Male", "Female"),
                exposedHeight = 100.dp,
                onOptionSelected = { sex -> viewModel.updateState { copy(sex = sex) } }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

/**
 * Displays the terms and conditions, privacy policy, and data analytics approvals.
 *
 * @param viewModel The [RegisterViewModel] used to handle UI state and interactions.
 * @param state The current state of the registration screen, represented by [RegisterState].
 * @param uri The [Uri] used to link to additional information pages.
 */
@Composable
fun Approbation(
    viewModel: RegisterViewModel,
    state: RegisterState,
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(16.dp)
    ) {
        item {
            ItemWithLink(
                title = stringResource(Res.string.terms_and_conditions),
                description = stringResource(Res.string.terms_and_conditions_description),
                checked = state.termsAndConditions,
                onCheckedChange = { viewModel.updateState { copy(termsAndConditions = termsAndConditions.not()) } }
            )
            Spacer(modifier = Modifier.height(20.dp))

            ItemWithLink(
                title = stringResource(Res.string.privacy_policy),
                description = stringResource(Res.string.privacy_policy_description),
                checked = state.privacyPolicy,
                onCheckedChange = { viewModel.updateState { copy(privacyPolicy = privacyPolicy.not()) } }
            )
            Spacer(modifier = Modifier.height(20.dp))

            ItemWithLink(
                title = stringResource(Res.string.data_analytics),
                description = stringResource(Res.string.data_analytics_description),
                checked = state.dataAnalytics,
                onCheckedChange = { viewModel.updateState { copy(dataAnalytics = dataAnalytics.not()) } }
            )
        }
    }
}