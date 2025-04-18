package org.quickness.ui.components.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
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
            modifier = Modifier.weight(1.2f)
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