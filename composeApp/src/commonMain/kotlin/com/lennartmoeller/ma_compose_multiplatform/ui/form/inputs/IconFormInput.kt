package com.lennartmoeller.ma_compose_multiplatform.ui.form.inputs

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.lennartmoeller.ma_compose_multiplatform.ui.form.Form

@Composable
fun IconFormInput(
    form: Form,
    id: String,
    initial: String = "",
    label: String,
    iconUnicode: String = "\uf86d",
    onValueChange: ((value: String) -> Unit)? = null,
    required: Boolean = false,
) {
    FormInput(
        form = form,
        id = id,
        initial = initial,
        label = label,
        iconUnicode = iconUnicode,
        toText = { it: String -> it },
        toValue = { it: String -> it },
        textFormatter = ::iconTextFormatter,
        onValueChange = onValueChange,
        required = required,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    )
}

private fun iconTextFormatter(oldValue: String, newValue: String): String {
    val regex = Regex("[^a-z0-9-]")
    return newValue.lowercase().replace(regex, "")
}
