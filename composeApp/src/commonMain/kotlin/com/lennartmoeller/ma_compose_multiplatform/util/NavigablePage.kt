package com.lennartmoeller.ma_compose_multiplatform.util

import androidx.compose.runtime.Composable

abstract class NavigablePage {
    abstract val title: String

    abstract val iconUnicode: String

    open val subtitle: String? = null

    open val floatingActionButton: @Composable (() -> Unit)? = null

    open val headerLeading: List<@Composable () -> Unit> = emptyList()

    open val headerTrailing: List<@Composable () -> Unit> = emptyList()

    @Composable
    abstract fun build()

}
