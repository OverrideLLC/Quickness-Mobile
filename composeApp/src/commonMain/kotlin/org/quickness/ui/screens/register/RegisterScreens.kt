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
import org.quickness.ui.components.SexField
import org.quickness.ui.components.TextFIelCustom
import org.quickness.ui.components.TextFieldCustomEmail
import org.quickness.ui.components.TextFieldCustomPassword
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
 * @param state The current state of the registration screen, represented by [RegisterViewModel.RegisterState].
 */
@Composable
fun EmailAndPassword(
    viewModel: RegisterViewModel,
    state: RegisterViewModel.RegisterState
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        TextFieldCustomEmail(
            value = state.email,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { viewModel.onEmailChange(it) },
            text = stringResource(Res.string.email),
            isError = state.isError
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldCustomPassword(
            value = state.password,
            text = stringResource(Res.string.password),
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { viewModel.onPasswordChange(it) },
            isPasswordVisible = state.isVisiblePassword,
            isError = state.isError,
            togglePasswordVisibility = { viewModel.onIsVisiblePasswordChange() }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldCustomPassword(
            value = state.confirmPassword,
            text = stringResource(Res.string.confirm_password),
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { viewModel.onConfirmPasswordChange(it) },
            isPasswordVisible = state.isVisibleConfirmPassword,
            isError = state.isError,
            togglePasswordVisibility = { viewModel.onIsVisibleConfirmPasswordChange() }
        )
    }
}

/**
 * Displays the personal information input fields for user registration.
 *
 * @param viewModel The [RegisterViewModel] used to handle UI state and interactions.
 * @param state The current state of the registration screen, represented by [RegisterViewModel.RegisterState].
 */
@Composable
fun InformationPersonal(
    viewModel: RegisterViewModel,
    state: RegisterViewModel.RegisterState
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
                onValueChange = { viewModel.onNameChange(it) },
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
                onValueChange = { viewModel.onCurpChange(it) },
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
                placeholder = "452 903 932",
                onValueChange = { viewModel.onPhoneNumberChange(it) },
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
                onDayChange = { viewModel.onDayChange(it) },
                onMonthChange = { viewModel.onMonthChange(it) },
                onYearChange = { viewModel.onYearChange(it) }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            DropdownField(
                label = "State",
                options = listOf(
                    "Aguascalientes", "Baja California", "Baja California Sur",
                    "Campeche", "Chiapas", "Chihuahua", "Coahuila", "Colima",
                    "Durango", "Guanajuato", "Guerrero", "Hidalgo", "Jalisco",
                    "Michoacán", "Morelos", "Nayarit", "Nuevo León", "Oaxaca",
                    "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosí",
                    "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala",
                    "Veracruz", "Yucatán", "Zacatecas", "Ciudad de México"
                ),
                selectedOption = state.selectedState,
                isError = state.isError,
                onOptionSelected = { viewModel.onStateSelected(it) }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            SexField(
                selectedOption = state.sex,
                isError = state.isError,
                onSexChange = { viewModel.onSexSelected(it) }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

/**
 * Displays the terms and conditions, privacy policy, and data analytics approvals.
 *
 * @param viewModel The [RegisterViewModel] used to handle UI state and interactions.
 * @param state The current state of the registration screen, represented by [RegisterViewModel.RegisterState].
 * @param uri The [Uri] used to link to additional information pages.
 */
@Composable
fun Approbation(
    viewModel: RegisterViewModel,
    state: RegisterViewModel.RegisterState,
    uri: Uri
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
                uri = uri,
                onCheckedChange = { viewModel.onTermsAndConditionsChange() }
            )
            Spacer(modifier = Modifier.height(20.dp))

            ItemWithLink(
                title = stringResource(Res.string.privacy_policy),
                description = stringResource(Res.string.privacy_policy_description),
                uri = uri,
                checked = state.privacyPolicy,
                onCheckedChange = { viewModel.onPrivacyPolicyChange() }
            )
            Spacer(modifier = Modifier.height(20.dp))

            ItemWithLink(
                title = stringResource(Res.string.data_analytics),
                description = stringResource(Res.string.data_analytics_description),
                checked = state.dataAnalytics,
                uri = uri,
                onCheckedChange = { viewModel.onDataAnalyticsChange() }
            )
        }
    }
}