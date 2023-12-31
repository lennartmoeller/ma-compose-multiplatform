package com.lennartmoeller.ma_compose_multiplatform.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lennartmoeller.ma_compose_multiplatform.database.Database
import com.lennartmoeller.ma_compose_multiplatform.entities.Account
import com.lennartmoeller.ma_compose_multiplatform.entities.Category
import com.lennartmoeller.ma_compose_multiplatform.entities.PutResponse
import com.lennartmoeller.ma_compose_multiplatform.entities.Transaction
import com.lennartmoeller.ma_compose_multiplatform.ui.SkeletonState
import com.lennartmoeller.ma_compose_multiplatform.ui.custom.CustomDivider
import com.lennartmoeller.ma_compose_multiplatform.ui.custom.CustomIcon
import com.lennartmoeller.ma_compose_multiplatform.ui.custom.RegularStyle
import com.lennartmoeller.ma_compose_multiplatform.ui.form.CreateElementFloatingActionButton
import com.lennartmoeller.ma_compose_multiplatform.ui.form.CreateElementTextButton
import com.lennartmoeller.ma_compose_multiplatform.ui.form.CustomDialog
import com.lennartmoeller.ma_compose_multiplatform.ui.form.Form
import com.lennartmoeller.ma_compose_multiplatform.ui.form.inputs.DateFormInput
import com.lennartmoeller.ma_compose_multiplatform.ui.form.inputs.DropdownSelectorFormInput
import com.lennartmoeller.ma_compose_multiplatform.ui.form.inputs.EuroFormInput
import com.lennartmoeller.ma_compose_multiplatform.ui.form.inputs.TextFormInput
import com.lennartmoeller.ma_compose_multiplatform.ui.util.ScreenWidthBreakpoint
import com.lennartmoeller.ma_compose_multiplatform.util.Euro
import com.lennartmoeller.ma_compose_multiplatform.util.GermanDate
import com.lennartmoeller.ma_compose_multiplatform.util.HttpHelper
import com.lennartmoeller.ma_compose_multiplatform.util.NavigablePage
import kotlinx.coroutines.launch

class TransactionsPage : NavigablePage() {
    override val title: String = "Transaktionen"
    override val iconUnicode: String = "\uf81d"
    override val floatingActionButton: @Composable () -> Unit = {
        ScreenWidthBreakpoint(
            width = CustomDialog.maxDialogContainerWidth,
            smallDeviceContent = {
                CreateElementFloatingActionButton(onClick = { dialog.open() })
            },
        )
    }
    override val headerTrailing: List<@Composable () -> Unit> = listOf({
        ScreenWidthBreakpoint(
            width = CustomDialog.maxDialogContainerWidth,
            largeDeviceContent = {
                CreateElementTextButton(onClick = { dialog.open() })
            }
        )
    })

    private var dialogTransaction by mutableStateOf<Transaction?>(null)
    private var dialog: CustomDialog = CustomDialog()
    private var form = Form()

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun build() {
        val accounts: Map<Int, Account> = Database.getAccounts()
        val categories: Map<Int, Category> = Database.getCategories()
        val transactionsGrouped: Map<String, List<Transaction>> =
            Database.getTransactions().values.sortedBy { it.date }.groupBy({ it.date }, { it })
        val coroutineScope = rememberCoroutineScope()
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
                    CustomDivider()
                }
                items(count = transactions.size) { index ->
                    val account: Account = accounts[transactions[index].account]!!
                    val category: Category = categories[transactions[index].category]!!
                    val transaction: Transaction = transactions[index]
                    ListItem(
                        headlineContent = { Text(category.label) },
                        leadingContent = {
                            CustomIcon(
                                name = category.icon,
                                style = RegularStyle()
                            )
                        },
                        supportingContent = {
                            transaction.description?.let {
                                if (it.isNotBlank()) Text(it)
                            }
                        },
                        trailingContent = {
                            Column(horizontalAlignment = Alignment.End) {
                                Text(text = account.label)
                                Text(text = Euro.toStr(transaction.amount))
                            }
                        },
                        modifier = Modifier.clickable(onClick = {
                            dialog.open(); dialogTransaction = transaction
                        })
                    )
                    // divider if not last item
                    if (index < transactions.size - 1) CustomDivider(1)
                }
            }
        }
        val accountOptions = accounts.values
            .sortedBy { it.label }
            .associate { it.id to it.label }
        val categoryOptions = categories.values
            .sortedBy { it.label }
            .associate { it.id to it.label }
        dialog.build(
            onClose = {
                dialog.close()
                dialogTransaction = null
            },
            onSave = { coroutineScope.launch { saveTransaction() } },
            title = "Transaktion " + if (dialogTransaction == null) "erstellen" else "bearbeiten",
            content = {
                Column {
                    DateFormInput(
                        form = form,
                        id = "date",
                        initial = dialogTransaction?.date,
                        label = "Datum",
                        required = true,
                    )
                    DropdownSelectorFormInput(
                        form = form,
                        id = "account",
                        initial = dialogTransaction?.account,
                        label = "Konto",
                        iconUnicode = "\uf19c",
                        options = accountOptions,
                        required = true,
                    )
                    DropdownSelectorFormInput(
                        form = form,
                        id = "category",
                        initial = dialogTransaction?.category,
                        label = "Kategorie",
                        iconUnicode = "\uf86d",
                        options = categoryOptions,
                        required = true,
                    )
                    TextFormInput(
                        form = form,
                        id = "description",
                        initial = dialogTransaction?.description ?: "",
                        label = "Beschreibung",
                        required = false,
                    )
                    EuroFormInput(
                        form = form,
                        id = "amount",
                        initial = dialogTransaction?.amount ?: 0,
                        label = "Betrag",
                        required = true,
                    )
                }
            }
        )
    }

    private suspend fun saveTransaction() {
        if (form.hasErrors()) return
        val values: MutableMap<String, Any?> = form.getValues()
        if (dialogTransaction == null) {
            dialogTransaction = Transaction.fromMap(values)
        } else {
            dialogTransaction!!.updateFromMap(values)
        }
        val response: PutResponse = HttpHelper.put("transaction", dialogTransaction)
        if (dialogTransaction!!.id == 0) {
            dialogTransaction!!.id = response.id
        }
        Database.insertTransaction(dialogTransaction!!)
        dialog.close()
        dialogTransaction = null
    }

}
