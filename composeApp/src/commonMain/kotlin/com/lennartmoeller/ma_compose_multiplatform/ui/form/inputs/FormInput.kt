package com.lennartmoeller.ma_compose_multiplatform.ui.form.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.lennartmoeller.ma_compose_multiplatform.ui.custom.CustomIcon
import com.lennartmoeller.ma_compose_multiplatform.ui.form.Form

@Composable
fun <T : Any> FormInput(
    form: Form,
    id: String,
    initial: T,
    label: String,
    iconUnicode: String,
    required: Boolean = false,
    toText: ((T) -> String),
    toValue: ((String) -> T),
    textFormatter: ((oldValue: String, newValue: String) -> String)? = null,
    onValueChange: ((value: T) -> Unit)? = null,
    validator: ((value: T) -> String?)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    var text: String by rememberSaveable { mutableStateOf(toText(initial)) }
    var value: T by rememberSaveable { mutableStateOf(initial) }
    var errorMessage: String? by rememberSaveable { mutableStateOf(null) }
    val getErrorMessage: () -> String? = {
        validator?.invoke(value)
            ?: if (required && text.isEmpty()) "Dieses Feld ist erforderlich" else null
    }
    form.setFormInputFeedback(id, value, getErrorMessage() != null)
    TextField(
        label = { Text(label) },
        value = text,
        onValueChange = {
            // format text and save both text and value
            text = textFormatter?.invoke(text, it) ?: it
            value = toValue(text)
            errorMessage = getErrorMessage()
            form.setFormInputFeedback(id, value, errorMessage != null)
            // call on value change
            if (errorMessage == null && onValueChange != null) {
                onValueChange(value)
            }
        },
        leadingIcon = { CustomIcon(unicode = iconUnicode) },
        isError = errorMessage != null,
        supportingText = {
            if (errorMessage != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
    )
}
