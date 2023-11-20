package com.lennartmoeller.ma_compose_multiplatform.ui.custom

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomDivider(level: Int = 0) {
    if (level == 0) {
        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = .5f))
    } else {
        Divider(
            modifier = Modifier.padding(horizontal = 10.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = .2f)
        )
    }
}
