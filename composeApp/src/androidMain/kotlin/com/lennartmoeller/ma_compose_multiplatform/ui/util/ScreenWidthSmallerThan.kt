package com.lennartmoeller.ma_compose_multiplatform.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun ScreenWidthBreakpoint(
    width: Dp,
    smallDeviceContent: @Composable (() -> Unit)?,
    largeDeviceContent: @Composable (() -> Unit)?,
) {
    if (LocalConfiguration.current.screenWidthDp.dp < width) {
        smallDeviceContent?.invoke()
    } else {
        largeDeviceContent?.invoke()
    }
}
