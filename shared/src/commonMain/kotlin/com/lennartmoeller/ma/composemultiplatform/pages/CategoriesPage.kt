package com.lennartmoeller.ma.composemultiplatform.pages

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.lennartmoeller.ma.composemultiplatform.database.Database
import com.lennartmoeller.ma.composemultiplatform.entities.Category

@Composable
fun CategoriesPage() {
    val categories: List<Category> = Database.getCategories().values.toList().sortedBy { it.label }
    LazyColumn {
        items(count = categories.size) { index ->
            val category: Category = categories[index]
            ListItem(
                headlineContent = { Text(category.label) },
                leadingContent = {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Localized description",
                    )
                }
            )
            Divider()
        }
    }
}
