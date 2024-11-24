package org.quickness.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.quickness.ui.components.AgeInputFields
import org.quickness.ui.components.SexField
import org.quickness.ui.components.StateDropdown
import org.quickness.ui.components.TextFIelCustom
import org.quickness.ui.components.TextFieldCustomEmail
import org.quickness.ui.components.TextFieldCustomPassword
import quickness.composeapp.generated.resources.Poppins_Bold
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
import quickness.composeapp.generated.resources.read_more
import quickness.composeapp.generated.resources.terms_and_conditions
import quickness.composeapp.generated.resources.terms_and_conditions_description

@Composable
fun EmailAndPassword(viewModel: RegisterViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        TextFieldCustomEmail(
            value = viewModel.state.collectAsState().value.email,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { viewModel.onEmailChange(it) },
            text = stringResource(Res.string.email),
            isError = viewModel.state.collectAsState().value.isError
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldCustomPassword(
            value = viewModel.state.collectAsState().value.password,
            text = stringResource(Res.string.password),
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { viewModel.onPasswordChange(it) },
            isPasswordVisible = viewModel.state.collectAsState().value.isVisiblePassword,
            isError = viewModel.state.collectAsState().value.isError,
            togglePasswordVisibility = { viewModel.onIsVisiblePasswordChange() }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldCustomPassword(
            value = viewModel.state.collectAsState().value.confirmPassword,
            text = stringResource(Res.string.confirm_password),
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { viewModel.onConfirmPasswordChange(it) },
            isPasswordVisible = viewModel.state.collectAsState().value.isVisibleConfirmPassword,
            isError = viewModel.state.collectAsState().value.isError,
            togglePasswordVisibility = { viewModel.onIsVisibleConfirmPasswordChange() }
        )
    }
}

@Composable
fun InformationPersonal(viewModel: RegisterViewModel) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            TextFIelCustom(
                value = viewModel.state.collectAsState().value.name,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { viewModel.onNameChange(it) },
                text = stringResource(Res.string.name_complete),
                placeholder = "surnames names",
                keyboardType = KeyboardType.Text,
                icon = Res.drawable.badge_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                isError = viewModel.state.collectAsState().value.isError
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            TextFIelCustom(
                value = viewModel.state.collectAsState().value.curp,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Text,
                placeholder = "##################",
                onValueChange = { viewModel.onCurpChange(it) },
                icon = Res.drawable.person_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                text = stringResource(Res.string.curp),
                isError = viewModel.state.collectAsState().value.isError
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            TextFIelCustom(
                value = viewModel.state.collectAsState().value.phoneNumber,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Phone,
                placeholder = "452 903 932",
                onValueChange = { viewModel.onPhoneNumberChange(it) },
                text = stringResource(Res.string.number_phone),
                icon = Res.drawable.phone_iphone_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                isError = viewModel.state.collectAsState().value.isError
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
                day = viewModel.state.collectAsState().value.day,
                month = viewModel.state.collectAsState().value.month,
                year = viewModel.state.collectAsState().value.year,
                isError = viewModel.state.collectAsState().value.isError,
                onDayChange = { viewModel.onDayChange(it) },
                onMonthChange = { viewModel.onMonthChange(it) },
                onYearChange = { viewModel.onYearChange(it) }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            StateDropdown(
                selectedState = viewModel.state.collectAsState().value.selectedState,
                isError = viewModel.state.collectAsState().value.isError,
                onStateSelected = { viewModel.onStateSelected(it) },
                onValueChange = { viewModel.onStateSelected(it) }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            SexField(
                selectedOption = viewModel.state.collectAsState().value.sex,
                isError = viewModel.state.collectAsState().value.isError,
                onSexChange = { viewModel.onSexSelected(it) }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun Approbation(viewModel: RegisterViewModel) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(16.dp)
    ) {
        item {
            // Términos y condiciones
            ItemWithLink(
                title = stringResource(Res.string.terms_and_conditions),
                description = stringResource(Res.string.terms_and_conditions_description),
                url = "https://example.com/terms",
                checked = viewModel.state.collectAsState().value.termsAndConditions,
                onCheckedChange = { viewModel.onTermsAndConditionsChange() }
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Política de privacidad
            ItemWithLink(
                title = stringResource(Res.string.privacy_policy),
                description = stringResource(Res.string.privacy_policy_description),
                url = "https://example.com/privacy",
                checked = viewModel.state.collectAsState().value.privacyPolicy,
                onCheckedChange = { viewModel.onPrivacyPolicyChange() }
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Analítica de datos
            ItemWithLink(
                title = stringResource(Res.string.data_analytics),
                description = stringResource(Res.string.data_analytics_description),
                url = "https://example.com/analytics",
                checked = viewModel.state.collectAsState().value.dataAnalytics,
                onCheckedChange = { viewModel.onDataAnalyticsChange() }
            )
        }
    }
}

@Composable
private fun ItemWithLink(
    title: String,
    description: String,
    url: String,
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorScheme.onBackground, shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = title,
            color = colorScheme.primary,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(resource = Res.font.Poppins_Bold)),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            color = colorScheme.tertiary,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(resource = Res.font.Poppins_Light)),
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = {

            },
            content = {
                Text(
                    text = stringResource(Res.string.read_more),
                    color = colorScheme.primary,
                    fontFamily = FontFamily(Font(resource = Res.font.Poppins_Bold)),
                )
            }
        )
        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckedChange()
            }
        )
    }
}