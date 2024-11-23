package org.quickness.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AgeInputFields(
    day: String,
    month: String,
    year: String,
    isError: Boolean,
    onDayChange: (String) -> Unit,
    onMonthChange: (String) -> Unit,
    onYearChange: (String) -> Unit
) {
    val days = (1..31).map { it.toString().padStart(2, '0') }
    val months = (1..12).map { it.toString().padStart(2, '0') }
    val years = (2024 downTo 1920).map { it.toString() }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Dropdown para el día
        DropdownField(
            label = "Day",
            options = days,
            isError = isError,
            selectedOption = day,
            onOptionSelected = onDayChange,
            modifier = Modifier.weight(1f)
        )

        // Dropdown para el mes
        DropdownField(
            label = "Month",
            options = months,
            isError = isError,
            selectedOption = month,
            onOptionSelected = onMonthChange,
            modifier = Modifier.weight(1f)
        )

        // Dropdown para el año
        DropdownField(
            label = "Year",
            options = years,
            isError = isError,
            selectedOption = year,
            onOptionSelected = onYearChange,
            modifier = Modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    isError: Boolean = false,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            colors = TextFieldColorsApp(),
            readOnly = true,
            isError = isError,
            label = { Text(label) },
            trailingIcon = {
                if (expanded) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null,
                        modifier = Modifier.wrapContentHeight()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.wrapContentHeight()
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentWidth().height(200.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    text = { Text(option) }
                )
            }
        }
    }
}