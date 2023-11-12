package com.lennartmoeller.ma.composemultiplatform.ui.form.inputs

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.lennartmoeller.ma.composemultiplatform.ui.form.Form
import com.lennartmoeller.ma.composemultiplatform.util.Euro

@Composable
fun EuroFormInput(
    form: Form,
    id: String,
    initial: Int = 0,
    label: String,
    iconUnicode: String = "\uf153",
    onValueChange: ((value: Int) -> Unit)? = null,
    required: Boolean = false,
    signed: Boolean = true,
) {
    FormInput(
        form = form,
        id = id,
        initial = initial,
        label = label,
        iconUnicode = iconUnicode,
        toText = { value: Int -> Euro.toStr(value, includeEuroSign = false, includeDots = false) },
        toValue = { text: String -> Euro.toCent(text) ?: 0 },
        textFormatter = { old, new -> euroTextFormatter(old, new, signed) },
        validator = {
            if (required && it == 0) return@FormInput "Dieses Feld ist erforderlich"
            return@FormInput null
        },
        onValueChange = { onValueChange?.invoke(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    )
}

private fun euroTextFormatter(oldValue: String, newValue: String, signed: Boolean): String {
    // allow empty input as starting point
    if (newValue.isEmpty()) return newValue
    // clean string
    var newText = newValue
        // allow points as commas
        .replace(".", ",")
        // disallow all characters except digits, commas, minus
        .replace(Regex("[^0-9,-]"), "")
    // remove minus signs
    newText = if (signed && newText.startsWith("-")) {
        "-" + newText.substring(1).replace("-", "")
    } else {
        newText.replace("-", "")
    }
    // restore old value if the string is not a valid euro string
    val regex = Regex("^-?(0|[1-9][0-9]*)(,[0-9]{0,2})?\$|^-\$")
    return if (regex.matches(newText)) newText else oldValue
}
