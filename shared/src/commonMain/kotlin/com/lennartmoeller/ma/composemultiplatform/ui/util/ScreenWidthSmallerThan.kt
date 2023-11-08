package com.lennartmoeller.ma.composemultiplatform.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

@Composable
expect fun ScreenWidthBreakpoint(
    width: Dp,
    smallDeviceContent: @Composable (() -> Unit)? = null,
    largeDeviceContent: @Composable (() -> Unit)? = null
)
