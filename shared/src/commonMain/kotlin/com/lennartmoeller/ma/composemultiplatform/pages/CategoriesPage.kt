package com.lennartmoeller.ma.composemultiplatform.pages

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.lennartmoeller.ma.composemultiplatform.database.Database
import com.lennartmoeller.ma.composemultiplatform.entities.Category
import com.lennartmoeller.ma.composemultiplatform.ui.custom.CustomDivider
import com.lennartmoeller.ma.composemultiplatform.ui.custom.CustomIcon
import com.lennartmoeller.ma.composemultiplatform.ui.SkeletonState
import com.lennartmoeller.ma.composemultiplatform.util.NavigablePage

class CategoriesPage : NavigablePage() {
    override val title: String = "Kategorien"
    override val iconUnicode: String = "\uf86d"

    @Composable
    override fun build() {
        val categories: List<Category> =
            Database.getCategories().values.toList().sortedBy { it.label }
        LazyColumn(contentPadding = PaddingValues(bottom = SkeletonState.PAGE_BOTTOM_PADDING)) {
            items(count = categories.size) { index ->
                val category: Category = categories[index]
                ListItem(
                    headlineContent = { Text(category.label) },
                    leadingContent = { CustomIcon(name = category.icon) }
                )
                // divider between items
                if (index < categories.size - 1) CustomDivider(1)
            }
        }
    }

}
