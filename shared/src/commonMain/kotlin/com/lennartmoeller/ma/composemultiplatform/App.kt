package com.lennartmoeller.ma.composemultiplatform

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import com.lennartmoeller.ma.composemultiplatform.database.Database
import com.lennartmoeller.ma.composemultiplatform.entities.Data
import com.lennartmoeller.ma.composemultiplatform.navigation.Navigation
import com.lennartmoeller.ma.composemultiplatform.navigation.NavigationItem
import com.lennartmoeller.ma.composemultiplatform.pages.AccountsPage
import com.lennartmoeller.ma.composemultiplatform.pages.CategoriesPage
import com.lennartmoeller.ma.composemultiplatform.pages.TransactionsPage
import com.lennartmoeller.ma.composemultiplatform.ui.theme.AppTheme
import com.lennartmoeller.ma.composemultiplatform.utility.HttpHelper
import kotlinx.coroutines.runBlocking

@Composable
fun App() {
    // init data on app start
    runBlocking {
        val data: Data = HttpHelper.get(resource = "data")
        Database.build(data)
    }

    AppTheme(
        useDarkTheme = false // DEBUG: light and dark theme
    ) {
        Navigation(
            navigationItems = listOf(
                NavigationItem(
                    page = { CategoriesPage() },
                    unselectedIcon = Filled.Check,
                    selectedIcon = Filled.Check,
                    label = "Kategorien"
                ), NavigationItem(
                    page = { AccountsPage() },
                    unselectedIcon = Filled.Check,
                    selectedIcon = Filled.Check,
                    label = "Konten"
                ), NavigationItem(
                    page = { TransactionsPage() },
                    unselectedIcon = Filled.Check,
                    selectedIcon = Filled.Check,
                    label = "Transaktionen"
                )
            )
        )
    }
}
