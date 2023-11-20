package com.lennartmoeller.ma_compose_multiplatform.pages

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
import com.lennartmoeller.ma_compose_multiplatform.database.Database
import com.lennartmoeller.ma_compose_multiplatform.entities.Category
import com.lennartmoeller.ma_compose_multiplatform.entities.PutResponse
import com.lennartmoeller.ma_compose_multiplatform.ui.SkeletonState
import com.lennartmoeller.ma_compose_multiplatform.ui.custom.CustomDivider
import com.lennartmoeller.ma_compose_multiplatform.ui.custom.CustomIcon
import com.lennartmoeller.ma_compose_multiplatform.ui.form.CreateElementFloatingActionButton
import com.lennartmoeller.ma_compose_multiplatform.ui.form.CreateElementTextButton
import com.lennartmoeller.ma_compose_multiplatform.ui.form.CustomDialog
import com.lennartmoeller.ma_compose_multiplatform.ui.form.Form
import com.lennartmoeller.ma_compose_multiplatform.ui.form.inputs.DropdownSelectorFormInput
import com.lennartmoeller.ma_compose_multiplatform.ui.form.inputs.IconFormInput
import com.lennartmoeller.ma_compose_multiplatform.ui.form.inputs.TextFormInput
import com.lennartmoeller.ma_compose_multiplatform.ui.util.ScreenWidthBreakpoint
import com.lennartmoeller.ma_compose_multiplatform.util.HttpHelper
import com.lennartmoeller.ma_compose_multiplatform.util.NavigablePage
import kotlinx.coroutines.launch

class CategoriesPage : NavigablePage() {
    override val title: String = "Kategorien"
    override val iconUnicode: String = "\uf86d"
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

    private var dialogCategory by mutableStateOf<Category?>(null)
    private var dialog: CustomDialog = CustomDialog()
    private var form = Form()

    @Composable
    override fun build() {
        val categories: List<Category> =
            Database.getCategories().values.toList().sortedBy { it.label }
        val coroutineScope = rememberCoroutineScope()
        LazyColumn(contentPadding = PaddingValues(bottom = SkeletonState.PAGE_BOTTOM_PADDING)) {
            items(count = categories.size) { index ->
                val category: Category = categories[index]
                ListItem(
                    headlineContent = { Text(category.label) },
                    leadingContent = { CustomIcon(name = category.icon) },
                    modifier = Modifier.clickable(onClick = {
                        dialog.open(); dialogCategory = category
                    })
                )
                // divider if not last item
                if (index < categories.size - 1) CustomDivider(1)
            }
        }
        dialog.build(
            onClose = {
                dialog.close()
                dialogCategory = null
            },
            onSave = { coroutineScope.launch { saveCategory() } },
            title = "Kategorie " + if (dialogCategory == null) "erstellen" else "bearbeiten",
            content = {
                Column {
                    DropdownSelectorFormInput(
                        form = form,
                        id = "type",
                        iconUnicode = "\uf02b",
                        initial = dialogCategory?.type ?: 1,
                        label = "Art",
                        options = mapOf(1 to "Ausgaben", 2 to "Einnahmen"),
                    )
                    TextFormInput(
                        form = form,
                        id = "label",
                        initial = dialogCategory?.label ?: "",
                        label = "Bezeichnung",
                        required = true,
                    )
                    IconFormInput(
                        form = form,
                        id = "icon",
                        initial = dialogCategory?.icon ?: "",
                        label = "Icon-Name",
                    )
                }
            }
        )
    }

    private suspend fun saveCategory() {
        if (form.hasErrors()) return
        val values: MutableMap<String, Any?> = form.getValues()
        if (dialogCategory == null) {
            dialogCategory = Category.fromMap(values)
        } else {
            dialogCategory!!.updateFromMap(values)
        }
        val response: PutResponse = HttpHelper.put("category", dialogCategory)
        if (dialogCategory!!.id == 0) {
            dialogCategory!!.id = response.id
        }
        Database.insertCategory(dialogCategory!!)
        dialog.close()
        dialogCategory = null
    }

}
