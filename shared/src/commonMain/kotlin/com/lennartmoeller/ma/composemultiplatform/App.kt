package com.lennartmoeller.ma.composemultiplatform

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.runtime.Composable
import com.lennartmoeller.ma.composemultiplatform.database.Database
import com.lennartmoeller.ma.composemultiplatform.entities.Data
import com.lennartmoeller.ma.composemultiplatform.ui.NavigationItem
import com.lennartmoeller.ma.composemultiplatform.pages.AccountsPage
import com.lennartmoeller.ma.composemultiplatform.pages.CategoriesPage
import com.lennartmoeller.ma.composemultiplatform.pages.TransactionsPage
import com.lennartmoeller.ma.composemultiplatform.ui.Skeleton
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
        useDarkTheme = false // TODO: Remove after debugging
    ) {
        Skeleton(
            navigationItems = listOf(
                NavigationItem(
                    page = { TransactionsPage() },
                    unselectedIcon = Outlined.Payments,
                    selectedIcon = Filled.Payments,
                    label = "Transaktionen"
                ),
                NavigationItem(
                    page = { CategoriesPage() },
                    unselectedIcon = Outlined.Category,
                    selectedIcon = Filled.Category,
                    label = "Kategorien"
                ), NavigationItem(
                    page = { AccountsPage() },
                    unselectedIcon = Outlined.AccountBalance,
                    selectedIcon = Filled.AccountBalance,
                    label = "Konten"
                )
            )
        )
    }
}
