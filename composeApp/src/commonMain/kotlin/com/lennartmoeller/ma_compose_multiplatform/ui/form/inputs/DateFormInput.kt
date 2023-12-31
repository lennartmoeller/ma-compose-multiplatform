package com.lennartmoeller.ma_compose_multiplatform.ui.form.inputs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lennartmoeller.ma_compose_multiplatform.ui.custom.CustomIcon
import com.lennartmoeller.ma_compose_multiplatform.ui.custom.CustomTextButton
import com.lennartmoeller.ma_compose_multiplatform.ui.custom.RegularStyle
import com.lennartmoeller.ma_compose_multiplatform.ui.form.Form
import com.lennartmoeller.ma_compose_multiplatform.util.GermanDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateFormInput(
    form: Form,
    id: String,
    initial: String?,
    label: String,
    iconUnicode: String = "\uf073",
    onValueChange: ((value: String?) -> Unit)? = null,
    required: Boolean = false,
) {
    var value: String? by rememberSaveable { mutableStateOf(initial) }
    var text: String by rememberSaveable {
        mutableStateOf(initial?.let { GermanDate(it).beautifyDate() } ?: "")
    }
    var errorMessage: String? by rememberSaveable { mutableStateOf(null) }
    var openPicker by remember { mutableStateOf(false) }
    val getErrorMessage: () -> String? =
        { if (required && text.isEmpty()) "Dieses Feld ist erforderlich" else null }
    form.setFormInputFeedback(id, value.toString(), getErrorMessage() != null)
    Box(modifier = Modifier.padding(bottom = 16.dp)) {
        TextField(
            label = { Text(label) },
            value = text,
            leadingIcon = { CustomIcon(unicode = iconUnicode, style = RegularStyle()) },
            singleLine = true,
            onValueChange = {},
            // disabling is necessary to make it clickable
            enabled = false,
            modifier = Modifier
                .clickable { openPicker = true }
                .fillMaxWidth(),
            // fake colors so that it looks enabled
            colors = TextFieldDefaults.colors(
                disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .8f),
                disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .8f),
                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .8f),
                disabledContainerColor = Color.Transparent,
            ),
        )
    }
    if (openPicker) {
        val datePickerState = rememberDatePickerState(value?.let { GermanDate(it).toEpochMillis() })
        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
        DatePickerDialog(
            onDismissRequest = { openPicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        openPicker = false
                        val germanDate: GermanDate? =
                            datePickerState.selectedDateMillis?.let { GermanDate(it) }
                        value = germanDate?.toString()
                        text = germanDate?.beautifyDate() ?: ""
                        errorMessage = getErrorMessage()
                        form.setFormInputFeedback(id, value.toString(), errorMessage != null)
                        // call on value change
                        if (errorMessage == null && onValueChange != null) {
                            onValueChange(value)
                        }
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("Okay")
                }
            },
            dismissButton = {
                CustomTextButton(
                    onClick = { openPicker = false },
                    label = "Verwerfen",
                )
            }
        ) {
            DatePicker(
                state = datePickerState,
                dateFormatter = DatePickerFormatter(
                    "MMMM YYYY",
                    "dd.MM.YYYY",
                    "dd. MMMM YYYY",
                ),
                title = null,
                headline = {
                    val headline = if (datePickerState.selectedDateMillis == null) {
                        "Wähle ein Datum"
                    } else {
                        GermanDate(datePickerState.selectedDateMillis!!)
                            .beautifyDate(monthAsNumber = false)
                    }
                    Text(
                        text = headline,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                showModeToggle = false,
            )
        }
    }
}
