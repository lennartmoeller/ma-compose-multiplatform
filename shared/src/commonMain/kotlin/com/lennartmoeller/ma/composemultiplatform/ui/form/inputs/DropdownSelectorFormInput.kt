package com.lennartmoeller.ma.composemultiplatform.ui.form.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lennartmoeller.ma.composemultiplatform.ui.custom.CustomIcon
import com.lennartmoeller.ma.composemultiplatform.ui.form.Form

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> DropdownSelectorFormInput(
    form: Form,
    id: String,
    initial: T,
    label: String,
    iconUnicode: String,
    options: Map<T, String>,
    required: Boolean = false,
) {
    var value: T by rememberSaveable { mutableStateOf(initial) }
    var text: String by rememberSaveable { mutableStateOf(options[initial]!!) }
    var expanded by rememberSaveable { mutableStateOf(false) }
    form.setFormInputFeedback(id, value, false)
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        TextField(
            readOnly = true,
            value = text,
            onValueChange = {},
            label = { Text(label) },
            leadingIcon = { CustomIcon(unicode = iconUnicode) },
            singleLine = true,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { (itemValue, itemText) ->
                DropdownMenuItem(
                    onClick = {
                        value = itemValue
                        text = itemText
                        expanded = false
                        form.setFormInputFeedback(id, value, false)
                    },
                    text = { Text(text = itemText) }
                )
            }
        }
    }
}
