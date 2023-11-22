package com.lennartmoeller.ma_compose_multiplatform.ui

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import com.lennartmoeller.ma_compose_multiplatform.ui.custom.CustomIcon
import com.lennartmoeller.ma_compose_multiplatform.ui.custom.RegularStyle
import com.lennartmoeller.ma_compose_multiplatform.ui.custom.SolidStyle
import com.lennartmoeller.ma_compose_multiplatform.util.NavigablePage

@Composable
fun Skeleton(pages: List<NavigablePage>) {
    val skeletonState = remember { SkeletonState(pages) }
    skeletonState.RenderSkeleton()
}

class SkeletonState(private val pages: List<NavigablePage>) {

    companion object {
        private const val NAV_ITEM_UNSELECTED_OPACITY = 0.55F
        private val COMPRESSED_SIDEBAR_WIDTH = 80.dp
        private val EXTENDED_SIDEBAR_WIDTH = 220.dp
        private val MIN_CONTENT_WIDTH = 500.dp
        private val HEADER_HEIGHT_WIDE_DEVICES = 100.dp
        private val HEADER_HEIGHT_THIN_DEVICES = 80.dp
        private val CONTENT_PADDING_WIDE_DEVICES = 16.dp
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
                Header(thinDevice = true)
            },
            bottomBar = {
                NavigationBar {
                    pages.mapIndexed { index, page ->
                        val isSelected: Boolean = currentPageIndex == index
                        NavigationBarItem(
                            icon = { NavigationIcon(page, isSelected) },
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
                    pages[currentPageIndex].build()
                }
            },
            floatingActionButton = { pages[currentPageIndex].floatingActionButton?.invoke() }
        )
    }

    @Composable
    private fun RenderMediumDevice() {
        Scaffold(
            floatingActionButton = { pages[currentPageIndex].floatingActionButton?.invoke() }
        ) {
            Row {
                NavigationRail(
                    // set the elevation to match the NavigationBar elevation
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.0.dp),
                    header = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.height(HEADER_HEIGHT_WIDE_DEVICES - 8.dp)
                        ) { MenuButton() }
                    },
                    content = {
                        pages.mapIndexed { index, page ->
                            val isSelected: Boolean = currentPageIndex == index
                            NavigationRailItem(
                                icon = { NavigationIcon(page, isSelected) },
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
                    Box(modifier = Modifier.padding(horizontal = CONTENT_PADDING_WIDE_DEVICES)) {
                        pages[currentPageIndex].build()
                    }
                }
            }
        }
    }

    @Composable
    private fun RenderWideDevice() {
        Scaffold(
            floatingActionButton = { pages[currentPageIndex].floatingActionButton?.invoke() }
        ) {
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
                                .height(HEADER_HEIGHT_WIDE_DEVICES)
                                .padding(start = 21.dp)
                        ) { MenuButton() }
                        PermanentDrawerSheet(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            drawerTonalElevation = 3.0.dp
                        ) {
                            pages.mapIndexed { index, page ->
                                val isSelected: Boolean = currentPageIndex == index
                                NavigationDrawerItem(
                                    modifier = Modifier.height(46.dp),
                                    shape = MaterialTheme.shapes.medium,
                                    label = {
                                        Text(
                                            page.title,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.W500,
                                                fontSize = 13.sp,
                                                letterSpacing = 0.2.sp
                                            ),
                                            modifier = Modifier.alpha(if (!isSelected) NAV_ITEM_UNSELECTED_OPACITY else 1F)
                                        )
                                    },
                                    icon = { NavigationIcon(page, isSelected) },
                                    onClick = { currentPageIndex = index },
                                    selected = isSelected
                                )
                                if (index < pages.size - 1) {
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
                                .padding(horizontal = CONTENT_PADDING_WIDE_DEVICES)
                        ) {
                            pages[currentPageIndex].build()
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
            CustomIcon(unicode = "\uf0c9")
        }
    }

    @Composable
    private fun NavigationIcon(page: NavigablePage, isSelected: Boolean) {
        CustomIcon(
            unicode = page.iconUnicode,
            style = if (isSelected) SolidStyle() else RegularStyle(),
            opacity = if (isSelected) .8f else NAV_ITEM_UNSELECTED_OPACITY,
        )
    }

    @Composable
    private fun Header(thinDevice: Boolean = false) {
        val headerHeight =
            if (thinDevice) HEADER_HEIGHT_THIN_DEVICES else HEADER_HEIGHT_WIDE_DEVICES
        val paddingItems = (headerHeight - 52.dp) / 4
        val paddingSides =
            if (thinDevice) paddingItems * 2 else CONTENT_PADDING_WIDE_DEVICES + paddingItems
        Box(
            modifier = Modifier
                .statusBarsPadding() // avoid that header is under StatusBar in Android
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .height(headerHeight)
                .padding(horizontal = paddingSides)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(paddingItems)
            ) {
                if (thinDevice) MenuButton()
                pages[currentPageIndex].headerLeading.forEach { it() }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    Text(
                        text = pages[currentPageIndex].title,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    pages[currentPageIndex].subtitle?.let { subtitle ->
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
                        )
                    }
                }
                pages[currentPageIndex].headerTrailing.forEach { it() }
            }
        }
    }

}
