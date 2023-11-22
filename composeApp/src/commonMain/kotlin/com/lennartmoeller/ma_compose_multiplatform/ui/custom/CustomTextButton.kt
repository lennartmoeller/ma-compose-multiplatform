package com.lennartmoeller.ma_compose_multiplatform.ui.custom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextButton(
    iconUnicode: String? = null,
    label: String? = null,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                start = if (iconUnicode == null) 8.dp else 4.dp,
                end = 8.dp
            )
        ) {
            iconUnicode?.let {
                CustomIcon(
                    unicode = it,
                    size = 14.sp,
                    style = RegularStyle(),
                )
            }
            label?.let { Text(label) }
        }
    }
}
