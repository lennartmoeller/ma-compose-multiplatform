package com.lennartmoeller.ma.composemultiplatform.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun ScreenWidthBreakpoint(width: Dp, smallDeviceContent: @Composable (() -> Unit)?, largeDeviceContent: @Composable (() -> Unit)?) {
    if (with(LocalDensity.current) { LocalWindowInfo.current.containerSize.width.toDp() } < width) {
        smallDeviceContent?.invoke()
    } else {
        largeDeviceContent?.invoke()
    }
}
