package com.lennartmoeller.ma.composemultiplatform.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    @SerialName("id") var id: Int,
    @SerialName("date") var date: String,
    @SerialName("account") var account: Int,
    @SerialName("category") var category: Int,
    @SerialName("description") var description: String?,
    @SerialName("amount") var amount: Int
)
