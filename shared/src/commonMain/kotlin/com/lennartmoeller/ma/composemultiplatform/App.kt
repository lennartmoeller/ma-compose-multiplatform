package com.lennartmoeller.ma.composemultiplatform

import androidx.compose.runtime.Composable
import com.lennartmoeller.ma.composemultiplatform.database.Database
import com.lennartmoeller.ma.composemultiplatform.entities.Data
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
            pages = listOf(
                TransactionsPage(),
                CategoriesPage(),
                AccountsPage(),
            )
        )
    }
}
