package com.lennartmoeller.ma.composemultiplatform.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.lennartmoeller.ma.composemultiplatform.ui.Divider
import com.lennartmoeller.ma.composemultiplatform.ui.FontAwesomeIcon
import com.lennartmoeller.ma.composemultiplatform.ui.SkeletonState
import com.lennartmoeller.ma.composemultiplatform.utility.GermanDate
import com.lennartmoeller.ma.composemultiplatform.utility.Money

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionsPage() {
    val accounts: Map<Int, Account> = Database.getAccounts()
    val categories: Map<Int, Category> = Database.getCategories()
    val transactionsGrouped: Map<String, List<Transaction>> =
        Database.getTransactions().values.sortedBy { it.date }.groupBy({ it.date }, { it })
    LazyColumn(contentPadding = PaddingValues(bottom = SkeletonState.PAGE_BOTTOM_PADDING)) {
        transactionsGrouped.forEach { (date, transactions) ->
            stickyHeader {
                Surface(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        text = GermanDate(date).beautifyDate()
                    )
                }
                Divider()
            }
            items(count = transactions.size) { index ->
                val account: Account = accounts[transactions[index].account]!!
                val category: Category = categories[transactions[index].category]!!
                val transaction: Transaction = transactions[index]
                ListItem(
                    headlineContent = { Text(category.label) },
                    leadingContent = { FontAwesomeIcon(name = category.icon) },
                    supportingContent = {
                        transaction.description?.let {
                            if (it.isNotBlank()) Text(it)
                        }
                    },
                    trailingContent = {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = account.label)
                            Text(text = Money.formatCents(transaction.amount))
                        }
                    },
                )
                // divider if not last item
                if (index < transactions.size - 1) Divider(1)
            }
        }
    }
}
