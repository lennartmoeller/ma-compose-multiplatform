package com.lennartmoeller.ma.composemultiplatform.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("accounts") val accounts: Map<String, Account>,
    @SerialName("categories") val categories: Map<String, Category>,
    @SerialName("icons") val icons: Map<String, Icon>,
    @SerialName("transactions") val transactions: Map<String, Transaction>
)
