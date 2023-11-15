package com.lennartmoeller.ma.composemultiplatform.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.lennartmoeller.ma.composemultiplatform.database.Database
import com.lennartmoeller.ma.composemultiplatform.entities.Account
import com.lennartmoeller.ma.composemultiplatform.entities.PutResponse
import com.lennartmoeller.ma.composemultiplatform.ui.SkeletonState
import com.lennartmoeller.ma.composemultiplatform.ui.custom.CustomDivider
import com.lennartmoeller.ma.composemultiplatform.ui.custom.CustomIcon
import com.lennartmoeller.ma.composemultiplatform.ui.form.CreateElementFloatingActionButton
import com.lennartmoeller.ma.composemultiplatform.ui.form.CreateElementTextButton
import com.lennartmoeller.ma.composemultiplatform.ui.form.CustomDialog
import com.lennartmoeller.ma.composemultiplatform.ui.form.Form
import com.lennartmoeller.ma.composemultiplatform.ui.form.inputs.EuroFormInput
import com.lennartmoeller.ma.composemultiplatform.ui.form.inputs.IconFormInput
import com.lennartmoeller.ma.composemultiplatform.ui.form.inputs.TextFormInput
import com.lennartmoeller.ma.composemultiplatform.ui.util.ScreenWidthBreakpoint
import com.lennartmoeller.ma.composemultiplatform.util.HttpHelper
import com.lennartmoeller.ma.composemultiplatform.util.NavigablePage
import kotlinx.coroutines.launch

class AccountsPage : NavigablePage() {
    override val title: String = "Konten"
    override val iconUnicode: String = "\uf19c"
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

    private var dialogAccount by mutableStateOf<Account?>(null)
    private var dialog: CustomDialog = CustomDialog()
    private var form = Form()

    @Composable
    override fun build() {
        val accounts: List<Account> = Database.getAccounts().values.toList().sortedBy { it.label }
        val coroutineScope = rememberCoroutineScope()
        LazyColumn(contentPadding = PaddingValues(bottom = SkeletonState.PAGE_BOTTOM_PADDING)) {
            items(count = accounts.size) { index ->
                val account: Account = accounts[index]
                ListItem(
                    headlineContent = { Text(account.label) },
                    leadingContent = { CustomIcon(name = account.icon) },
                    modifier = Modifier.clickable(onClick = {
                        dialog.open(); dialogAccount = account
                    })
                )
                // divider if not last item
                if (index < accounts.size - 1) CustomDivider(1)
            }
        }
        dialog.build(
            onClose = {
                dialog.close()
                dialogAccount = null
            },
            onSave = { coroutineScope.launch { saveAccount() } },
            title = "Konto " + if (dialogAccount == null) "erstellen" else "bearbeiten",
            content = {
                Column {
                    TextFormInput(
                        form = form,
                        id = "label",
                        initial = dialogAccount?.label ?: "",
                        label = "Bezeichnung",
                        required = true,
                    )
                    EuroFormInput(
                        form = form,
                        id = "start_balance",
                        initial = dialogAccount?.startBalance ?: 0,
                        label = "Startbetrag",
                    )
                    IconFormInput(
                        form = form,
                        id = "icon",
                        initial = dialogAccount?.icon ?: "",
                        label = "Icon-Name",
                    )
                }
            }
        )
    }

    private suspend fun saveAccount() {
        if (form.hasErrors()) return
        val values: MutableMap<String, Any?> = form.getValues()
        if (dialogAccount == null) {
            dialogAccount = Account.fromMap(values)
        } else {
            dialogAccount!!.updateFromMap(values)
        }
        val response: PutResponse = HttpHelper.put("account", dialogAccount)
        if (dialogAccount!!.id == 0) {
            dialogAccount!!.id = response.id
        }
        Database.insertAccount(dialogAccount!!)
        dialog.close()
        dialogAccount = null
    }

}
