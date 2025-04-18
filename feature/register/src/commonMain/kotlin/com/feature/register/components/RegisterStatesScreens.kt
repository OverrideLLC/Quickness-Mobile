package com.feature.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.feature.register.screen.RegisterViewModel
import com.feature.register.states.RegisterState
import com.quickness.shared.utils.enums.MexicanState
import com.shared.resources.drawable.ResourceNameKey
import com.shared.resources.strings.Strings
import com.shared.ui.components.component.ItemWithLink
import com.shared.ui.components.fields.AgeInputFields
import com.shared.ui.components.fields.DropdownField
import com.shared.ui.components.fields.TextFIelCustom
import com.shared.ui.components.fields.TextFieldCustomEmail
import com.shared.ui.components.fields.TextFieldCustomPassword

/**
 * Displays email and password input fields for user registration.
 *
 * @param viewModel The [org.quickness.ui.screens.register.RegisterViewModel] used to handle UI state and interactions.
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
            text = Strings.Authentication.EMAIL,
            isError = state.isError,
            icons = viewModel.getDrawable(ResourceNameKey.ALTERNATE_EMAIL_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldCustomPassword(
            value = state.password,
            text = Strings.Authentication.PASSWORD,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { password -> viewModel.updateState { copy(password = password) } },
            isPasswordVisible = state.isVisiblePassword,
            isError = state.isError,
            togglePasswordVisibility = { viewModel.updateState { copy(isVisiblePassword = isVisiblePassword.not()) } },
            icon = viewModel.getDrawable(ResourceNameKey.LOCK_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name),
            togglePasswordVisibilityIcon = {
                if (state.isVisiblePassword)
                    viewModel.getDrawable(ResourceNameKey.VISIBILITY_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)
                else
                    viewModel.getDrawable(ResourceNameKey.VISIBILITY_OFF_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldCustomPassword(
            value = state.confirmPassword,
            text = Strings.Authentication.CONFIRM_PASSWORD,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { confirmPassword -> viewModel.updateState { copy(confirmPassword = confirmPassword) } },
            isPasswordVisible = state.isVisibleConfirmPassword,
            isError = state.isError,
            togglePasswordVisibility = { viewModel.updateState { copy(isVisibleConfirmPassword = isVisibleConfirmPassword.not()) } },
            icon = viewModel.getDrawable(ResourceNameKey.LOCK_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name),
            togglePasswordVisibilityIcon = {
                if (state.isVisibleConfirmPassword)
                    viewModel.getDrawable(ResourceNameKey.VISIBILITY_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)
                else
                    viewModel.getDrawable(ResourceNameKey.VISIBILITY_OFF_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = """
                Password parameters:
                8 characters.
                1 special character.
                1 number character.
                1 uppercase character
            """.trimIndent(),
            color = colorScheme.primary,
            textAlign = TextAlign.Center,
            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
        )
        Spacer(modifier = Modifier.height(10.dp))
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
                text = Strings.Profile.NAME,
                placeholder = "Names",
                keyboardType = KeyboardType.Text,
                icon = viewModel.getDrawable(ResourceNameKey.PERSON_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name),
                isError = state.isError
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            TextFIelCustom(
                value = state.lastName,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { last -> viewModel.updateState { copy(lastName = last) } },
                text = Strings.PersonalInformation.LAST_NAME,
                placeholder = "Last names",
                keyboardType = KeyboardType.Text,
                icon = viewModel.getDrawable(ResourceNameKey.BADGE_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name),
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
                icon = viewModel.getDrawable(ResourceNameKey.PERSON_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name),
                text = Strings.PersonalInformation.CURP,
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
                text = Strings.PersonalInformation.NUMBER_PHONE,
                icon = viewModel.getDrawable(ResourceNameKey.PHONE_IPHONE_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name),
                isError = state.isError
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Text(
                text = "Age",
                color = colorScheme.primary,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
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
                title = Strings.TermsAndPolicies.TERMS_AND_CONDITIONS,
                description = Strings.TermsAndPolicies.TERMS_AND_CONDITIONS_DESCRIPTION,
                checked = state.termsAndConditions,
                onCheckedChange = { viewModel.updateState { copy(termsAndConditions = termsAndConditions.not()) } },
                uri = {

                }
            )
            Spacer(modifier = Modifier.height(20.dp))

            ItemWithLink(
                title = Strings.TermsAndPolicies.PRIVACY_POLICY,
                description = Strings.TermsAndPolicies.PRIVACY_POLICY_DESCRIPTION,
                checked = state.privacyPolicy,
                onCheckedChange = { viewModel.updateState { copy(privacyPolicy = privacyPolicy.not()) } },
                uri = {

                }
            )
            Spacer(modifier = Modifier.height(20.dp))

            ItemWithLink(
                title = Strings.TermsAndPolicies.DATA_ANALYTICS,
                description = Strings.TermsAndPolicies.DATA_ANALYTICS_DESCRIPTION,
                checked = state.dataAnalytics,
                onCheckedChange = { viewModel.updateState { copy(dataAnalytics = dataAnalytics.not()) } },
                uri = {

                }
            )
        }
    }
}