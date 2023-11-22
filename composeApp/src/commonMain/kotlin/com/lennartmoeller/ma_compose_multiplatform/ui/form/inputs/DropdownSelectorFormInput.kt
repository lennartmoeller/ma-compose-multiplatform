package com.lennartmoeller.ma_compose_multiplatform.ui.form.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lennartmoeller.ma_compose_multiplatform.ui.custom.CustomIcon
import com.lennartmoeller.ma_compose_multiplatform.ui.custom.RegularStyle
import com.lennartmoeller.ma_compose_multiplatform.ui.form.Form

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> DropdownSelectorFormInput(
    form: Form,
    id: String,
    initial: T?,
    label: String,
    iconUnicode: String,
    options: Map<T, String>,
    onValueChange: ((value: T?) -> Unit)? = null,
    required: Boolean = false,
) {
    var value: T? by rememberSaveable { mutableStateOf(initial) }
    var text: String by rememberSaveable { mutableStateOf(options[initial] ?: "") }
    var errorMessage: String? by rememberSaveable { mutableStateOf(null) }
    val getErrorMessage: () -> String? =
        { if (required && text.isEmpty()) "Dieses Feld ist erforderlich" else null }
    var expanded by rememberSaveable { mutableStateOf(false) }
    form.setFormInputFeedback(id, value, getErrorMessage() != null)
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
            leadingIcon = { CustomIcon(unicode = iconUnicode, style = RegularStyle()) },
            singleLine = true,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
            ),
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
                        errorMessage = getErrorMessage()
                        form.setFormInputFeedback(id, value.toString(), errorMessage != null)
                        // call on value change
                        if (errorMessage == null && onValueChange != null) {
                            onValueChange(value)
                        }
                    },
                    text = { Text(text = itemText) }
                )
            }
        }
    }
}
