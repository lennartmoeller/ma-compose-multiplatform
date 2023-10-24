package navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val page: @Composable () -> Unit,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val label: String
)
