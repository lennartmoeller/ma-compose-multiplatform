package com.lennartmoeller.ma_compose_multiplatform.ui.form

import androidx.compose.runtime.Composable
import com.lennartmoeller.ma_compose_multiplatform.ui.custom.CustomTextButton

@Composable
fun CreateElementTextButton(onClick: () -> Unit) {
    CustomTextButton(
        onClick = onClick,
        iconUnicode = "2b",
        label = "Hinzufügen",
    )
}
