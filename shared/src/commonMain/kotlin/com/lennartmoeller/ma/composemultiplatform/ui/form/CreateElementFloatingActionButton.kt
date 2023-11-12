package com.lennartmoeller.ma.composemultiplatform.ui.form

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lennartmoeller.ma.composemultiplatform.ui.custom.CustomIcon
import com.lennartmoeller.ma.composemultiplatform.ui.custom.RegularStyle

@Composable
fun CreateElementFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Row(
            modifier = Modifier.padding(start = 8.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomIcon(
                unicode = "2b",
                size = 18.sp,
                style = RegularStyle(),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Text(text = "Hinzufügen")
        }
    }
}
