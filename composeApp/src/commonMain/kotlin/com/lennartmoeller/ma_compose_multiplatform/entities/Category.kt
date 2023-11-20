package com.lennartmoeller.ma_compose_multiplatform.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("id") var id: Int = 0,
    @SerialName("label") var label: String,
    @SerialName("type") var type: Int,
    @SerialName("icon") var icon: String? = null,
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): Category {
            return Category(
                id = map["id"] as? Int ?: 0,
                label = map["label"] as String,
                type = map["type"] as Int,
                icon = map["icon"] as String?,
            )
        }
    }

    fun updateFromMap(map: Map<String, Any?>) {
        map["id"]?.let { id = it as Int }
        map["label"]?.let { label = it as String }
        map["type"]?.let { type = it as Int }
        map["icon"]?.let { icon = it as String? }
    }
}
