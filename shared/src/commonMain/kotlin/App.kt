import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import navigation.Navigation
import navigation.NavigationItem
import pages.FirstPage
import pages.SecondPage
import pages.ThirdPage
import ui.theme.AppTheme

@Composable
fun App() {
    AppTheme(useDarkTheme = false) {
        Navigation(
            navigationItems = listOf(
                NavigationItem(
                    page = { FirstPage() },
                    unselectedIcon = Filled.Check,
                    selectedIcon = Filled.Check,
                    label = "First"
                ), NavigationItem(
                    page = { SecondPage() },
                    unselectedIcon = Filled.Check,
                    selectedIcon = Filled.Check,
                    label = "Second"
                ), NavigationItem(
                    page = { ThirdPage() },
                    unselectedIcon = Filled.Check,
                    selectedIcon = Filled.Check,
                    label = "Third"
                )
            )
        )
    }
}
