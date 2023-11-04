package com.lennartmoeller.ma.composemultiplatform.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Account(
    @SerialName("id") var id: Int,
    @SerialName("label") var label: String,
    @SerialName("start_balance") var startBalance: Int,
    @SerialName("icon") var icon: String?,
)
