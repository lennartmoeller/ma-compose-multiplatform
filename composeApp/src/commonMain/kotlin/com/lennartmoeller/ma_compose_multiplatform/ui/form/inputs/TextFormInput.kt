package com.lennartmoeller.ma_compose_multiplatform.ui.form.inputs

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.lennartmoeller.ma_compose_multiplatform.ui.form.Form
import com.lennartmoeller.ma_compose_multiplatform.ui.form.inputs.FormInput

@Composable
fun TextFormInput(
    form: Form,
    id: String,
    initial: String = "",
    label: String,
    iconUnicode: String = "\uf02b",
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
        onValueChange = onValueChange,
        required = required,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    )
}
