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
) {
    companion object {
        fun fromMap(map: Map<String, Any>): Transaction {
            return Transaction(
                id = map["id"] as Int,
                date = map["date"] as String,
                account = map["account"] as Int,
                category = map["category"] as Int,
                description = map["description"] as String,
                amount = map["amount"] as Int
            )
        }
    }

    fun updateFromMap(map: Map<String, Any>) {
        map["id"]?.let { id = it as Int }
        map["date"]?.let { date = it as String }
        map["account"]?.let { account = it as Int }
        map["category"]?.let { category = it as Int }
        map["description"]?.let { description = it as String }
        map["amount"]?.let { amount = it as Int }
    }
}
