package com.lennartmoeller.ma.composemultiplatform.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lennartmoeller.ma.composemultiplatform.database.Database
import com.lennartmoeller.ma.composemultiplatform.entities.Account
import com.lennartmoeller.ma.composemultiplatform.entities.Category
import com.lennartmoeller.ma.composemultiplatform.entities.Transaction
import com.lennartmoeller.ma.composemultiplatform.utility.GermanDate
import com.lennartmoeller.ma.composemultiplatform.utility.Money

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionsPage() {
    val accounts: Map<Int, Account> = Database.getAccounts()
    val categories: Map<Int, Category> = Database.getCategories()
    val transactionsGrouped: Map<String, List<Transaction>> =
        Database.getTransactions().values.sortedBy { it.date }.groupBy({ it.date }, { it })
    LazyColumn {
        transactionsGrouped.forEach { (date, transactions) ->
            stickyHeader {
                Surface(tonalElevation = 3.dp, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        text = GermanDate(date).beautifyDate()
                    )
                }
                Divider()
            }
            items(count = transactions.size) { index ->
                val transaction: Transaction = transactions[index]
                ListItem(
                    headlineContent = { Text(categories[transaction.category]!!.label) },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description",
                        )
                    },
                    supportingContent = {
                        transaction.description?.let {
                            if (it.isNotBlank()) Text(it)
                        }
                    },
                    trailingContent = {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = accounts[transaction.account]!!.label)
                            Text(text = Money.formatCents(transaction.amount))
                        }
                    },
                )
                Divider()
            }
        }
    }
}
