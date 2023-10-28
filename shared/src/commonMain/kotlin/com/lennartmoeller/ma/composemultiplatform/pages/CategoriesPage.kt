package com.lennartmoeller.ma.composemultiplatform.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lennartmoeller.ma.composemultiplatform.database.Database

@Composable
fun CategoriesPage() {
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        Database.getCategories().forEach {
            ListItem(headlineContent = { Text(it.label) })
        }
    }
}
