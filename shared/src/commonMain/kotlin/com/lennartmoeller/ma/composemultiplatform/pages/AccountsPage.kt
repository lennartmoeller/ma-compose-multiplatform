package com.lennartmoeller.ma.composemultiplatform.pages

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.lennartmoeller.ma.composemultiplatform.database.Database
import com.lennartmoeller.ma.composemultiplatform.entities.Account
import com.lennartmoeller.ma.composemultiplatform.ui.Divider
import com.lennartmoeller.ma.composemultiplatform.ui.SkeletonState

@Composable
fun AccountsPage() {
    val accounts: List<Account> = Database.getAccounts().values.toList().sortedBy { it.label }
    LazyColumn(contentPadding = PaddingValues(bottom = SkeletonState.PAGE_BOTTOM_PADDING)) {
        items(count = accounts.size) { index ->
            val account: Account = accounts[index]
            ListItem(
                headlineContent = { Text(account.label) }
            )
            // divider if not last item
            if (index < accounts.size - 1) Divider(1)
        }
    }
}
