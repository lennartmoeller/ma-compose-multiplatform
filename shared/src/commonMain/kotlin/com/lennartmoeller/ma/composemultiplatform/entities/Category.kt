package com.lennartmoeller.ma.composemultiplatform.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("id") var id: Int,
    @SerialName("label") var label: String,
    @SerialName("type") var type: Int,
)
