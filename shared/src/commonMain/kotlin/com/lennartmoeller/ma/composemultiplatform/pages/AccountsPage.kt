package com.lennartmoeller.ma.composemultiplatform.pages

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.lennartmoeller.ma.composemultiplatform.database.Database
import com.lennartmoeller.ma.composemultiplatform.entities.Account

@Composable
fun AccountsPage() {
    val accounts: List<Account> = Database.getAccounts().values.toList().sortedBy { it.label }
    LazyColumn {
        items(count = accounts.size) { index ->
            val account: Account = accounts[index]
            ListItem(
                headlineContent = { Text(account.label) }
            )
            Divider()
        }
    }
}
