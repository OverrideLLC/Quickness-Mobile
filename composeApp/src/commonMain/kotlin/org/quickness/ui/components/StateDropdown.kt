package org.quickness.ui.components

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
import androidx.compose.material3.ExposedDropdownMenuDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateDropdown(
    selectedState: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    onStateSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val states = listOf(
        "Aguascalientes", "Baja California", "Baja California Sur",
        "Campeche", "Chiapas", "Chihuahua", "Coahuila", "Colima",
        "Durango", "Guanajuato", "Guerrero", "Hidalgo", "Jalisco",
        "Michoacán", "Morelos", "Nayarit", "Nuevo León", "Oaxaca",
        "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosí",
        "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala",
        "Veracruz", "Yucatán", "Zacatecas", "Ciudad de México"
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            value = selectedState,
            colors = TextFieldColorsApp(),
            isError = isError,
            onValueChange = { onValueChange(it) },
            readOnly = true,
            label = { Text("State") },
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
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .wrapContentWidth()
                .height(200.dp)
        ) {
            states.forEach { state ->
                DropdownMenuItem(
                    onClick = {
                        onStateSelected(state)
                        expanded = false
                    },
                    text = { Text(state) },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
