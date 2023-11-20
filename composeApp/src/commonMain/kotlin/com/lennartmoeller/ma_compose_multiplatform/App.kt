package com.lennartmoeller.ma_compose_multiplatform

import androidx.compose.runtime.Composable
import com.lennartmoeller.ma_compose_multiplatform.database.Database
import com.lennartmoeller.ma_compose_multiplatform.entities.Data
import com.lennartmoeller.ma_compose_multiplatform.pages.AccountsPage
import com.lennartmoeller.ma_compose_multiplatform.pages.CategoriesPage
import com.lennartmoeller.ma_compose_multiplatform.pages.TransactionsPage
import com.lennartmoeller.ma_compose_multiplatform.ui.Skeleton
import com.lennartmoeller.ma_compose_multiplatform.ui.theme.AppTheme
import com.lennartmoeller.ma_compose_multiplatform.util.HttpHelper
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
