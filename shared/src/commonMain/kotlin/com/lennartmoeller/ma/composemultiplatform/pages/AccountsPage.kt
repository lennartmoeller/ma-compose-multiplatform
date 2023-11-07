package com.lennartmoeller.ma.composemultiplatform.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lennartmoeller.ma.composemultiplatform.database.Database
import com.lennartmoeller.ma.composemultiplatform.entities.Account
import com.lennartmoeller.ma.composemultiplatform.ui.Divider
import com.lennartmoeller.ma.composemultiplatform.ui.EditDialog
import com.lennartmoeller.ma.composemultiplatform.ui.EditDialogState
import com.lennartmoeller.ma.composemultiplatform.ui.FontAwesomeIcon
import com.lennartmoeller.ma.composemultiplatform.ui.RegularStyle
import com.lennartmoeller.ma.composemultiplatform.ui.SkeletonState
import com.lennartmoeller.ma.composemultiplatform.utility.NavigablePage
import com.lennartmoeller.ma.composemultiplatform.utility.ScreenWidthBreakpoint

class AccountsPage : NavigablePage() {
    override val title: String = "Konten"
    override val iconUnicode: String = "\uf19c"
    override val floatingActionButton: @Composable () -> Unit = {
        ScreenWidthBreakpoint(
            width = EditDialogState.maxDialogContainerWidth,
            smallDeviceContent = {
                FloatingActionButton(
                    onClick = {
                        dialogItem = null
                        dialogOpen = true
                    },
                ) {
                    Row(
                        modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FontAwesomeIcon(
                            unicode = "2b",
                            size = 18.sp,
                            style = RegularStyle(),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Text(text = "Hinzufügen")
                    }
                }
            },
        )
    }
    override val headerTrailing: List<@Composable () -> Unit> = listOf({
        ScreenWidthBreakpoint(
            width = EditDialogState.maxDialogContainerWidth,
            largeDeviceContent = {
                TextButton(onClick = {
                    dialogItem = null
                    dialogOpen = true
                }) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FontAwesomeIcon(
                            unicode = "2b",
                            size = 14.sp,
                            style = RegularStyle(),
                        )
                        Text("Hinzufügen")
                    }
                }
            }
        )
    })

    private var dialogOpen by mutableStateOf(false)
    private var dialogItem by mutableStateOf<Account?>(null)

    @Composable
    override fun build() {
        val accounts: List<Account> = Database.getAccounts().values.toList().sortedBy { it.label }
        LazyColumn(contentPadding = PaddingValues(bottom = SkeletonState.PAGE_BOTTOM_PADDING)) {
            items(count = accounts.size) { index ->
                val account: Account = accounts[index]
                ListItem(
                    headlineContent = { Text(account.label) },
                    leadingContent = { FontAwesomeIcon(name = account.icon) },
                    modifier = Modifier.clickable(onClick = {
                        dialogItem = account
                        dialogOpen = true
                    })
                )
                // divider if not last item
                if (index < accounts.size - 1) Divider(1)
            }
        }
        if (dialogOpen) {
            BuildDialog()
        }
    }

    @Composable
    fun BuildDialog() {
        EditDialog(
            onClose = { closeDialog() },
            onSave = { saveElement() },
            dialogTitle = "Konto " + if (dialogItem == null) "erstellen" else "bearbeiten"
        ) {

        }
    }

    private fun closeDialog() {
        dialogOpen = false
        dialogItem = null
    }

    private fun saveElement() {
        closeDialog()
    }

}
