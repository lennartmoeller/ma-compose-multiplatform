package navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Navigation(navigationItems: List<NavigationItem>) {
    var currentPageIndex: Int by rememberSaveable { mutableStateOf(0) }

    BoxWithConstraints {
        if (maxWidth < 500.dp) {
            Scaffold(
                bottomBar = {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.background,
                        tonalElevation = 0.dp
                    ) {
                        navigationItems.mapIndexed { index, element ->
                            NavigationBarItem(
                                label = { Text(element.label) },
                                icon = {
                                    Icon(
                                        imageVector = element.unselectedIcon,
                                        contentDescription = element.label
                                    )
                                },
                                onClick = { currentPageIndex = index },
                                selected = currentPageIndex == index,
                            )
                        }
                    }
                },
            ) { innerPadding ->
                Box(
                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                        .fillMaxHeight().fillMaxWidth().padding(innerPadding)
                ) {
                    navigationItems[currentPageIndex].page()
                }
            }
        } else {
            Scaffold {
                Row {
                    NavigationRail(containerColor = MaterialTheme.colorScheme.background) {
                        navigationItems.mapIndexed { index, element ->
                            NavigationRailItem(
                                label = { Text(element.label) },
                                icon = {
                                    Icon(
                                        imageVector = element.unselectedIcon,
                                        contentDescription = element.label
                                    )
                                },
                                onClick = { currentPageIndex = index },
                                selected = currentPageIndex == index,
                            )
                        }
                    }
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                            .fillMaxHeight().fillMaxWidth()
                    ) {
                        navigationItems[currentPageIndex].page()
                    }
                }
            }
        }
    }
}
