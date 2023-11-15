package com.lennartmoeller.ma.composemultiplatform.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Account(
    @SerialName("id") var id: Int = 0,
    @SerialName("label") var label: String,
    @SerialName("start_balance") var startBalance: Int,
    @SerialName("icon") var icon: String? = null,
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): Account {
            return Account(
                id = map["id"] as? Int ?: 0,
                label = map["label"] as String,
                startBalance = map["start_balance"] as Int,
                icon = map["icon"] as String?,
            )
        }
    }

    fun updateFromMap(map: Map<String, Any?>) {
        map["id"]?.let { id = it as Int }
        map["label"]?.let { label = it as String }
        map["start_balance"]?.let { startBalance = it as Int }
        map["icon"]?.let { icon = it as String? }
    }
}
