package com.lennartmoeller.ma.composemultiplatform.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val page: @Composable () -> Unit,
    val icon: String,
    val label: String
)
