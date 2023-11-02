package com.lennartmoeller.ma.composemultiplatform.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Skeleton(navigationItems: List<NavigationItem>) {
    val skeletonState = remember { SkeletonState(navigationItems) }
    skeletonState.RenderSkeleton()
}

class SkeletonState(private val navigationItems: List<NavigationItem>) {

    companion object {
        private const val NAV_ITEM_UNSELECTED_OPACITY = 0.55F
        private val COMPRESSED_SIDEBAR_WIDTH = 80.dp
        private val EXTENDED_SIDEBAR_WIDTH = 220.dp
        private val MIN_CONTENT_WIDTH = 500.dp
        private val HEADER_HEIGHT = 100.dp
        val PAGE_BOTTOM_PADDING = 14.dp
    }

    private var currentPageIndex by mutableStateOf(0)

    @Composable
    fun RenderSkeleton() {
        BoxWithConstraints {
            when {
                maxWidth < (MIN_CONTENT_WIDTH + COMPRESSED_SIDEBAR_WIDTH) -> RenderThinDevice()
                maxWidth < (MIN_CONTENT_WIDTH + EXTENDED_SIDEBAR_WIDTH) -> RenderMediumDevice()
                else -> RenderWideDevice()
            }
        }
    }

    @Composable
    private fun RenderThinDevice() {
        Scaffold(
            topBar = {
                Header(leading = { MenuButton() })
            },
            bottomBar = {
                NavigationBar {
                    navigationItems.mapIndexed { index, navigationItem ->
                        val isSelected: Boolean = currentPageIndex == index
                        NavigationBarItem(
                            icon = { NavigationIcon(navigationItem, isSelected) },
                            onClick = { currentPageIndex = index },
                            selected = isSelected,
                        )
                    }
                }
            },
            content = { innerPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(innerPadding)
                ) {
                    navigationItems[currentPageIndex].page()
                }
            }
        )
    }

    @Composable
    private fun RenderMediumDevice() {
        Scaffold {
            Row {
                NavigationRail(
                    // set the elevation to match the NavigationBar elevation
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.0.dp),
                    header = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.height(HEADER_HEIGHT - 8.dp)
                        ) { MenuButton() }
                    },
                    content = {
                        navigationItems.mapIndexed { index, navigationItem ->
                            val isSelected: Boolean = currentPageIndex == index
                            NavigationRailItem(
                                icon = { NavigationIcon(navigationItem, isSelected) },
                                onClick = { currentPageIndex = index },
                                selected = isSelected
                            )
                        }
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {
                    Header()
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        navigationItems[currentPageIndex].page()
                    }
                }
            }
        }
    }

    @Composable
    private fun RenderWideDevice() {
        Scaffold {
            PermanentNavigationDrawer(
                drawerContent = {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.0.dp))
                            .width(EXTENDED_SIDEBAR_WIDTH)
                    ) {
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(HEADER_HEIGHT)
                                .padding(start = 20.dp)
                        ) { MenuButton() }
                        PermanentDrawerSheet(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            drawerTonalElevation = 3.0.dp
                        ) {
                            navigationItems.mapIndexed { index, navigationItem ->
                                val isSelected: Boolean = currentPageIndex == index
                                NavigationDrawerItem(
                                    modifier = Modifier.height(46.dp),
                                    shape = MaterialTheme.shapes.medium,
                                    label = {
                                        Text(
                                            navigationItem.label,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.W500,
                                                fontSize = 13.sp,
                                                letterSpacing = 0.2.sp
                                            ),
                                            modifier = Modifier.alpha(if (!isSelected) NAV_ITEM_UNSELECTED_OPACITY else 1F)
                                        )
                                    },
                                    icon = { NavigationIcon(navigationItem, isSelected) },
                                    onClick = { currentPageIndex = index },
                                    selected = isSelected
                                )
                                if (index < navigationItems.size - 1) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                }
                            }
                        }
                    }
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {
                        Header()
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(horizontal = 16.dp)
                        ) {
                            navigationItems[currentPageIndex].page()
                        }
                    }
                }
            )
        }
    }

    @Composable
    private fun MenuButton() {
        IconButton(
            onClick = { println("TODO: Implement") } // TODO: Implement
        ) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Einstellungen")
        }
    }

    @Composable
    private fun NavigationIcon(navigationItem: NavigationItem, isSelected: Boolean) {
        Icon(
            imageVector = if (isSelected) navigationItem.selectedIcon else navigationItem.unselectedIcon,
            contentDescription = navigationItem.label,
            modifier = Modifier.alpha(if (!isSelected) NAV_ITEM_UNSELECTED_OPACITY else 1F)
        )
    }

    @Composable
    private fun Header(
        leading: @Composable (() -> Unit)? = null,
        title: String? = navigationItems[currentPageIndex].label,
        subtitle: String? = null,
        trailing: @Composable (() -> Unit)? = null,
    ) {
        val padding = (HEADER_HEIGHT - 52.dp) / 4
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .height(HEADER_HEIGHT)
                .padding(padding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leading != null) {
                    Box { leading.invoke() }
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    if (title != null) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = padding)
                        )
                    }
                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
                        )
                    }
                }
                trailing?.invoke()
            }
        }
    }

}
